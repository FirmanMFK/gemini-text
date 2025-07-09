package id.firman.geminitest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.generationConfig
import id.firman.geminitest.viewmodel.TextViewModel

@Suppress("UNCHECKED_CAST")
val ViewModelFactory = object : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {
        val config = generationConfig {
            temperature = 0.9f
        }

        val model = GenerativeModel(
            modelName = "gemini-1.5-flash-latest",
            apiKey = "",
            generationConfig = config
        )

        return with(modelClass) {
            when {
                isAssignableFrom(TextViewModel::class.java) -> {
                    TextViewModel(generativeModel = model)
                }
                else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
    }
}