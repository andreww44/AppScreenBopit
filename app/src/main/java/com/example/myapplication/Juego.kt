package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.media.MediaPlayer
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GestureDetectorCompat

private const val DEBUG_TAG = "Gestures"
class Juego : AppCompatActivity() {

    private var musicOn = true;

    private lateinit var victorySong: MediaPlayer;
    private lateinit var defeatSong: MediaPlayer
    private lateinit var backMusic: MediaPlayer;

    private lateinit var bPlay: Button
    private lateinit var bStop: Button
    private lateinit var bVictory: Button
    private lateinit var bDefeat: Button

    private lateinit var gestureDetector: GestureDetector

    private lateinit var textViewTouchEvent: TextView
    private lateinit var listViewTouchEventHistory: ListView
    private lateinit var touchEventHistory: ArrayList<String>
    private lateinit var historyAdapter: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego)

        gestureDetector = GestureDetector(this, GestureListener())
        //mDetector.setOnDoubleTapListener(this)

        setSupportActionBar(findViewById(R.id.toolbar3))

        textViewTouchEvent = findViewById(R.id.textViewTouchEvent)
        listViewTouchEventHistory = findViewById(R.id.listViewTouchEventHistory)

        //Creating the list
        touchEventHistory = ArrayList()
        historyAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, touchEventHistory)
        listViewTouchEventHistory.adapter = historyAdapter

        val intentMain = Intent(this, MainActivity::class.java)

        backMusic = MediaPlayer.create(applicationContext, R.raw.laser)
        defeatSong = MediaPlayer.create(applicationContext, R.raw.fallo)
        victorySong = MediaPlayer.create(applicationContext, R.raw.victoria)

        bPlay = findViewById(R.id.playButton)
        bStop = findViewById(R.id.stopButton)
        bVictory = findViewById(R.id.victoryButton)
        bDefeat = findViewById(R.id.defeatButton)

        backMusic.start()
        backMusic.isLooping = true;

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
            defeatSong.pause()
        }

        bDefeat.setOnClickListener(){
            defeatSong.start()
            backMusic.pause()
            victorySong.pause();
        }

    }

    override fun onPause() {
        super.onPause()
        backMusic.pause();
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

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun addToList(action: String){
        val historyEntry = "Action: $action"
        textViewTouchEvent.text = action
        touchEventHistory.add(0, historyEntry)
        historyAdapter.notifyDataSetChanged()
    }


}


