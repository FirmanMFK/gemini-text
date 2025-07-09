package id.firman.geminitest.state

sealed interface TextPromptUIState {

    data object Initial : TextPromptUIState
    data object Loading : TextPromptUIState

    data class Success(
        val output: String
    ) : TextPromptUIState

    data class Error(
        val message: String
    ) : TextPromptUIState
}