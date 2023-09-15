package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Intent about
        val intentAbout = Intent(this, AboutActivity::class.java)
        val aboutButton = findViewById<Button>(R.id.About)

        aboutButton.setOnClickListener{
            startActivity(intentAbout);
        }

        //Intent Exit

        val exitButton = findViewById<Button>(R.id.About)

    }

    @SuppressLint("")

}