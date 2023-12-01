package com.example.memorytroncollection

import android.animation.ObjectAnimator
import android.animation.Animator
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Semaphore

class JuegoRaul : AppCompatActivity() {
    var musica = true
    lateinit var imgs: MutableList<Int>
    var ultimg: ImageView? = null
    var primerclick = false
    lateinit var imageViews: MutableList<ImageView>
    var pulsados: MutableList<Boolean> = MutableList(12) { false }
    var posant: Int? = null
    var cont = 0
    var gana = 0
    var vidas = 4
    var mediaPlayer: MediaPlayer? = null
    lateinit var sharedPreferences:SharedPreferences
    lateinit var  handler :Handler
    lateinit var semaforo:Semaphore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego_raul)

        handler=Handler(Looper.getMainLooper())
        semaforo=Semaphore(1)


        imgs = mutableListOf(
            R.drawable.carta1_r,
            R.drawable.carta2_r,
            R.drawable.carta3_r,
            R.drawable.carta4_r,
            R.drawable.carta5_r,
            R.drawable.carta6_r,
            R.drawable.carta1_r,
            R.drawable.carta2_r,
            R.drawable.carta3_r,
            R.drawable.carta4_r,
            R.drawable.carta5_r,
            R.drawable.carta6_r
        )
        imgs.shuffle()
        imageViews = mutableListOf(
            findViewById<ImageView>(R.id.ct1img1),
            findViewById<ImageView>(R.id.ct1img2),
            findViewById<ImageView>(R.id.ct1img3),
            findViewById<ImageView>(R.id.ct1img4),
            findViewById<ImageView>(R.id.ct2img1),
            findViewById<ImageView>(R.id.ct2img2),
            findViewById<ImageView>(R.id.ct2img3),
            findViewById<ImageView>(R.id.ct2img4),
            findViewById<ImageView>(R.id.ct3img1),
            findViewById<ImageView>(R.id.ct3img2),
            findViewById<ImageView>(R.id.ct3img3),
            findViewById<ImageView>(R.id.ct3img4)
        )

        sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this)
        musica = sharedPreferences.getBoolean("musica",true)
        mediaPlayer = MediaPlayer.create(this, R.raw.juego)
        mediaPlayer?.setVolume(0.5F, 0.5F)
        mediaPlayer?.start()
        if (!musica) {
            mediaPlayer?.stop()
        }
    }


    //funcion que compara
    fun comprobar(img1: ImageView, img2: ImageView): Boolean {
        // Compara las imágenes para ver si son iguales
        var drawable1 = img1.drawable
        var drawable2 = img2.drawable
        var bitmap1 = Bitmap.createBitmap(
            drawable1.intrinsicWidth,
            drawable1.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        var bitmap2 = Bitmap.createBitmap(
            drawable2.intrinsicWidth,
            drawable2.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        var canvas1 = android.graphics.Canvas(bitmap1)
        var canvas2 = android.graphics.Canvas(bitmap2)
        drawable1.setBounds(0, 0, canvas1.width, canvas1.height)
        drawable2.setBounds(0, 0, canvas2.width, canvas2.height)
        drawable1.draw(canvas1)
        drawable2.draw(canvas2)
        return bitmap1.sameAs(bitmap2)
    }

    fun primeraparte(pos: Int) {
        // Configura la primera parte del juego al hacer clic en una imagen
        if (cont<2) {
            imageViews[pos].setImageResource(imgs[pos])
            pulsados[pos] = true
            cont++
            if (ultimg == null) {
                primerclick = false
            } else {
                primerclick = true
            }
        }
    }

    //La segunda parte donde se compara si las imagenes son iguales o no teniendo en cuenta si es la primera foto que se pulsa o no
    fun segundaparte(pos: Int) {
        semaforo.acquire()
        if (primerclick && cont==2) {
            val iguales = comprobar(imageViews[pos], ultimg!!)
            procesarResultado(pos, iguales)
        } else {
            ultimg = imageViews[pos]
            posant = pos
        }
        semaforo.release()
    }


    //funcion que compara  que dependiendo si las imagenes son iguales o no responde lo que toque
    private fun procesarResultado(pos: Int, iguales: Boolean) {

            if (iguales) {
                ultimg = null
                cont = 0
                gana++
                if (gana == 6) {
                    Thread.sleep(250)
                    var resultado = "Eres Admin"
                    newActivity(resultado)
                }
            } else if (cont == 2) {
                Thread.sleep(400)
                animacion(imageViews[pos],100,150)
                animacion(imageViews[posant!!],100,150)
                imageViews[pos].setImageResource(R.drawable.parteatras_r)
                ultimg!!.setImageResource(R.drawable.parteatras_r)
                pulsados[pos] = false
                pulsados[posant!!] = false
                ultimg = null
                posant = null
                cont = 0
                vidas--
                if (vidas == 3) {
                    var imagen = findViewById<ImageView>(R.id.vida4)
                    animacion(imagen,50,150)
                    imagen.setImageResource(R.drawable.vidamns_r)
                } else if (vidas == 2) {
                    var imagen = findViewById<ImageView>(R.id.vida3)
                    animacion(imagen,50,150)
                    imagen.setImageResource(R.drawable.vidamns_r)
                } else if (vidas == 1) {
                    var imagen = findViewById<ImageView>(R.id.vida2)
                    animacion(imagen,50,150)
                    imagen.setImageResource(R.drawable.vidamns_r)
                } else if (vidas == 0) {
                    var imagen = findViewById<ImageView>(R.id.vida1)
                    animacion(imagen,50,150)
                    imagen.setImageResource(R.drawable.vidamns_r)
                    Thread.sleep(300)
                    var resultado = "Cagaste"
                    newActivity(resultado)
                }
            }
        }

    // Funcion onClick puesta en el xml

    fun pulsado(view: View) {
        when (view.id) {
            R.id.ct1img1 -> {
                var pos = 0
                accion(pos, view)
            }
            R.id.ct1img2 -> {
                var pos = 1
                accion(pos, view)
            }
            R.id.ct1img3 -> {
                var pos = 2
                accion(pos, view)
            }
            R.id.ct1img4 -> {
                var pos = 3
                accion(pos, view)
            }
            R.id.ct2img1 -> {
                var pos = 4
                accion(pos, view)
            }
            R.id.ct2img2 -> {
                var pos = 5
                accion(pos, view)
            }
            R.id.ct2img3 -> {
                var pos = 6
                accion(pos, view)
            }
            R.id.ct2img4 -> {
                var pos = 7
                accion(pos, view)
            }
            R.id.ct3img1 -> {
                var pos = 8
                accion(pos, view)
            }
            R.id.ct3img2 -> {
                var pos = 9
                accion(pos, view)
            }
            R.id.ct3img3 -> {
                var pos = 10
                accion(pos, view)
            }
            R.id.ct3img4 -> {
                var pos = 11
                accion(pos, view)
            }
        }
    }


    //La animacion no se que mas quieres si se llama asi la funcion
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

    fun accion(pos: Int, view: View) {
        animacion(imageViews[pos],150,100)
        view.postDelayed({
            if (!pulsados[pos]) {
                Thread{
                    semaforo.acquire()
                    primeraparte(pos)
                    handler.post {
                        semaforo.release()
                        segundaparte(pos)
                    }
                }.start()
            }
        },250)
    }

    // funcion para pasar al resultado final

    fun newActivity(res: String) {
        var intent = Intent(this, FinalRaul::class.java)
        intent.putExtra("resultado", res)
        intent.putExtra("musica", musica)
        mediaPlayer?.stop()
        startActivity(intent)
    }

    override fun onBackPressed() {
        mediaPlayer?.stop()
        var intent=Intent(this,InicioRaul::class.java)
        startActivity(intent)
        super.onBackPressed()
    }

    fun reiniciar(view: View) {
        var intent=Intent(this,JuegoRaul::class.java)
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
