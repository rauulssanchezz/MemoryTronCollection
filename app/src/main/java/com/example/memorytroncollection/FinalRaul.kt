package com.example.memorytroncollection

import android.animation.Animator
import android.animation.ObjectAnimator
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
    var modo=""
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final_raul)


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        // Recupera las victorias y derrotas almacenadas en SharedPreferences
        var victoriasN=sharedPreferences.getInt("victoriasN", 0)
        var derrotasN=sharedPreferences.getInt("derrotasN", 0)

        var victoriasD=sharedPreferences.getInt("victoriasD", 0)
        var derrotasD=sharedPreferences.getInt("derrotasD", 0)


        var victorias =0
        var derrotas =0

        var resultado = intent.getStringExtra("resultado").toString()
        modo=intent.getStringExtra("modo").toString()

        var fondo = findViewById<ConstraintLayout>(R.id.background)
        var texto = findViewById<ImageView>(R.id.texto)
        var resultados = findViewById<TextView>(R.id.info)

        resultados.visibility = View.VISIBLE

        if (resultado.equals("Eres Admin")) {
            if (modo.equals("normal")) {
                fondo.setBackgroundResource(R.drawable.win_r)
                texto.setImageResource(R.drawable.admintxt_r)
                mediaPlayer = MediaPlayer.create(this, R.raw.ganador)
                victoriasN++
            }else{
                fondo.setBackgroundResource(R.drawable.victoria2)
                texto.setImageResource(R.drawable.admintxt_r)
                mediaPlayer = MediaPlayer.create(this, R.raw.ganador2_r)
                victoriasD++
            }
            mediaPlayer?.start()
            mediaPlayer?.isLooping=true
        } else {
            if (modo.equals("normal")){
                fondo.setBackgroundResource(R.drawable.lose_r)
                texto.setImageResource(R.drawable.cagastetxt_r)
                mediaPlayer = MediaPlayer.create(this, R.raw.derrota)
                derrotasN++
            }else{
                fondo.setBackgroundResource(R.drawable.perdiste2)
                texto.setImageResource(R.drawable.cagastetxt_r)
                mediaPlayer = MediaPlayer.create(this, R.raw.derrota2_r)
                derrotasD++
            }
            mediaPlayer?.seekTo(5000)
            mediaPlayer?.setVolume(1.0F,1.0F)
            mediaPlayer?.start()
            mediaPlayer?.isLooping=true
        }
        if (modo.equals("normal")) {
            with(sharedPreferences.edit()) {
                putInt("victoriasN", victoriasN)
                putInt("derrotasN", derrotasN)
                apply()
            }
            victorias=victoriasN
            derrotas=derrotasN
        }else{
            with(sharedPreferences.edit()) {
                putInt("victoriasD", victoriasD)
                putInt("derrotasD", derrotasD)
                apply()
            }
            victorias=victoriasD
            derrotas=derrotasD
        }
        resultados.text = "Victorias Totales: $victorias\nDerrotas Totales: $derrotas"

        musica=sharedPreferences.getBoolean("musica",true)

        if(!musica) {
            mediaPlayer?.stop()
        }


    }
    fun animacion(view:View,tiempoX:Long,tiempoY:Long){
        view.animate().apply {
            scaleX(0.9f)
            scaleY(0.9f)
            duration=tiempoX
        }.withEndAction {
            view.animate().apply {
                scaleX(1.0f)
                scaleY(1.0f)
                duration=tiempoY
            }
        }
    }
    override fun onBackPressed() {
        mediaPlayer?.stop()
        var intent= Intent(this,InicioRaul::class.java)
        startActivity(intent)
        super.onBackPressed()
    }

    fun reiniciar(view: View) {
        if (modo.equals("normal")) {
            intent = Intent(this, JuegoRaul::class.java)
        }else{
            intent = Intent(this, Juego2Raul::class.java)
        }
        mediaPlayer?.stop()
        animacion(view,200,200)
        view.postDelayed({startActivity(intent)},400)
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