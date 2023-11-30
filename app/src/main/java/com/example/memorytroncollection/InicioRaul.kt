package com.example.memorytroncollection

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class InicioRaul : AppCompatActivity() {
    var mediaPlayer: MediaPlayer?=null
    var musica=true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mediaPlayer= MediaPlayer.create(this,R.raw.inicio)
        mediaPlayer?.setVolume(0.3F,0.3F)
        mediaPlayer?.start()
    }

    fun jugar(view: View) {
        val intent= Intent(this,JuegoRaul::class.java)
        intent.putExtra("musica",musica)
        mediaPlayer?.stop()
        mediaPlayer= MediaPlayer.create(this,R.raw.boton)
        mediaPlayer?.seekTo(900)
        mediaPlayer?.start()
        mediaPlayer?.setVolume(1.0F,1.0F)
        Thread.sleep(250)
        startActivity(intent)
    }

    override fun onStop() {
        mediaPlayer?.pause()
        super.onStop()
    }

    override fun onStart() {
        mediaPlayer?.start()
        super.onStart()
    }

    fun pararMusica(view: View) {
        if (musica) {
            mediaPlayer?.pause()
            musica=false
        }else{
            mediaPlayer?.start()
            musica=true
        }
    }
}