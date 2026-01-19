package com.example.harrypotter.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.harrypotter.R
import com.example.harrypotter.data.CrearVaritaDto
import com.example.harrypotter.data.Varita
import com.example.harrypotter.databinding.ActivityVaritaBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class VaritaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVaritaBinding
    private var servicio = RetrofitClient.apiService
    private var listaCamposIncorrectos: MutableList<String> = mutableListOf()

    private var idVarita: Int = 0
    private var madera: String? = null
    private var nucleo: String? = null
    private var personaje: String? = null
    private var rota: Boolean = false
    private var longitud: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVaritaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        gifFondo()

        idVarita = intent.getIntExtra("id", -1)
        madera = intent.getStringExtra("madera")
        nucleo = intent.getStringExtra("nucleo")
        personaje = intent.getStringExtra("personaje")
        rota = intent.getBooleanExtra("rota", false)
        longitud = intent.getStringExtra("longitud")

        funcionPantalla()
    }

    // crear varita comprobando si están los campos con datos
    fun crearVaritaDto(): CrearVaritaDto? {

        listaCamposIncorrectos = mutableListOf()

        val madera = binding.edtxtMadera.text.toString()
        val nucleo = binding.edtxtNucleo.text.toString()
        val longitudTexto = binding.edtxtLongitud.text.toString()


        //añadir elementos obligatorios a la lista de error si están vacios
        if (madera.isBlank()){
            listaCamposIncorrectos.add("Madera")
        }
        if (nucleo.isBlank()){
            listaCamposIncorrectos.add("Núcleo")
        }
        if (longitudTexto.isBlank()){
            listaCamposIncorrectos.add("Longitud")
        }

        if (listaCamposIncorrectos.isNotEmpty()) {
            return null
        }

        val longitud = longitudTexto.toBigDecimalOrNull() ?: run {
            listaCamposIncorrectos.add("Longitud (valor inválido)")
            return null
        }

        return CrearVaritaDto(
            madera = madera,
            nucleo = nucleo,
            longitud = longitud
        )
    }

    fun onClickCreacion(view: View) {
        val dto = crearVaritaDto() ?: return dialogoError(
            "Campos incompletos",
            listaCamposIncorrectos.joinToString("\n")
        )

        val nombrePersonaje = binding.edtxtPersonaje.text.toString().trim()
        val estaRota = binding.chkRota.isChecked

        lifecycleScope.launch {
            try {
                // Crear la varita
                val varitaApi = servicio.crearVarita(dto)
                val idVarita = varitaApi.id

                // buscar ID si hay nombre de personaje
                if (nombrePersonaje.isNotEmpty() && nombrePersonaje != "Sin asignar") {
                    val listaPersonajes = servicio.buscarPersonajePorNombre(nombrePersonaje)

                    if (listaPersonajes.isNotEmpty()) {

                        val idPersonaje = listaPersonajes[0].id

                        if (idPersonaje != null) {
                            servicio.asignarVarita(idPersonaje, idVarita)
                            dialogoError("Éxito", "Varita creada correctamente")
                        }
                    } else {
                        // si no existe el personaje
                        dialogoError(
                            "Error Personaje",
                            "El personaje '$nombrePersonaje' no existe. Se pondrá como 'Sin asignar'."
                        )
                    }
                }
                if (estaRota) {
                    servicio.romperVarita(idVarita)
                }
            }

            catch (e: HttpException) {
                val code = e.code()
                val errorBody = e.response()?.errorBody()?.string() // JSON como String

                // Mostrar mensaje raw
                dialogoError("Error", errorBody ?: "Error desconocido")
            }


        }
    }


    // poner la varita como rota
    fun onClickRomper(view: View) {

        lifecycleScope.launch {
            try {
                // Llamada a la API
                val resumen = servicio.romperVarita(idVarita)

                binding.chkRota.isChecked = true
                rota = true // Actualizar rota
                dialogoError("Éxito", "La varita se rompió correctamente")
            } catch (e: Exception) {
                dialogoError("Error", "La varita ya se encuentra rota")            }

        }
    }

    fun dialogoError(titulo: String, mensaje: String) {
        val dialogo = DialogoError()

        val args = Bundle().apply {
            putString("TITULO", titulo)
            putString("MENSAJE", mensaje)
        }

        dialogo.arguments = args
        dialogo.show(supportFragmentManager, "DialogoError")
    }


    // poner los datos de la varita seleccionada en la listview en los edit text
    fun ponerDatosEnEntrys() {
        val materiales = intent.getStringExtra("materiales") ?: ""

        // separar "madera" y "nucleo"
        if (materiales.contains(" y ")) {
            val partes = materiales.split(" y ")
            binding.edtxtMadera.setText(partes[0])
            binding.edtxtNucleo.setText(partes[1])
        } else {
            binding.edtxtMadera.setText(materiales)
        }

        binding.edtxtLongitud.setText(longitud)
        binding.edtxtPersonaje.setText(personaje)
        binding.chkRota.isChecked = rota
    }

    fun desactivarEdicion() {
        binding.chkRota.isEnabled = false
        binding.edtxtMadera.isEnabled = false
        binding.edtxtNucleo.isEnabled = false
        binding.edtxtLongitud.isEnabled = false
        binding.edtxtPersonaje.isEnabled = false
    }

    fun funcionPantalla() {
        // de la pantalla que vengo
        val pantalla = intent.getStringExtra("pantalla")

        if(pantalla == "main") {
            // Ver botón crear
            binding.BotonRomper.visibility = View.GONE
            binding.BotonCreacion.visibility = View.VISIBLE
        } else if (pantalla == "lista") {
            // Ver datos varita y botón romper
            binding.BotonCreacion.visibility = View.GONE
            binding.BotonRomper.visibility = View.VISIBLE
            ponerDatosEnEntrys()
            desactivarEdicion()
        }
    }

    fun gifFondo() {
        val gifImagenView = findViewById<ImageView>(R.id.gifFondo)

        Glide.with(this)
            .asGif()
            .load(R.raw.gifhuellas)
            .into(gifImagenView)
    }
    fun onClickAtras(view: View) {
        finish()
    }

}