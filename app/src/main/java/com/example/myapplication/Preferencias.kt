package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Debug
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Preferencias : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferencias)

        setSupportActionBar(findViewById(R.id.toolbar2))

        val puntajeText = findViewById<TextView>(R.id.puntaje)

        val card1View = findViewById<ListView>(R.id.listview)
        lifecycleScope.launch(Dispatchers.IO) {
            getData().collect(){
                withContext(Dispatchers.Main){
                    puntajeText.text = it.toString()
                }
            }

        }


        var nombres = listOf<String>("Tema                                                                                  ",
                                     "Dificultad                                                                            ",
                                     "Velocidad Audio                                                                       ",
                                     "Volumen                                                                               ")
        var adap = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nombres)

        card1View.adapter = adap;

        card1View.setOnItemClickListener { _, _, position, _ ->
            val builder = AlertDialog.Builder(this)
            when (position) {
                0 -> {
                    val custn = customDialog(this, "Tema", "LIGHT")
                    custn.show();
                }
                1 -> {
                    val custn = customDialog(this, "Dificultad", "5")
                    custn.show();

                }
                2 -> {
                    val custn = customDialog(this, "Velocidad Audio", "5")
                    custn.show();
                }
                3 -> {
                    val custn = customDialog(this, "Volumen", "5")
                    custn.show();
                }
                else -> Toast.makeText(this ,"Hola else", Toast.LENGTH_SHORT ).show()
            }
        }
        registerForContextMenu(card1View)



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

    private suspend fun getData() = dataStore.data.map { preferences->
        preferences[intPreferencesKey("puntaje")]
    }



}

