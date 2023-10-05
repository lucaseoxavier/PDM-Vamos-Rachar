package com.pdm.vamosrachar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import java.util.Locale

class MainActivity : AppCompatActivity() {
    class MainActivity : AppCompatActivity(), TextWatcher, TextToSpeech.OnInitListener {
        private lateinit var tts: TextToSpeech

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            val edtConta = findViewById<EditText>(R.id.edtConta)
            edtConta.addTextChangedListener(this)
            // Initialize TTS engine
            tts = TextToSpeech(this, this)

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            Log.d("PDM23", "Antes de mudar")
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            Log.d("PDM23", "Mudando")
        }

        override fun afterTextChanged(s: Editable?) {
            Log.d("PDM23", "Depois de mudar")
            Log.d("PDM23", s.toString())
        }

        fun clickFalar(v: View) {

            tts.speak("Oi Sumido", TextToSpeech.QUEUE_FLUSH, null, null)


        }

        override fun onDestroy() {
            // Release TTS engine resources
            tts.stop()
            tts.shutdown()
            super.onDestroy()
        }

        override fun onInit(status: Int) {
            if (status == TextToSpeech.SUCCESS) {
                // TTS engine is initialized successfully
                tts.language = Locale.getDefault()
                Log.d("PDM23", "Sucesso na Inicialização")
            } else {
                // TTS engine failed to initialize
                Log.e("PDM23", "Failed to initialize TTS engine.")
            }
        }
    }
}