package com.example.harrypotter.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.harrypotter.R
import com.example.harrypotter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        gifPortada()
    }

    fun gifPortada() {
        val gifImagenView = findViewById<ImageView>(R.id.harrygif)

        Glide.with(this)
            .asGif()
            .load(R.raw.harrygif2)
            .into(gifImagenView)
    }

    fun onClickGestion(vista : View) {
        val intent = Intent(this, ListadoVaritas::class.java)
        startActivity(intent)
    }

    fun onClickCreacion(vista : View) {
        val intent = Intent(this, VaritaActivity::class.java)
        intent.putExtra("pantalla","main")
        startActivity(intent)
    }


}