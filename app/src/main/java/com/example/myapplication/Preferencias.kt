package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

class Preferencias : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferencias)

        setSupportActionBar(findViewById(R.id.toolbar2))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        setSupportActionBar(findViewById(R.id.toolbar2))
        menuInflater.inflate(R.menu.menupreferences, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.id_save -> {

                return true
            }
            R.id.id_back -> {
                val intentMain = Intent(this, MainActivity::class.java)
                finish()
                startActivity(intentMain)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


}

