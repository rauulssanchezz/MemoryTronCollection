package com.example.memorytroncollection

import android.animation.ObjectAnimator
import android.animation.Animator
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.os.Bundle
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
    var pulsado: MutableList<Boolean> = MutableList(12) { false }
    var posant: Int? = null
    var cont = 0
    var semaforo = Semaphore(1)
    var gana = 0
    var vidas = 4
    var mediaPlayer: MediaPlayer? = null

    // Utiliza CountDownLatch para la sincronización
    private val latch = CountDownLatch(1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego_raul)

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

        musica = intent.getBooleanExtra("musica", musica)
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
        imageViews[pos].setImageResource(imgs[pos])
        pulsado[pos] = true
        cont++
        if (ultimg == null) {
            primerclick = false
        } else {
            primerclick = true
        }
    }

    //La segunda parte donde se compara si las imagenes son iguales o no teniendo en cuenta si es la primera foto que se pulsa o no
    fun segundaparte(pos: Int) {
        if (primerclick) {
            Thread(Runnable {
                val iguales = comprobar(imageViews[pos], ultimg!!)
                runOnUiThread {
                    procesarResultado(pos, iguales)
                }
            }).start()
        } else {
            ultimg = imageViews[pos]
            posant = pos
        }
    }


    //funcion que compara  que dependiendo si las imagenes son iguales o no responde lo que toque
    private fun procesarResultado(pos: Int, iguales: Boolean) {
        try {
            semaforo.acquire()

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
                Thread.sleep(500)
                imageViews[pos].setImageResource(R.drawable.parteatras_r)
                ultimg!!.setImageResource(R.drawable.parteatras_r)
                pulsado[pos] = false
                pulsado[posant!!] = false
                ultimg = null
                posant = null
                cont = 0
                vidas--
                if (vidas == 3) {
                    var imagen = findViewById<ImageView>(R.id.vida4)
                    imagen.setImageResource(R.drawable.vidamns_r)
                } else if (vidas == 2) {
                    var imagen = findViewById<ImageView>(R.id.vida3)
                    imagen.setImageResource(R.drawable.vidamns_r)
                } else if (vidas == 1) {
                    var imagen = findViewById<ImageView>(R.id.vida2)
                    imagen.setImageResource(R.drawable.vidamns_r)
                } else if (vidas == 0) {
                    var imagen = findViewById<ImageView>(R.id.vida1)
                    imagen.setImageResource(R.drawable.vidamns_r)
                    Thread.sleep(300)
                    var resultado = "Cagaste"
                    newActivity(resultado)
                }
            }
        } finally {
            semaforo.release()
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

    //funcion que ejecuta la animación de las cartas y ejecuta la primera y segunda parte si ya ya
    // lo se, la animacion obviamente he mirao como hacerla con chatgpt pero le da el toque no me jodas
    fun accion(pos: Int, view: View) {
        if (!pulsado[pos]) {
            animacion(imageViews[pos],150,100)
            primeraparte(pos)
            view.postDelayed({ segundaparte(pos) }, 0)
        }
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