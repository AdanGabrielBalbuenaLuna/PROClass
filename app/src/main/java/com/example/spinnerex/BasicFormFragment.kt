package com.example.spinnerex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.spinnerex.databinding.FragmentBasicFormBinding
import com.example.spinnerex.utils.ValidatorInputV2
import com.example.spinnerex.utils.dialog.DialogFactory

class BasicFormFragment : Fragment() {

    private val viviendaList = listOf(
        "Propia",
        "Rentada/Hipotecada",
        "Familiar",
        "Otro"
    )

    val validations = mutableListOf<ValidatorInputV2>()

    private var _binding: FragmentBasicFormBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBasicFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun initViews() {
        // Define un mensaje genérico para errores de validación.
        val genericMessage = resources.getString(R.string.generic_invalid_message)

        // Aplica configuraciones a las vistas usando el objeto binding.
        binding.apply {
            // Inicializa un validador para el campo de tipo de vivienda, definiendo reglas como texto requerido y longitud mínima.
            val viviendaTypeValidator = ValidatorInputV2(
                inputAddressTypeFragment,
                resources.getString(R.string.required_fields),
                ValidatorInputV2.Type.TEXT,
                genericMessage,
                resources.getInteger(R.integer.company_name_characters_min)
            )
            // Asocia el validador al campo de texto para que reaccione a los cambios de entrada.
            editAddressTypeFragment.addTextChangedListener(viviendaTypeValidator)
            // Añade el validador a una lista de validaciones para un control centralizado.
            validations.add(viviendaTypeValidator)

            // Configura el campo de texto para que, al ser clicado, muestre un diálogo de selección.
            editAddressTypeFragment.setOnClickListener {
                // Abre un diálogo de selección con el título y la lista de tipos de vivienda.
                // La lambda se ejecuta cuando el usuario selecciona un elemento, actualizando el campo de texto.
                DialogFactory.showPickerDialg(
                    getString(R.string.title_tipo_de_vivienda),
                    viviendaList, activity
                ) { _, position ->
                    editAddressTypeFragment.setText(viviendaList[position])
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }
}