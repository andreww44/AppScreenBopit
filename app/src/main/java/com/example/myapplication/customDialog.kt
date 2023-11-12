package com.example.myapplication

import android.app.Dialog
import android.content.Context
import android.media.session.PlaybackState.CustomAction
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.myapplication.R




class customDialog(
    context: Context,
    private val title:String?,
    private var numero: String,

    ): Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.cardviewbuildevolume)
        val titletext = findViewById<TextView>(R.id.titulotext)
        val numerotext = findViewById<TextView>(R.id.bnumerotex)
        val butonmas = findViewById<Button>(R.id.mas)
        val butonmenos = findViewById<Button>(R.id.menos)
        val butonaceptar = findViewById<Button>(R.id.aceptar)

        titletext.text = title;
        numerotext.text = numero

        butonmas.setOnClickListener(){
        }

        butonmenos.setOnClickListener(){
        }
        butonaceptar.setOnClickListener(){
            dismiss()
        }


        //seekbar.setOnSeekBarChangeListener()
    }



}