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
import kotlinx.coroutines.launch
import retrofit2.HttpException

class VaritaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVaritaBinding
    private var servicio = RetrofitClient.apiService
    private lateinit var listaCampos: List<Any>

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
    fun crearVarita(): Varita? {
        // coger los datos de los edit text
        val madera = binding.edtxtMadera.text.toString()
        val nucleo = binding.edtxtNucleo.text.toString()
        val longitudTexto = binding.edtxtLongitud.text.toString()
        val personaje = binding.edtxtPersonaje.text.toString().ifEmpty { "Sin asignar" }
        val rota = binding.chkRota.isChecked

        // comprobar que los campos obligatorios no están vacios
        if (madera.isBlank() || nucleo.isBlank() || longitudTexto.isBlank()) {
            return null
        }

        // longitud a bigdecimal
        val longitud = longitudTexto.toBigDecimalOrNull() ?: return null

        // crear la varita con los datos de los entrys
        return Varita(
            madera = madera,
            nucleo = nucleo,
            longitud = longitud,
            personaje = personaje,
            rota = rota
        )
    }

    fun onClickCreacion(view : View) {
        val varita = crearVarita()
        if(varita == null){
            dialogoError()
        } else {
            //val listaVaritas = servicio.crearVarita(varita)
        }
    }

    // poner la varita como rota
    fun onClickRomper(view: View) {

        lifecycleScope.launch {
            try {
                // Llamada a la API
                val resumen = servicio.romperVarita(idVarita)

                binding.chkRota.isChecked = true
                rota = true // Actualizar variable local
                Toast.makeText(this@VaritaActivity, "Varita rota: ${resumen.materiales}", Toast.LENGTH_SHORT).show()

            } catch (e: HttpException) {
                // SI HAY ERROR DE LA API (Código 400, 404, etc.)
                val codigo = e.code()
                // Intentamos extraer el mensaje de error del body ("La varita ya se encuentra rota")
                val mensajeError = e.response()?.errorBody()?.string() ?: "Error desconocido"

                if (codigo == 400) {
                    Toast.makeText(this@VaritaActivity, mensajeError, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@VaritaActivity, "Error del servidor: $codigo", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // ERRORES DE CONEXIÓN (Sin internet, timeout)
                Toast.makeText(this@VaritaActivity, "Error de red: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun dialogoError() {
        // Crear diálogo
        val dialogo = DialogoError()

        // Mensaje que sale
        val args = Bundle()
        args.putString("TITULO", "Error")
        args.putString("MENSAJE", "Campos incorrectos")
        dialogo.arguments = args

        // Mostrar diálogo
        dialogo.show(supportFragmentManager, null)
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

    fun funcionPantalla() {
        // de la pantalla que vengo
        val pantalla = intent.getStringExtra("pantalla")

        if(pantalla == "main") {
            // Ver datos varita y ver botón romper
            binding.BotonCreacion.visibility = View.GONE
            binding.BotonRomper.visibility = View.VISIBLE
            ponerDatosEnEntrys()
        } else {
            // Ver botón crear
            binding.BotonRomper.visibility = View.GONE
            binding.BotonCreacion.visibility = View.VISIBLE
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