package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class Preferencias : AppCompatActivity() {

    val u2 = "Alvaro"
    val p2 = "1234"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferencias)

        setSupportActionBar(findViewById(R.id.toolbar2))

        val loginButton = findViewById<Button>(R.id.Login)

        val user = findViewById<EditText>(R.id.UserLog);
        val password = findViewById<EditText>(R.id.Password);
        val userlog = findViewById<TextView>(R.id.User);

        loginButton.setOnClickListener{
            if(user.getText().toString().equals(u2.toString()) && password.getText().toString().equals(p2.toString())){
                userlog.setText("Score: 2000")
            }
            else
            {
                userlog.setText("Contraseña Incorrecta")
            }

        }
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

