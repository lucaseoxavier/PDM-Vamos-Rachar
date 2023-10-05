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
    class MainActivity : AppCompatActivity() {
        private val tag = javaClass.name
        private lateinit var textToSpeech: TextToSpeech

        private val textToSpeechListener = TextToSpeech.OnInitListener { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.language = Locale.getDefault()
                Log.i(tag, "Success to initialize TTS engine.")
            } else {
                Log.e(tag, "Failed to initialize TTS engine.")
            }
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            val textWatcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                }
            }

            // Initialize TTS engine
            textToSpeech = TextToSpeech(this@MainActivity, textToSpeechListener)
        }

        fun clickFalar(v: View) {
            textToSpeech.speak("Oi Sumido", TextToSpeech.QUEUE_FLUSH, null, null)
        }

        override fun onDestroy() {
            // Release TTS engine resources
            textToSpeech.stop()
            textToSpeech.shutdown()
            super.onDestroy()
        }
    }
}