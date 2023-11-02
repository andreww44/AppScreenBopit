package com.example.myapplication

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

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

    private val linear_acceleration = DoubleArray(3) { 0.0 }

    private lateinit var gestureDetector: GestureDetector

    private lateinit var textViewTouchEvent: TextView
    private lateinit var listViewTouchEventHistory: ListView
    private lateinit var touchEventHistory: ArrayList<String>
    private lateinit var historyAdapter: ArrayAdapter<String>


    private lateinit var sensorManager: SensorManager
    private lateinit var sensor: Sensor
    //private lateinit var sensorEventListener: SensorEventListener
    //ivate lateinit var sensorEventLsitener: SensorEventListener

    private lateinit var axi_x: TextView
    private lateinit var axi_y: TextView
    private lateinit var axi_z: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego)

        sensorManager =getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        gestureDetector = GestureDetector(this, GestureListener())


        val a = sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)

        //ToolBar
        setSupportActionBar(findViewById(R.id.toolbar3))

        //Crear Lsita
        textViewTouchEvent = findViewById(R.id.textViewTouchEvent)
        listViewTouchEventHistory = findViewById(R.id.listViewTouchEventHistory)

        axi_x = findViewById(R.id.AxiX)
        axi_y = findViewById(R.id.AxiY)
        axi_z = findViewById(R.id.AxiZ)

        touchEventHistory = ArrayList()
        historyAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, touchEventHistory)
        listViewTouchEventHistory.adapter = historyAdapter

        //MediaPlayer
        backMusic = MediaPlayer.create(applicationContext, R.raw.laser)
        defeatSong = MediaPlayer.create(applicationContext, R.raw.fallo)
        victorySong = MediaPlayer.create(applicationContext, R.raw.victoria)

        bPlay = findViewById(R.id.playButton)
        bStop = findViewById(R.id.stopButton)
        bVictory = findViewById(R.id.victoryButton)
        bDefeat = findViewById(R.id.defeatButton)

        backMusic.start()
        backMusic.isLooping = true;

        //sensor.

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
            showToast("onFling")
            addToList("onFling")
            return true
        }
    }

    /*
    inner class sensorEventListener: SensorEventListener{

        override fun onSensorChanged(event: SensorEvent) {

            val alpha: Float = 0.8f
            var gravity = 9.8;

            // Isolate the force of gravity with the low-pass filter. val gravity[3]: Float;
            var gravity_1 = alpha * gravity + (1 - alpha) * event.values[0]
            var gravity_2 = alpha * gravity + (1 - alpha) * event.values[1]
            var gravity_3 = alpha * gravity + (1 - alpha) * event.values[2]

            // Remove the gravity contribution with the high-pass filter.
            linear_acceleration[0] = event.values[0] - gravity_1
            linear_acceleration[1] = event.values[1] - gravity_2
            linear_acceleration[2] = event.values[2] - gravity_3

            axi_x.text = linear_acceleration[0].toString();
            axi_y.text = linear_acceleration[1].toString();
            axi_z.text = linear_acceleration[2].toString();
        }

        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        }
    }*/

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun addToList(action: String){
        val historyEntry = "Action: $action"
        textViewTouchEvent.text = action
        touchEventHistory.add(0, historyEntry)
        historyAdapter.notifyDataSetChanged()
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

            axi_x.text = linear_acceleration[0].toString();
            axi_y.text = linear_acceleration[1].toString();
            axi_z.text = linear_acceleration[2].toString();
        }
        // Isolate the force of gravity with the low-pass filter. val gravity[3]: Float;

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }


}


