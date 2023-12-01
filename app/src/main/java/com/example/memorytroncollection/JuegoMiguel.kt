package com.example.memorytroncollection

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import androidx.core.os.postDelayed
import com.example.memorytroncollection.databinding.ActivityJuegoMiguelBinding
import kotlinx.coroutines.sync.Semaphore

class JuegoMiguel : AppCompatActivity() {
    //    Musica
    private var musica: MediaPlayer? = null
    private var musicaChampion: MediaPlayer? = null
    private var sonidogracioso: MediaPlayer? = null
    private var giroR: MediaPlayer? = null
    private var musicaBoolean = true

    //    Binding
    private lateinit var bind: ActivityJuegoMiguelBinding

    //    Cartas
    private lateinit var cartas: MutableList<Int>
    private var volteada = MutableList(12) { false }

    //Recojo la variable que indica si es modo secreto
    private var oculto = false

    //    Variables de la partida
    private var terminado = false
    private var primero = true
    private var vidas = 5
    private var contadorparejas = 0

    //    Variables para comparar cartas
    private var carta1: Drawable? = null
    private var carta2: Drawable? = null
    private var vista1: ImageView? = null
    private var vista2: ImageView? = null
    private var indice1: Int = -1
    private var indice2: Int = -1

    //    Semaforo que controla cada jugada
    private var semaphore = Semaphore(1)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityJuegoMiguelBinding.inflate(layoutInflater)
        setContentView(bind.root)


        oculto = intent.getBooleanExtra("oculto", false)
        //Si ha activado el modo secreto
        if (oculto) {
            //Pongo las cartas rusas
            cartas = mutableListOf(
                R.drawable.ruso1_m,
                R.drawable.ruso2_m,
                R.drawable.ruso3_m,
                R.drawable.ruso4_m,
                R.drawable.ruso5_m,
                R.drawable.ruso6_m,
                R.drawable.ruso1_m,
                R.drawable.ruso2_m,
                R.drawable.ruso3_m,
                R.drawable.ruso4_m,
                R.drawable.ruso5_m,
                R.drawable.ruso6_m
            )

            //Inicio la musica rusa
            musica = MediaPlayer.create(this, R.raw.tripaloski)
            musica?.start()
            giroR = MediaPlayer.create(this, R.raw.giror)
            bind.music.visibility = View.GONE
            //Pongo fondo ruso
            bind.juego.setBackgroundResource(R.drawable.tripaloski_m)
            //Pongo textos en ruso
            bind.textView.text = getString(R.string.derrotaR)
            bind.textView.text = getString(R.string.victoriaR)
            bind.textView.setBackgroundColor(getColor(R.color.yellow))

        } else {
            //Pongo las cartas estandar
            cartas = mutableListOf(
                R.drawable.carta1_m,
                R.drawable.carta2_m,
                R.drawable.carta3_m,
                R.drawable.carta4_m,
                R.drawable.carta5_m,
                R.drawable.carta6_m,
                R.drawable.carta1_m,
                R.drawable.carta2_m,
                R.drawable.carta3_m,
                R.drawable.carta4_m,
                R.drawable.carta5_m,
                R.drawable.carta6_m
            )

            //Inicio la musica
            musica = MediaPlayer.create(this, R.raw.wade)
            musica?.setVolume(0.8F,0.8F)
            musica?.start()
        }

        //Desordeno las cartas
        cartas.shuffle()

        //Inicio cronometro
        bind.chronometer.start()

        //Funcion boton restart
        bind.restart.setOnClickListener {
            //Paro todas las canciones
            musicaChampion?.stop()
            sonidogracioso?.stop()
            recreate()
        }


    }

    //Override para cuando estas en segundo plano
    override fun onStop() {
        musica?.pause()
        musicaChampion?.pause()
        sonidogracioso?.pause()
        super.onStop()
    }

    //Override para cuando vuelves a la app
    override fun onResume() {

        musica?.start()

        super.onResume()
    }


    //Override para cuando retrocedes
    override fun onBackPressed() {
        musica?.stop()
        musicaChampion?.stop()
        sonidogracioso?.stop()
        super.onBackPressed()
    }

    //Funcion para quitar vidas
    private fun vidaMenos() {
        //Controlo que corazon cambiar segun las vidas
        when (vidas) {
            5 -> {
                //Pongo el corazon roto
                bind.coraM5.setImageResource(R.drawable.cora_animacion_m)

                //Al medio segundo se pone el corazon vacio
                Handler(Looper.getMainLooper()).postDelayed({
                    bind.coraM5.setImageResource(R.drawable.cora_vacio_m)

                }, 500)

            }

            4 -> {
                //...
                bind.coraM4.setImageResource(R.drawable.cora_animacion_m)

                //...
                Handler(Looper.getMainLooper()).postDelayed({
                    bind.coraM4.setImageResource(R.drawable.cora_vacio_m)

                }, 500)

            }

            3 -> {
                bind.coraM3.setImageResource(R.drawable.cora_animacion_m)

                Handler(Looper.getMainLooper()).postDelayed({
                    bind.coraM3.setImageResource(R.drawable.cora_vacio_m)

                }, 500)
            }

            2 -> {
                bind.coraM2.setImageResource(R.drawable.cora_animacion_m)

                Handler(Looper.getMainLooper()).postDelayed({
                    bind.coraM2.setImageResource(R.drawable.cora_vacio_m)

                }, 500)
            }

            1 -> {
                bind.coraM1.setImageResource(R.drawable.cora_animacion_m)

                Handler(Looper.getMainLooper()).postDelayed({
                    bind.coraM1.setImageResource(R.drawable.cora_vacio_m)

                }, 500)
            }
        }

        //Le bajo una vida
        vidas--

        //Compruebo el numero de vidas
        if (vidas == 0) {
            //Muestro mensajes de derrota
            bind.card.visibility = View.VISIBLE
            bind.restart.visibility = View.VISIBLE
            //Paro el cronometro
            bind.chronometer.stop()
            musica?.stop()
            //Compruebo el modo
            if (oculto) {
                //Inicio la musica
                sonidogracioso = MediaPlayer.create(this, R.raw.sonidogracioso)
                sonidogracioso!!.start()
            }

        }
    }

    //Funcion para comparar dos cartas
    private fun comprobarPareja(i1: ImageView, i2: ImageView): Boolean {

        //Convierto los ImageView a Drawable
        val d1 = i1.drawable
        val d2 = i2.drawable

        //Creo un mapa de bit para cada Drawable
        val bitmap1 =
            Bitmap.createBitmap(d1.intrinsicWidth, d1.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val bitmap2 =
            Bitmap.createBitmap(d2.intrinsicWidth, d2.intrinsicHeight, Bitmap.Config.ARGB_8888)

        //Creo dos canvas para el diseño
        val canvas1 = Canvas(bitmap1)
        val canvas2 = Canvas(bitmap2)

        d1.setBounds(0, 0, canvas1.width, canvas1.height)
        d2.setBounds(0, 0, canvas2.width, canvas2.height)

        d1.draw(canvas1)
        d2.draw(canvas2)

        //Comparara las dos imagenes visualmente
        return bitmap1.sameAs(bitmap2)

    }

    //Funcion para mostrar la carta
    private fun mostrar(i: ImageView?, r: Int) {
        if (oculto) {
            giroR?.start()
        }

        i?.setImageResource(cartas[r])
        volteada[r] = true
    }

    //Funcion para ocultar la carta
    private fun ocultar(i: ImageView?, r: Int) {
        i?.setImageResource(R.drawable.trasera_m)
        volteada[r] = false
    }

    //Funcion para comprobar que ya se han encontrado todas las parejas
    private fun comprobarFin() {
        contadorparejas++
        if (contadorparejas == 6) {
            terminado = true

            bind.card.visibility = View.VISIBLE
            bind.restart.visibility = View.VISIBLE

            bind.chronometer.stop()
            val tiempo = bind.chronometer.text.toString()

            bind.textView.text = getString(R.string.victoria) + "\n" + tiempo

            musica?.stop()
            musica?.release()

            musicaChampion = MediaPlayer.create(this, R.raw.campeon)
            musicaChampion!!.start()
        }
    }

    private fun animacion(view: View, tiempoX: Long, tiempoY: Long) {
        view.animate().apply {
            scaleX(0.9f)
            scaleY(0.9f)
            duration = tiempoX
        }.withEndAction {
            view.animate().apply {
                scaleX(1.0f)
                scaleY(1.0f)
                duration = tiempoY
            }
        }
    }

    fun pararMusica(view: View) {
        view.animate().apply {
            scaleX(0.9f)
            scaleY(0.9f)
            duration = 300
        }.withEndAction {
            view.animate().apply {
                scaleX(1.0f)
                scaleY(1.0f)
                duration = 300
            }
        }
        if (musicaBoolean) {
            bind.music.setImageResource(R.drawable.musica_off_dark)
            musica?.pause()
            musicaBoolean = false

        } else {
            bind.music.setImageResource(R.drawable.musica_on_dark)
            musica?.start()
            musicaBoolean = true

        }
    }

    //Funcion para cada vez que se pincha una carta
    fun click(view: View) {

        if (vidas > 0 && !terminado) {
            if (semaphore.tryAcquire()) {
                when (view.id) {
                    R.id.c1 -> {
                        if (!volteada[0]) {
                            animacion(view, 150, 100)
                            if (primero) {
                                vista1 = bind.c1
                                indice1 = 0
                                carta1 = cartas[indice1].toDrawable()
                                mostrar(vista1, indice1)
                                primero = false
                                semaphore.release()

                            } else {
                                vista2 = bind.c1
                                indice2 = 0
                                carta2 = cartas[indice2].toDrawable()
                                mostrar(vista2, indice2)

                                if (comprobarPareja(vista1!!, vista2!!)) {
                                    vista1 = null
                                    vista2 = null
                                    primero = true
                                    comprobarFin()
                                    semaphore.release()
                                } else {
                                    Handler(Looper.getMainLooper()).postDelayed({
                                        ocultar(vista1, indice1)
                                        ocultar(vista2, indice2)
                                        primero = true
                                        vidaMenos()
                                        semaphore.release()
                                    }, 1000)

                                }

                            }

                        } else {
                            semaphore.release()
                        }


                    }

                    R.id.c2 -> {
                        if (!volteada[1]) {
                            animacion(view, 150, 100)
                            if (primero) {
                                vista1 = bind.c2
                                indice1 = 1
                                carta1 = cartas[indice1].toDrawable()
                                mostrar(vista1, indice1)
                                primero = false
                                semaphore.release()

                            } else {
                                vista2 = bind.c2
                                indice2 = 1
                                carta2 = cartas[indice2].toDrawable()
                                mostrar(vista2, indice2)

                                if (comprobarPareja(vista1!!, vista2!!)) {
                                    vista1 = null
                                    vista2 = null
                                    primero = true
                                    comprobarFin()
                                    semaphore.release()
                                } else {
                                    Handler(Looper.getMainLooper()).postDelayed({
                                        ocultar(vista1, indice1)
                                        ocultar(vista2, indice2)
                                        primero = true
                                        vidaMenos()
                                        semaphore.release()
                                    }, 1000)
                                }
                            }

                        } else {
                            semaphore.release()
                        }


                    }

                    R.id.c3 -> {
                        if (!volteada[2]) {
                            animacion(view, 150, 100)
                            if (primero) {
                                vista1 = bind.c3
                                indice1 = 2
                                carta1 = cartas[indice1].toDrawable()
                                mostrar(vista1, indice1)
                                primero = false
                                semaphore.release()

                            } else {
                                vista2 = bind.c3
                                indice2 = 2
                                carta2 = cartas[indice2].toDrawable()
                                mostrar(vista2, indice2)

                                if (comprobarPareja(vista1!!, vista2!!)) {
                                    vista1 = null
                                    vista2 = null
                                    primero = true
                                    comprobarFin()
                                    semaphore.release()
                                } else {
                                    Handler(Looper.getMainLooper()).postDelayed({
                                        ocultar(vista1, indice1)
                                        ocultar(vista2, indice2)
                                        primero = true
                                        vidaMenos()
                                        semaphore.release()
                                    }, 1000)
                                }

                            }

                        } else {
                            semaphore.release()
                        }

                    }

                    R.id.c4 -> {
                        if (!volteada[3]) {
                            animacion(view, 150, 100)
                            if (primero) {
                                vista1 = bind.c4
                                indice1 = 3
                                carta1 = cartas[indice1].toDrawable()
                                mostrar(vista1, indice1)
                                primero = false
                                semaphore.release()

                            } else {
                                vista2 = bind.c4
                                indice2 = 3
                                carta2 = cartas[indice2].toDrawable()
                                mostrar(vista2, indice2)

                                if (comprobarPareja(vista1!!, vista2!!)) {
                                    vista1 = null
                                    vista2 = null
                                    primero = true
                                    comprobarFin()
                                    semaphore.release()
                                } else {

                                    Handler(Looper.getMainLooper()).postDelayed({
                                        ocultar(vista1, indice1)
                                        ocultar(vista2, indice2)
                                        primero = true
                                        vidaMenos()
                                        semaphore.release()
                                    }, 1000)
                                }

                            }
                        } else {
                            semaphore.release()
                        }


                    }

                    R.id.c5 -> {
                        if (!volteada[4]) {
                            animacion(view, 150, 100)
                            if (primero) {
                                vista1 = bind.c5
                                indice1 = 4
                                carta1 = cartas[indice1].toDrawable()
                                mostrar(vista1, indice1)
                                primero = false
                                semaphore.release()

                            } else {
                                vista2 = bind.c5
                                indice2 = 4
                                carta2 = cartas[indice2].toDrawable()
                                mostrar(vista2, indice2)

                                if (comprobarPareja(vista1!!, vista2!!)) {
                                    vista1 = null
                                    vista2 = null
                                    primero = true
                                    comprobarFin()
                                    semaphore.release()
                                } else {

                                    Handler(Looper.getMainLooper()).postDelayed({
                                        ocultar(vista1, indice1)
                                        ocultar(vista2, indice2)
                                        primero = true
                                        vidaMenos()
                                        semaphore.release()
                                    }, 1000)
                                }
                            }

                        } else {
                            semaphore.release()
                        }

                    }

                    R.id.c6 -> {
                        if (!volteada[5]) {
                            animacion(view, 150, 100)
                            if (primero) {
                                vista1 = bind.c6
                                indice1 = 5
                                carta1 = cartas[indice1].toDrawable()
                                mostrar(vista1, indice1)
                                primero = false
                                semaphore.release()

                            } else {
                                vista2 = bind.c6
                                indice2 = 5
                                carta2 = cartas[indice2].toDrawable()
                                mostrar(vista2, indice2)

                                if (comprobarPareja(vista1!!, vista2!!)) {
                                    vista1 = null
                                    vista2 = null
                                    primero = true
                                    comprobarFin()
                                    semaphore.release()
                                } else {
                                    Handler(Looper.getMainLooper()).postDelayed({
                                        ocultar(vista1, indice1)
                                        ocultar(vista2, indice2)
                                        primero = true
                                        vidaMenos()
                                        semaphore.release()
                                    }, 1000)
                                }
                            }

                        } else {
                            semaphore.release()
                        }
                    }

                    R.id.c7 -> {
                        if (!volteada[6]) {
                            animacion(view, 150, 100)
                            if (primero) {
                                vista1 = bind.c7
                                indice1 = 6
                                carta1 = cartas[indice1].toDrawable()
                                mostrar(vista1, indice1)
                                primero = false
                                semaphore.release()

                            } else {
                                vista2 = bind.c7
                                indice2 = 6
                                carta2 = cartas[indice2].toDrawable()
                                mostrar(vista2, indice2)

                                if (comprobarPareja(vista1!!, vista2!!)) {
                                    vista1 = null
                                    vista2 = null
                                    primero = true
                                    comprobarFin()
                                    semaphore.release()
                                } else {
                                    Handler(Looper.getMainLooper()).postDelayed({
                                        ocultar(vista1, indice1)
                                        ocultar(vista2, indice2)
                                        primero = true
                                        vidaMenos()
                                        semaphore.release()
                                    }, 1000)
                                }
                            }

                        } else {
                            semaphore.release()
                        }
                    }

                    R.id.c8 -> {
                        if (!volteada[7]) {
                            animacion(view, 150, 100)
                            if (primero) {
                                vista1 = bind.c8
                                indice1 = 7
                                carta1 = cartas[indice1].toDrawable()
                                mostrar(vista1, indice1)
                                primero = false
                                semaphore.release()
                            } else {
                                vista2 = bind.c8
                                indice2 = 7
                                carta2 = cartas[indice2].toDrawable()
                                mostrar(vista2, indice2)

                                if (comprobarPareja(vista1!!, vista2!!)) {
                                    vista1 = null
                                    vista2 = null
                                    primero = true
                                    comprobarFin()
                                    semaphore.release()
                                } else {
                                    Handler(Looper.getMainLooper()).postDelayed({
                                        ocultar(vista1, indice1)
                                        ocultar(vista2, indice2)
                                        primero = true
                                        vidaMenos()
                                        semaphore.release()
                                    }, 1000)
                                }
                            }

                        } else {
                            semaphore.release()
                        }
                    }

                    R.id.c9 -> {
                        if (!volteada[8]) {
                            animacion(view, 150, 100)
                            if (primero) {
                                vista1 = bind.c9
                                indice1 = 8
                                carta1 = cartas[indice1].toDrawable()
                                mostrar(vista1, indice1)
                                primero = false
                                semaphore.release()

                            } else {
                                vista2 = bind.c9
                                indice2 = 8
                                carta2 = cartas[indice2].toDrawable()
                                mostrar(vista2, indice2)

                                if (comprobarPareja(vista1!!, vista2!!)) {
                                    vista1 = null
                                    vista2 = null
                                    primero = true
                                    comprobarFin()
                                    semaphore.release()
                                } else {
                                    Handler(Looper.getMainLooper()).postDelayed({
                                        ocultar(vista1, indice1)
                                        ocultar(vista2, indice2)
                                        primero = true
                                        vidaMenos()
                                        semaphore.release()
                                    }, 1000)
                                }
                            }

                        } else {
                            semaphore.release()
                        }
                    }

                    R.id.c10 -> {
                        if (!volteada[9]) {
                            animacion(view, 150, 100)
                            if (primero) {
                                vista1 = bind.c10
                                indice1 = 9
                                carta1 = cartas[indice1].toDrawable()
                                mostrar(vista1, indice1)
                                primero = false
                                semaphore.release()
                            } else {
                                vista2 = bind.c10
                                indice2 = 9
                                carta2 = cartas[indice2].toDrawable()
                                mostrar(vista2, indice2)

                                if (comprobarPareja(vista1!!, vista2!!)) {
                                    vista1 = null
                                    vista2 = null
                                    primero = true
                                    comprobarFin()
                                    semaphore.release()
                                } else {
                                    Handler(Looper.getMainLooper()).postDelayed({
                                        ocultar(vista1, indice1)
                                        ocultar(vista2, indice2)
                                        primero = true
                                        vidaMenos()
                                        semaphore.release()
                                    }, 1000)
                                }
                            }

                        } else {
                            semaphore.release()
                        }
                    }

                    R.id.c11 -> {
                        if (!volteada[10]) {
                            animacion(view, 150, 100)
                            if (primero) {
                                vista1 = bind.c11
                                indice1 = 10
                                carta1 = cartas[indice1].toDrawable()
                                mostrar(vista1, indice1)
                                primero = false
                                semaphore.release()

                            } else {
                                vista2 = bind.c11
                                indice2 = 10
                                carta2 = cartas[indice2].toDrawable()
                                mostrar(vista2, indice2)

                                if (comprobarPareja(vista1!!, vista2!!)) {
                                    vista1 = null
                                    vista2 = null
                                    primero = true
                                    comprobarFin()
                                    semaphore.release()
                                } else {
                                    Handler(Looper.getMainLooper()).postDelayed({
                                        ocultar(vista1, indice1)
                                        ocultar(vista2, indice2)
                                        primero = true
                                        vidaMenos()
                                        semaphore.release()
                                    }, 1000)
                                }
                            }

                        } else {
                            semaphore.release()
                        }
                    }

                    R.id.c12 -> {
                        if (!volteada[11]) {
                            animacion(view, 150, 100)
                            if (primero) {
                                vista1 = bind.c12
                                indice1 = 11
                                carta1 = cartas[indice1].toDrawable()
                                mostrar(vista1, indice1)
                                primero = false
                                semaphore.release()
                            } else {
                                vista2 = bind.c12
                                indice2 = 11
                                carta2 = cartas[indice2].toDrawable()
                                mostrar(vista2, indice2)

                                if (comprobarPareja(vista1!!, vista2!!)) {
                                    vista1 = null
                                    vista2 = null
                                    primero = true
                                    comprobarFin()
                                    semaphore.release()
                                } else {
                                    Handler(Looper.getMainLooper()).postDelayed({
                                        ocultar(vista1, indice1)
                                        ocultar(vista2, indice2)
                                        primero = true
                                        vidaMenos()
                                        semaphore.release()
                                    }, 1000)
                                }
                            }

                        } else {
                            semaphore.release()
                        }
                    }

                }
            }
        }

    }
}
