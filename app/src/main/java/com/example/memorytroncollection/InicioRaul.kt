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

class InicioRaul : AppCompatActivity() {
    var mediaPlayer: MediaPlayer?=null
    var musica=true
    lateinit var sharedPreferences:SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_raul)
        sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this)
        musica=sharedPreferences.getBoolean("musica",true)
        mediaPlayer= MediaPlayer.create(this,R.raw.inicio)
        mediaPlayer?.setVolume(0.3F,0.3F)
        mediaPlayer?.start()
        if (!musica) {
            mediaPlayer?.stop()
        }
    }

    fun jugar(view: View) {
        val intent= Intent(this,JuegoRaul::class.java)
        mediaPlayer?.stop()
        mediaPlayer= MediaPlayer.create(this,R.raw.boton)
        mediaPlayer?.seekTo(900)
        mediaPlayer?.start()
        mediaPlayer?.setVolume(1.0F,1.0F)
        animacion(view,200,200)
        view.postDelayed({startActivity(intent)},400)
    }

    override fun onStop() {
        mediaPlayer?.pause()
        super.onStop()
    }

    override fun onStart() {
        mediaPlayer?.start()
        super.onStart()
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
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        super.onBackPressed()
    }
    fun jugar2(view: View) {
        val intent = Intent(this, Juego2Raul::class.java)
        intent.putExtra("musica", musica)
        mediaPlayer?.stop()
        mediaPlayer = MediaPlayer.create(this, R.raw.boton)
        mediaPlayer?.seekTo(900)
        mediaPlayer?.start()
        mediaPlayer?.setVolume(1.0F, 1.0F)
        animacion(view, 200, 200)
        view.postDelayed({ startActivity(intent) }, 400)
    }
}