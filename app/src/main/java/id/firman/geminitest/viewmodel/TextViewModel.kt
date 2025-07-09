package id.firman.geminitest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import id.firman.geminitest.state.TextPromptUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class TextSummary {
    STREAM
}

class TextViewModel(
    private val generativeModel: GenerativeModel
) : ViewModel() {

    private val mUiState: MutableStateFlow<TextPromptUIState> =
        MutableStateFlow(TextPromptUIState.Initial)

    val uiState: StateFlow<TextPromptUIState> =
        mUiState.asStateFlow()

    fun summarize(
        inputText: String,
        type: TextSummary
    ) {
        mUiState.value = TextPromptUIState.Loading

        viewModelScope.launch {
            if (type == TextSummary.STREAM) {
                runCatching {
                    generativeModel.generateContent(inputText).text
                }.onSuccess { content ->
                    if (content != null) {
                        mUiState.value = TextPromptUIState.Success(output = content)
                    }
                }.onFailure { error ->
                    mUiState.value = TextPromptUIState.Error(
                        message = error.localizedMessage ?: "Unknown Error!"
                    )
                }
            }
        }
    }
}