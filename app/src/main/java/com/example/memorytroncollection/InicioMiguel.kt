package com.example.memorytroncollection

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.memorytroncollection.databinding.ActivityInicioMiguelBinding
import com.example.memorytroncollection.databinding.ActivityMainBinding

class InicioMiguel : AppCompatActivity() {
    private lateinit var bind : ActivityInicioMiguelBinding
    private var cont = 0
    private var oculto = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityInicioMiguelBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.jugar.setOnClickListener {
            var intent = Intent(this,JuegoMiguel::class.java)
            intent.putExtra("oculto",oculto)
            startActivity(intent)
        }
        bind.jugar2.setOnClickListener {
            var intent = Intent(this,JuegoMiguel::class.java)
            intent.putExtra("oculto",oculto)
            startActivity(intent)
        }

        bind.card.setOnClickListener {
            cont ++
            if (cont > 5){
                bind.explicacion.setBackgroundResource(R.drawable.fondoboton_m)
                bind.explicacion.setTextColor(getColor(R.color.yellow))
                bind.adv.visibility = View.GONE
                bind.jugar.visibility = View.INVISIBLE
                bind.jugar2.visibility = View.VISIBLE
                bind.t.text = getString(R.string.msj_bienvenidaR)
                bind.t.textSize = 15f
                bind.explicacion.text = getString(R.string.explicacionR)
                bind.explicacion.textSize = 15f
                oculto = true
            }
        }

    }
    override fun onBackPressed() {
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        super.onBackPressed()
    }
}