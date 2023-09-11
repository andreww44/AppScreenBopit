package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val intentMain = Intent(this, MainActivity::class.java)

        val boton = findViewById<Button>(R.id.boton2)

        boton.setOnClickListener{
            startActivity(intentMain);
        }
    }
}