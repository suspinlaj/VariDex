package com.example.harrypotter.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.harrypotter.R
import com.example.harrypotter.data.VaritaDto
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import com.example.harrypotter.data.VaritaResumenDto
import com.example.harrypotter.ui.VaritaActivity
import kotlin.jvm.java

class VaritaAdapter(
    context: Context,
    private val varitas: List<VaritaResumenDto>
) : ArrayAdapter<VaritaResumenDto>(context, 0, varitas) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.varita_lista, parent, false)

        // separar "madera" y "núcleo"
        val varita = varitas[position]
        val (madera, nucleo) = varita.materiales.split(" y ")

        // Madera
        val tvMaderaValue = view.findViewById<TextView>(R.id.tvMaderaValue)
        tvMaderaValue.text = madera

        // Núcleo
        val tvNucleoValue = view.findViewById<TextView>(R.id.tvNucleoValue)
        tvNucleoValue.text = nucleo

        // Personaje
        val tvPersonajeValue = view.findViewById<TextView>(R.id.tvPersonajeValue)
        tvPersonajeValue.text = varita.personaje ?: "Sin Asignar"

        // imagenes varitas bien
        val listaVaritasBien = listOf(
            R.drawable.varita1,
            R.drawable.varita2,
            R.drawable.varita3,
            R.drawable.varita4,
            R.drawable.varita5,
            R.drawable.varita6,
            R.drawable.varita7
        )
        val randomVaritaBien = listaVaritasBien.random()

        // imagenes varitas rotas
        val listaVaritasRotas = listOf(
            R.drawable.varitarota1,
            R.drawable.varitarota2,
            R.drawable.varitarota3,
            R.drawable.varitarota4,
            R.drawable.varitarota5,
            R.drawable.varitarota6,
            R.drawable.varitarota7
        )
        val randomVaritaRota = listaVaritasRotas.random()

        val varitaEstado = view.findViewById<ImageView>(R.id.imgEstadoVarita)

        if (varita.rota == "Sí") {
            varitaEstado.setImageResource(randomVaritaRota)  // imagen de varita rota
        } else {
            varitaEstado.setImageResource(randomVaritaBien) // imagen de varita bien
        }

        view.setOnClickListener {
            val intent = Intent(context, VaritaActivity::class.java)
            intent.putExtra("id", varita.id)
            intent.putExtra("materiales", varita.materiales)
            intent.putExtra("personaje", varita.personaje)
            intent.putExtra("rota", varita.rota == "Sí") // convertir String "Sí"/"No" a Boolean
            intent.putExtra("longitud", varita.longitud.toString())
            intent.putExtra("pantalla", "main") //
            context.startActivity(intent)
        }

        return view
    }
}
