package com.pdm.vamosrachar

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.simpleName

    private lateinit var textToSpeech: TextToSpeech
    private var resultValue: Double? = 0.0
    private var moneySpentValue = 0.0
    private var peopleToShareValue = 0

    private val textToSpeechListener = TextToSpeech.OnInitListener { status ->
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech.language = Locale.getDefault()
            Log.i(TAG, "Success to initialize TTS engine.")
        } else {
            Log.e(TAG, "Failed to initialize TTS engine.")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize TTS engine
        textToSpeech = TextToSpeech(this@MainActivity, textToSpeechListener)

        val moneySpent = findViewById<EditText>(R.id.moneySpent)
        val peopleToShare = findViewById<EditText>(R.id.peopleToShare)
        val resultField = findViewById<TextView>(R.id.resultField)
        val speakButton = findViewById<ImageButton>(R.id.speakButton)
        val shareButton = findViewById<ImageButton>(R.id.shareButton)

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // empty function
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                displayCalculatedValue(moneySpent, peopleToShare, resultField)
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
            resultValue?.let {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "O valor para cada um é ${formatDouble(it)}")
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }
        }
    }

    private fun displayCalculatedValue(moneySpent: EditText, peopleToShare: EditText, resultField: TextView) {
        moneySpentValue = if ( ! moneySpent.text.isNullOrEmpty()) moneySpent.text.toString().toDouble() else 0.0
        peopleToShareValue = if ( ! peopleToShare.text.isNullOrEmpty()) peopleToShare.text.toString().toInt() else 0

        if (peopleToShareValue == 0) {
            resultField.text = getString(R.string.insira_pessoas)
            resultValue = null
        } else {
            resultValue = moneySpentValue/peopleToShareValue
            val result = "R$ ${formatDouble(resultValue!!)}"
            resultField.text = result
        }
    }

    private fun onSpeakClick() {
        resultValue?.let { textToSpeech.speak(formatDouble(it), TextToSpeech.QUEUE_FLUSH, null, null) }
    }

    private fun formatDouble(resultValue: Double): String {
        return String.format("%.2f", resultValue)
    }

    override fun onDestroy() {
        // Release TTS engine resources
        textToSpeech.stop()
        textToSpeech.shutdown()
        super.onDestroy()
    }
}