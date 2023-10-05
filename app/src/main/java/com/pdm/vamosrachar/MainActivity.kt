package com.pdm.vamosrachar

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MainActivity : AppCompatActivity() {
    class MainActivity : AppCompatActivity() {
        private val tag = javaClass.name

        private lateinit var textToSpeech: TextToSpeech
        private var resultValue = 0.0

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

            // Initialize TTS engine
            textToSpeech = TextToSpeech(this@MainActivity, textToSpeechListener)

            val moneySpent = findViewById<EditText>(R.id.moneySpent)
            val moneySpentValue = moneySpent.text.toString().toDouble()
            val peopleToShare = findViewById<EditText>(R.id.peopleToShare)
            val peopleToShareValue = peopleToShare.text.toString().toInt()
            val resultField = findViewById<TextView>(R.id.resultField)
            val speakButton = findViewById<Button>(R.id.speakButton)
            val shareButton = findViewById<Button>(R.id.shareButton)

            val textWatcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // empty function
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    resultValue = moneySpentValue/peopleToShareValue
                    resultField.text = resultValue.toString()
                }

                override fun afterTextChanged(s: Editable?) {
                    // empty function
                }
            }

            moneySpent.addTextChangedListener(textWatcher)
            peopleToShare.addTextChangedListener(textWatcher)

            speakButton.setOnClickListener {
                onSpeakClick()
            }

            shareButton.setOnClickListener {
            }
        }

        private fun onSpeakClick() {
            textToSpeech.speak(resultValue.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
        }

        override fun onDestroy() {
            // Release TTS engine resources
            textToSpeech.stop()
            textToSpeech.shutdown()
            super.onDestroy()
        }
    }
}