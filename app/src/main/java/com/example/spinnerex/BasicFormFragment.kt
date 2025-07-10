package com.example.spinnerex

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.spinnerex.databinding.FragmentBasicFormBinding
import com.example.spinnerex.utils.ValidatorInputV2
import com.example.spinnerex.utils.dialog.DialogFactory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BasicFormFragment : Fragment() {
    private var labelsFromService = mutableListOf<LabelCredit2DTO>()

    val urlBaseApiary = "https://private-f35346-superiorex.apiary-mock.com/"

    private lateinit var viviendaOptionsMap: Map<String, String>
    private lateinit var viviendaDisplayList: List<String>
    private val FALLBACK_HOUSING_MAP: Map<String, String> = mapOf(
        "1" to "Propia",
        "2" to "Renta/Hipotecada",
        "3" to "Familiar",
        "0" to "Otro"
    )

    val validations = mutableListOf<ValidatorInputV2>()

    private var _binding: FragmentBasicFormBinding? = null
    private val binding get() = _binding!!

    // ✅ Declarar aquí para que esté disponible en toda la clase
    private lateinit var apiaryService: LabelsService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val retrofitApiaryObj = Retrofit.Builder()
            .baseUrl(urlBaseApiary)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiaryService = retrofitApiaryObj.create(LabelsService::class.java)
    }

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
                    viviendaDisplayList, activity
                ) { _, position ->
                    editAddressTypeFragment.setText(viviendaDisplayList[position])
                    println(viviendaDisplayList[position])
                    Log.d("Dialogo", "Seleccionaste: $viviendaDisplayList[position]")
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            try {
                val response = apiaryService.getLabels()
                if (response.isSuccessful) {
                    labelsFromService = (response.body()?.data?.labels ?: mutableListOf()).toMutableList()
                    Log.d("BasicFormFragment", "Labels cargados: ${labelsFromService.size}")

                    // ✅ Ahora que labelsFromService está poblada, puedes obtener el valor
                    val housingOptionsJsonString = getLabelValueByKey("CARD_APPLY_V2_NO_HIT_LIST_HOUSING")
                    processHousingOptions(housingOptionsJsonString) // Nueva función para procesar esto
                    initViews() // Inicializa las vistas aquí, después de todo.

                    binding.internetConsume.text = labelsFromService.firstOrNull()?.value ?: "" // Mejor forma de acceder al primer elemento
                } else {
                    Log.e("BasicFormFragment", "Error en la respuesta de la API: ${response.code()} - ${response.message()}")
                    processHousingOptions("") // Procesa con cadena vacía para usar fallback
                    initViews() // Inicializa vistas con fallback si la API falla
                }
            } catch (e: Exception) {
                Log.e("BasicFormFragment", "Excepción al llamar a la API de labels: ${e.message}", e)
                processHousingOptions("") // Procesa con cadena vacía para usar fallback
                initViews() // Inicializa vistas con fallback en caso de excepción
            }
        }

    }
    private fun processHousingOptions(housingOptionsJsonString: String) {
        try {
            if (housingOptionsJsonString.isNotEmpty()) {
                val type = object : TypeToken<Map<String, String>>() {}.type
                viviendaOptionsMap = Gson().fromJson(housingOptionsJsonString, type)
                if (viviendaOptionsMap.isEmpty()) {
                    viviendaOptionsMap = FALLBACK_HOUSING_MAP
                }
            } else {
                viviendaOptionsMap = FALLBACK_HOUSING_MAP
            }
        } catch (e: Exception) {
            e.printStackTrace()
            viviendaOptionsMap = FALLBACK_HOUSING_MAP
        }
        // Asegúrate de que viviendaDisplayList también se calcule aquí
        viviendaDisplayList = viviendaOptionsMap.map { it.key }
    }
    private fun getLabelValueByKey(key: String) = labelsFromService.find { it.key == key }?.value ?: ""
}