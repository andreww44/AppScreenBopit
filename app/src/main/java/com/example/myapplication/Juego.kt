package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Juego : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego)

        val intentMain = Intent(this, MainActivity::class.java)

        val boton = findViewById<Button>(R.id.Volver)

        boton.setOnClickListener{
            finish()
            startActivity(intentMain);
        }
    }
}