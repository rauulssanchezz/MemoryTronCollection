package com.example.memorytroncollection

import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class FinalRaul : AppCompatActivity() {
    var mediaPlayer: MediaPlayer? = null
    var musica = true

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final_raul)


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        // Recupera las victorias y derrotas almacenadas en SharedPreferences
        var victorias = sharedPreferences.getInt("victoriasRaul", 0)
        var derrotas = sharedPreferences.getInt("derrotasRaul", 0)

        var resultado = intent.getStringExtra("resultado").toString()

        var fondo = findViewById<ConstraintLayout>(R.id.background)
        var texto = findViewById<ImageView>(R.id.texto)
        var resultados = findViewById<TextView>(R.id.info)

        resultados.visibility = View.VISIBLE

        if (resultado.equals("Eres Admin")) {
            fondo.setBackgroundResource(R.drawable.win_r)
            texto.setImageResource(R.drawable.admintxt_r)
            mediaPlayer = MediaPlayer.create(this, R.raw.ganador)
            mediaPlayer?.start()
            victorias++
        } else {
            fondo.setBackgroundResource(R.drawable.lose_r)
            texto.setImageResource(R.drawable.cagastetxt_r)
            mediaPlayer = MediaPlayer.create(this, R.raw.derrota)
            mediaPlayer?.seekTo(5000)
            mediaPlayer?.setVolume(1.0F,1.0F)
            mediaPlayer?.start()
            derrotas++
        }
        with(sharedPreferences.edit()) {
            putInt("victoriasRaul", victorias)
            putInt("derrotasRaul", derrotas)
            apply()
        }
        resultados.text = "Victorias Totales: $victorias\nDerrotas Totales: $derrotas"

        musica=intent.getBooleanExtra("musica",musica)

        if(!musica) {
            mediaPlayer?.stop()
        }


    }
    override fun onBackPressed() {
        mediaPlayer?.stop()
        var intent= Intent(this,JuegoRaul::class.java)
        startActivity(intent)
        super.onBackPressed()
    }

    fun reiniciar(view: View) {
        var intent= Intent(this,JuegoRaul::class.java)
        mediaPlayer?.stop()
        startActivity(intent)
    }
    override fun onStop() {
        mediaPlayer?.pause()
        super.onStop()
    }

    override fun onResume() {
        mediaPlayer?.start()
        super.onResume()
    }
}