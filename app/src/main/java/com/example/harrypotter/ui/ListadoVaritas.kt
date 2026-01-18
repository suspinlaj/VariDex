package com.example.harrypotter.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.harrypotter.R
import com.example.harrypotter.databinding.ActivityListadoVaritasBinding
import com.example.harrypotter.adapter.VaritaAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListadoVaritas : AppCompatActivity() {
    private lateinit var binding: ActivityListadoVaritasBinding
    private var servicio = RetrofitClient.apiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListadoVaritasBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        cargarVaritas()
    }

    // refrescar los datos
    override fun onResume() {
        super.onResume()
        cargarVaritas()
    }

    fun onClickAtras(view: View) {
        finish()
    }
    private fun cargarVaritas() {

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                // Llamar a la API
                val listaVaritas = servicio.getVaritas()

                withContext(Dispatchers.Main) {

                    if (listaVaritas.isNotEmpty()) {
                        // Asignar adapter a la ListView
                        binding.listViewVaritas.adapter = VaritaAdapter(
                            this@ListadoVaritas,
                            listaVaritas
                        )
                    }
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@ListadoVaritas,
                        "Error de conexión con el servidor",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }




}