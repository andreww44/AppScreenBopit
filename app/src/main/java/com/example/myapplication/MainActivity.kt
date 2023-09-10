package com.example.myapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //HOME SCREEN
        //Intent intento = new Intent(packageContext: MainActivity.this, AboutActivity.class)

        val intentAbout = Intent(this, AboutActivity::class.java)
        startActivity(intentAbout);


    }
}