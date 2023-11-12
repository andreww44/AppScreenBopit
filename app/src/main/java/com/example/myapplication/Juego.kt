package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore.Audio.Media
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Math.abs
import java.util.Random

private const val DEBUG_TAG = "Gestures"

val Context.dataStore by preferencesDataStore(name = "USER_PREFERENCES_NAME")
class Juego : AppCompatActivity(), SensorEventListener {

    private var musicOn = true;

    private lateinit var victorySong: MediaPlayer;
    private lateinit var defeatSong: MediaPlayer
    private lateinit var backMusic: MediaPlayer;
    private lateinit var doubleSound: MediaPlayer;
    private lateinit var downitSound: MediaPlayer
    private lateinit var flingitSound: MediaPlayer;
    private lateinit var shakeitSound: MediaPlayer;
    private lateinit var touchitSound: MediaPlayer;
    private lateinit var niceTrySound: MediaPlayer;

    private val linear_acceleration = DoubleArray(3) { 0.0 }

    private lateinit var gestureDetector: GestureDetector

    private lateinit var textViewTouchEvent: TextView

    private lateinit var sensorManager: SensorManager
    private lateinit var sensor: Sensor

    private val acciones = arrayOf( "Fling-it", "DoubleTouch-It", "Down-IT!!", "Tap-It", "Shake-IT")
    private val random = Random()
    private var timeGame = 5000L;
    private var currentNumber = 0

    private var next = false
    private lateinit var gameText: TextView
    private var gameHandler: Handler = Handler(Looper.getMainLooper())

    private var score = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego)


        sensorManager =getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        gestureDetector = GestureDetector(this, GestureListener())

        val a = sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)

        //Crear Lsita
        textViewTouchEvent = findViewById(R.id.textViewTouchEvent)



        //MediaPlayer --- AudioCall
        mediaCallSounds()


        backMusic.start()
        backMusic.isLooping = true;

        //LoopGame
        gameText = findViewById<TextView>(R.id.textrun);

        currentNumber = abs(random.nextInt(acciones.size))
        callAction(currentNumber)

        gameText.text = acciones[currentNumber]

        val delayMillis = timeGame.toLong()
        gameHandler.postDelayed(nextGameRunnable, delayMillis)
        displayTimerHandler.postDelayed(displayTimerRunnable, displayTimerMillis)


    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)

    }
    override fun onResume() {
        super.onResume()
        backMusic.start()
        backMusic.isLooping = true;
    }

    override fun onPause() {
        super.onPause()
        backMusic.pause()
        victorySong.pause()
        defeatSong.pause()
    }

    inner class GestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onFling(
            e1: MotionEvent, e2: MotionEvent,
            velocityX: Float, velocityY: Float
        ): Boolean {
            if(currentNumber == 0 && !next )
            {
                winGame();
            }
            return true
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            if(currentNumber == 1 && !next)
            {
                winGame();
            }
            return super.onDoubleTap(e)
        }

        override fun onDown(e: MotionEvent): Boolean {
            if(currentNumber == 2 && !next)
            {
                winGame();
            }
            return true
        }

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            if(currentNumber == 3 && !next)
            {
                winGame();
            }
            return true
        }
    }


    override fun onSensorChanged(p0: SensorEvent?) {
        val alpha: Float = 0.8f
        var gravity = 9.8;

        if(p0 != null){
            var gravity_1 = alpha * gravity + (1 - alpha) * p0.values[0]
            var gravity_2 = alpha * gravity + (1 - alpha) * p0.values[1]
            var gravity_3 = alpha * gravity + (1 - alpha) * p0.values[2]

            // Remove the gravity contribution with the high-pass filter.
            linear_acceleration[0] = p0.values[0] - gravity_1
            linear_acceleration[1] = p0.values[1] - gravity_2
            linear_acceleration[2] = p0.values[2] - gravity_3

            if(linear_acceleration[1] > 1f)
            {
                if(currentNumber == 4 && !next)
                {
                    winGame()
                }
            }
        }

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    //GAAAAAMEEEEEEEE
    private val nextGameRunnable = Runnable { GameLoop() }

    private fun GameLoop() {

        if(next)
        {
            next = false;
            gameText.text = acciones[currentNumber]
            timeGame -= displayTimerMillis
            displayTime = timeGame.toFloat()/1000
            gameHandler.postDelayed(nextGameRunnable, timeGame)
        }
        else
        {
            gameText.setTextColor(Color.RED)
            backMusic.stop()
            niceTrySound.start()
            val customClassScore = customClassScore(this,"Game-Over", score.toString())
            customClassScore.show();

            //Persistencia de datos
            lifecycleScope.launch(Dispatchers.IO) {
                saveScore(score);
            }


            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
            },10000L)
            loseGame();
        }

    }

    private fun winGame(){
        gameText.setTextColor(Color.GREEN)
        next = true

        score += 100

        val prevGame = currentNumber
        do
        {
            currentNumber = abs(random.nextInt(acciones.size))
        }
        while (currentNumber == prevGame)
        callAction(currentNumber)
        gameHandler.removeCallbacks(nextGameRunnable)
        gameHandler.postDelayed(nextGameRunnable, 100)
    }

    private var displayTime: Float = timeGame.toFloat()/1000
    private val displayTimerMillis: Long = 100
    private val displayTimerHandler: Handler = Handler(Looper.getMainLooper())

    private val displayTimerRunnable = Runnable {
        displayTime -= 0.1f;
        displayTime = Math.max(displayTime, 0f)
        callDisplayTime()
    }

    private fun callDisplayTime()
    {
        displayTimerHandler.postDelayed(displayTimerRunnable, displayTimerMillis)
    }

    private fun mediaCallSounds(){
        backMusic = MediaPlayer.create(applicationContext, R.raw.loopbopit)
        defeatSong = MediaPlayer.create(applicationContext, R.raw.fallo)
        victorySong = MediaPlayer.create(applicationContext, R.raw.victoria)
        doubleSound = MediaPlayer.create(applicationContext, R.raw.doubleit)
        downitSound = MediaPlayer.create(applicationContext, R.raw.downit)
        flingitSound = MediaPlayer.create(applicationContext, R.raw.flingit)
        shakeitSound = MediaPlayer.create(applicationContext, R.raw.shakeit)
        touchitSound = MediaPlayer.create(applicationContext, R.raw.touchit)
        niceTrySound = MediaPlayer.create(applicationContext, R.raw.nicetry)
    }

    private fun callAction(n: Int){
        if(n == 0){
            flingitSound.start()
        } else if(n == 1){
            doubleSound.start()
        }else if(n == 2){
            downitSound.start()
        }else if(n == 3){
            touchitSound.start()
        }else if(n == 4){
            shakeitSound.start()
        }
    }

    private fun loseGame(){
        val value = 4000
        val delayMillis = value.toLong()

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({


            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, delayMillis)
    }

    private suspend fun saveScore(score:Int){
        dataStore.edit {preferences->
            preferences[intPreferencesKey("puntaje")] = score

        }
    }

}


