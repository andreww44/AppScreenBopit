package com.example.myapplication

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Random

private const val DEBUG_TAG = "Gestures"
class Juego : AppCompatActivity(), SensorEventListener {

    private var musicOn = true;

    private lateinit var victorySong: MediaPlayer;
    private lateinit var defeatSong: MediaPlayer
    private lateinit var backMusic: MediaPlayer;

    private lateinit var bPlay: Button
    private lateinit var bStop: Button
    private lateinit var bVictory: Button
    private lateinit var bDefeat: Button
    public var currentNumer = 0
    public var next = false;

    private val linear_acceleration = DoubleArray(3) { 0.0 }

    private lateinit var gestureDetector: GestureDetector

    private lateinit var textViewTouchEvent: TextView
    private lateinit var bopi: TextView
    private lateinit var textViewRun: TextView

    private lateinit var sensorManager: SensorManager
    private lateinit var sensor: Sensor

    private lateinit var axi_x: TextView
    private lateinit var axi_y: TextView
    private lateinit var axi_z: TextView


    //Tiempoo

    private var timeInterval = 3000L // Initial time interval, set to 3 seconds (3000 milliseconds)
    private val handler = Handler(Looper.getMainLooper())
    private var count  = 0
    private lateinit var updateText: Runnable

    private val acciones = Array<String>(3){"Shakeit"; "Flingit"; "DoubleTouchIt"}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego)


        sensorManager =getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        gestureDetector = GestureDetector(this, GestureListener())

        val a = sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)

        //Crear Lsita
        textViewTouchEvent = findViewById(R.id.textViewTouchEvent)
        textViewRun = findViewById(R.id.textrun)

        axi_x = findViewById(R.id.AxiX)
        axi_y = findViewById(R.id.AxiY)
        axi_z = findViewById(R.id.AxiZ)

        bopi = findViewById(R.id.bopi)

        //MediaPlayer
        backMusic = MediaPlayer.create(applicationContext, R.raw.loopbopit)
        defeatSong = MediaPlayer.create(applicationContext, R.raw.fallo)
        victorySong = MediaPlayer.create(applicationContext, R.raw.victoria)

        bPlay = findViewById(R.id.playButton)
        bStop = findViewById(R.id.stopButton)
        bVictory = findViewById(R.id.victoryButton)
        bDefeat = findViewById(R.id.defeatButton)

        backMusic.start()
        backMusic.isLooping = true;


        updateText = updateTextRunnable



        decreaseTimeDelay()

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)

    }
    override fun onResume() {
        super.onResume()
        bPlay.setOnClickListener{
            backMusic.start()
            victorySong.pause()
            defeatSong.pause()
            backMusic.isLooping = true;
        }
        bStop.setOnClickListener(){
            backMusic.pause()
            victorySong.pause()
            defeatSong.pause()
        }

        bVictory.setOnClickListener(){
            victorySong.start()
            backMusic.pause()
        }

        bDefeat.setOnClickListener(){
            defeatSong.start()
            victorySong.pause();
        }

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
            showToast("Flingit")
            textViewTouchEvent.text = "Flinit";
            if(currentNumer == 2)
            {
                bopi.text = "Bopit"
                next = true;
                //decreaseTimeDelay()
            }
            return true
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            showToast("DoubleTouch")
            textViewTouchEvent.text = "Doubleit";
            if(currentNumer == 3)
            {
                bopi.text = "Bopit"
                next = true;
                //decreaseTimeDelay()
            }
            return super.onDoubleTap(e)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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

            axi_x.text = "X = " + linear_acceleration[0].toString();
            axi_y.text = "Y = " + linear_acceleration[1].toString();
            axi_z.text = "Z = " + linear_acceleration[2].toString();
        }
        // Isolate the force of gravity with the low-pass filter. val gravity[3]: Float;

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }


    private val updateTextRunnable = object : Runnable {
        override fun run() {
            textViewRun.text = changeInstruction() + " (" + timeInterval.toString() + " )"
            handler.postDelayed(updateText, timeInterval)
        }
    }

    private fun changeInstruction() : String? {
        val random = Random()
        val randomIndex = random.nextInt(acciones.size)
        currentNumer = randomIndex + 1;
        return acciones[randomIndex]
    }

    private fun decreaseTimeDelay() {
        count += 1
        if (count == 3) {
            handler.removeCallbacks(updateText)
            return
        }
        timeInterval -= 500L
        handler.removeCallbacks(updateText) // Remove previous callbacks
        handler.postDelayed(updateText, timeInterval)
    }

    private fun GameLoop() {
        next = false;
        if(next == true){
            decreaseTimeDelay();
        }

    }
}


