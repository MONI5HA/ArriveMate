package com.example.arivemate.ui.translate

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.arivemate.R
import com.example.arivemate.databinding.ActivityTranslateBinding
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class Translate : AppCompatActivity() {
    private lateinit var binding: ActivityTranslateBinding
    private val SPEECH_REQUEST_CODE = 1234

    // Maps for languages (Spinner display name to code)
    private val speechLanguages = listOf(
        "English (US)" to "en-US",
        "French (France)" to "fr-FR",
        "Hindi (India)" to "hi-IN",
        "Spanish (Spain)" to "es-ES",
        "German (Germany)" to "de-DE"
    )

    private val translateLanguages = listOf(
        "English" to TranslateLanguage.ENGLISH,
        "French" to TranslateLanguage.FRENCH,
        "Hindi" to TranslateLanguage.HINDI,
        "Spanish" to TranslateLanguage.SPANISH,
        "German" to TranslateLanguage.GERMAN
    )

    private var recognizedText: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTranslateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup spinners
        val speechAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            speechLanguages.map { it.first })
        speechAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerSourceLang.adapter = speechAdapter

        val translateAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, translateLanguages.map { it.first })
        translateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTargetLang.adapter = translateAdapter

        binding.btnSpeak.setOnClickListener {
            val sourceLangCode = speechLanguages[binding.spinnerSourceLang.selectedItemPosition].second
            startSpeechToText(sourceLangCode)
        }
    }
    private fun startSpeechToText(languageCode: String) {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, languageCode)
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now")
        }

        try {
            startActivityForResult(intent, SPEECH_REQUEST_CODE)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Speech recognition not supported on this device", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val results = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            recognizedText = results?.get(0) ?: ""
            binding.tvRecognizedText.text = recognizedText

            // Now translate
            translateRecognizedText()
        }
    }

    private fun translateRecognizedText() {
        val targetLangCode = translateLanguages[binding.spinnerTargetLang.selectedItemPosition].second

        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)  // We'll use English as source for ML Kit (for simplicity)
            .setTargetLanguage(targetLangCode)
            .build()

        val translator = Translation.getClient(options)

        // Download conditions (wifi or any)
        val conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()

        lifecycleScope.launch {
            try {
                binding.tvTranslatedText.text = "Downloading translation model..."
                // Download model if not downloaded
                translator.downloadModelIfNeeded(conditions).await()

                // Translate text on background thread
                val translatedText = withContext(Dispatchers.IO) {
                    translator.translate(recognizedText).await()
                }

                binding.tvTranslatedText.text = translatedText

                // Close translator to release resources
                translator.close()
            } catch (e: Exception) {
                binding.tvTranslatedText.text = "Error: ${e.message}"
            }
        }
    }

}
