package com.example.memorytroncollection

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Perdedor_Pablo : AppCompatActivity() {
    var mediaPlayer: MediaPlayer?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perdedor_pablo)

        mediaPlayer= MediaPlayer.create(this,R.raw.mr)
        mediaPlayer?.seekTo(20000)
        mediaPlayer?.start()
    }

    fun Reiniciar(view: View) {
        mediaPlayer?.stop()
        val intent= Intent(this, JuegoPablo::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        mediaPlayer?.stop()
        val intent= Intent(this, JuegoPablo::class.java)
        startActivity(intent)
    }
    override fun onStop() {
        mediaPlayer?.stop()
        super.onStop()
    }
}