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
    lateinit var sharedPreferences:SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind=ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        musica = sharedPreferences.getBoolean("musica", true)
        mediaPlayer=MediaPlayer.create(this,R.raw.main_song)
        mediaPlayer?.setVolume(0.8F,0.8F)
        mediaPlayer?.start()
        mediaPlayer?.isLooping=true
        if (!musica){
            mediaPlayer?.pause()
        }
    }

    fun medieval(view: View) {
        mediaPlayer?.stop()
        var intent=Intent(this,InicioRaul::class.java)
        view.animate().apply {
            scaleX(0.9f)
            scaleY(0.9f)
            duration=200
        }.withEndAction{
            view.animate().apply {
                scaleX(1.0f)
                scaleY(1.0f)
                duration=200
            }
        }
        view.postDelayed({startActivity(intent)},600)


    }
    fun marcianos(view: View) {
        mediaPlayer?.stop()
        var intent = Intent(this,InicioMiguel::class.java)
        view.animate().apply {
            scaleX(0.9f)
            scaleY(0.9f)
            duration=200
        }.withEndAction{
            view.animate().apply {
                scaleX(1.0f)
                scaleY(1.0f)
                duration=200
            }
        }
        view.postDelayed({startActivity(intent)},600)
    }
    fun melendi(view: View) {
        mediaPlayer?.stop()
        var intent=Intent(this,InicioPablo::class.java)
        view.animate().apply {
            scaleX(0.9f)
            scaleY(0.9f)
            duration=200
        }.withEndAction{
            view.animate().apply {
                scaleX(1.0f)
                scaleY(1.0f)
                duration=200
            }
        }
        view.postDelayed({startActivity(intent)},600)
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
        if (musica) {
            bind.music.setImageResource(R.drawable.musica_off)
            mediaPlayer?.pause()
            musica=false
            sharedPreferences.edit().apply {
                putBoolean("musica",musica)
                apply()
            }

        }else{
            bind.music.setImageResource(R.drawable.musica_on)
            mediaPlayer?.start()
            musica=true
            sharedPreferences.edit().apply {
                putBoolean("musica",musica)
                apply()
            }
        }
    }
}