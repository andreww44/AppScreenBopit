package com.example.myapplication

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView

class customClassScore(context: Context,
                       private val title:String? = "Game - Over",
                       private var numero: String = "000000",

                       ): Dialog(context) {

    private var salir = false;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.cardviewin)
        val titletext = findViewById<TextView>(R.id.gameover)
        val numerotext = findViewById<TextView>(R.id.score2)
        //val buttona = findViewById<Button>(R.id.buttonBack)


        //titletext.text = title;
        numerotext.text = numero


    }
}