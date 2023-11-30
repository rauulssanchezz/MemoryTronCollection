package com.example.memorytroncollection

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.memorytroncollection.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var bind:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind=ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

    }

    fun medieval(view: View) {
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
        startActivity(intent)

    }
    fun marcianos(view: View) {
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
}