package com.example.memorytroncollection

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Ganador_Pablo : AppCompatActivity() {
    var mediaPlayer: MediaPlayer?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ganador_pablo)


        mediaPlayer= MediaPlayer.create(this,R.raw.ibai)
        mediaPlayer?.seekTo(3000)
        mediaPlayer?.start()
    }
    fun Reiniciar(view: View) {
        mediaPlayer?.stop()
        val intent= Intent(this, Juego::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        mediaPlayer?.stop()
        val intent= Intent(this, Juego::class.java)
        startActivity(intent)
    }

    override fun onStop() {
        mediaPlayer?.stop()
        super.onStop()
    }
}