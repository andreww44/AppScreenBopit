package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))

        //Intent Exit
        val exitButton = findViewById<Button>(R.id.Salir)

        exitButton.setOnClickListener{
            finish()
            exitProcess(0)

        }

        //Game Intent
        val intentGame = Intent(this, Juego::class.java)
        val gameButton = findViewById<Button>(R.id.Jugar)

        gameButton.setOnClickListener{
            startActivity(intentGame);
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.id_preferences -> {
                val intentPreferences = Intent(this, Preferencias::class.java)
                finish()
                startActivity(intentPreferences)
                return true
            }
            R.id.id_about -> {
                val intentPreferences = Intent(this, AboutActivity::class.java)
                finish()
                startActivity(intentPreferences)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}