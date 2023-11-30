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
        var intent=Intent(this,JuegoRaul::class.java)
        startActivity(intent)
    }
    fun marcianos(view: View) {
        var intent = Intent(this,InicioMiguel::class.java)
        startActivity(intent)
    }
    fun melendi(view: View) {
        var intent=Intent(this,JuegoPablo::class.java)
        startActivity(intent)
    }
}