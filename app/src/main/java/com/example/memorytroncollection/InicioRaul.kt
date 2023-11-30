package com.example.memorytroncollection

import android.animation.Animator
import android.animation.ObjectAnimator
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
        setContentView(R.layout.activity_inicio_raul)
        musica=intent.getBooleanExtra("musica",musica)
        mediaPlayer= MediaPlayer.create(this,R.raw.inicio)
        mediaPlayer?.setVolume(0.3F,0.3F)
        mediaPlayer?.start()
        if (!musica) {
            mediaPlayer?.stop()
        }
    }

    fun jugar(view: View) {
        val intent= Intent(this,JuegoRaul::class.java)
        intent.putExtra("musica",musica)
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
        val scaleXAnimator = ObjectAnimator.ofFloat(view, View.SCALE_X, 0.9f)
        val scaleYAnimator = ObjectAnimator.ofFloat(view, View.SCALE_Y, 0.9f)

        val animatorListener = object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
            }

            override fun onAnimationEnd(animation: Animator) {
                val scaleXAnimatorBack = ObjectAnimator.ofFloat(view, View.SCALE_X, 1.0f)
                val scaleYAnimatorBack = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1.0f)

                scaleXAnimatorBack.duration = tiempoX
                scaleYAnimatorBack.duration = tiempoY
                scaleXAnimatorBack.start()
                scaleYAnimatorBack.start()
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }
        }

        scaleXAnimator.addListener(animatorListener)
        scaleYAnimator.addListener(animatorListener)

        scaleXAnimator.duration = tiempoX
        scaleYAnimator.duration = tiempoY

        scaleXAnimator.start()
        scaleYAnimator.start()
    }

    override fun onBackPressed() {
        mediaPlayer?.stop()
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        super.onBackPressed()
    }
}