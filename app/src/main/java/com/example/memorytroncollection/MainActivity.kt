package com.example.memorytroncollection

import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Switch
import com.example.memorytroncollection.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var bind:ActivityMainBinding
    var mediaPlayer:MediaPlayer?=null
    var musica=true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind=ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
        mediaPlayer=MediaPlayer.create(this,R.raw.main_song)
        mediaPlayer?.start()
        mediaPlayer?.isLooping=true

    }

    fun medieval(view: View) {
        mediaPlayer?.stop()
        var intent=Intent(this,InicioRaul::class.java)
        view.animate().apply {
            scaleX(0.9f)
            scaleY(0.9f)
            duration=300
        }.withEndAction{
            view.animate().apply {
                scaleX(1.0f)
                scaleY(1.0f)
                duration=300
            }
        }
        intent.putExtra("musica",musica)
        startActivity(intent)

    }
    fun marcianos(view: View) {
        mediaPlayer?.stop()
        var intent = Intent(this,InicioMiguel::class.java)
        view.animate().apply {
            scaleX(0.9f)
            scaleY(0.9f)
            duration=300
        }.withEndAction{
            view.animate().apply {
                scaleX(1.0f)
                scaleY(1.0f)
                duration=300
            }
        }
        startActivity(intent)
    }
    fun melendi(view: View) {
        mediaPlayer?.stop()
        var intent=Intent(this,InicioPablo::class.java)
        view.animate().apply {
            scaleX(0.9f)
            scaleY(0.9f)
            duration=300
        }.withEndAction{
            view.animate().apply {
                scaleX(1.0f)
                scaleY(1.0f)
                duration=300
            }
        }
        startActivity(intent)
    }

    override fun onStop() {
        mediaPlayer?.pause()
        super.onStop()
    }

    override fun onStart() {
        if (musica){
            mediaPlayer?.start()
        }else{
            bind.music.setImageDrawable(getDrawable(R.drawable.musica_off))
        }

        super.onStart()
    }

    fun pararMusica(view: View) {
        if (musica) {
            bind.music.setImageResource(R.drawable.musica_off)
            mediaPlayer?.pause()
            musica=false

        }else{
            bind.music.setImageResource(R.drawable.musica_on)
            mediaPlayer?.start()
            musica=true
        }
    }
}