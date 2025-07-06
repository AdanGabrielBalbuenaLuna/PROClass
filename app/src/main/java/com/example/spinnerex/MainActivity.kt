package com.example.spinnerex

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
// Asegúrate de importar tu fragmento
import com.example.spinnerex.BasicFormFragment
// Descomenta si está en un subpaquete diferente

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main) // Esto infla activity_main.xml

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Solo añade el fragmento si savedInstanceState es null (para evitar añadirlo múltiples veces
        // en recreaciones de la actividad, como rotaciones de pantalla)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, BasicFormFragment::class.java, null)
                // Opcional: .setReorderingAllowed(true)
                // Opcional: .addToBackStack(null) // Si quieres que el usuario pueda volver al estado anterior con el botón de atrás
                .commit()
        }
    }
}