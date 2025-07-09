package id.firman.geminitest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import id.firman.geminitest.ui.screen.TextSummaryScreen
import id.firman.geminitest.ui.theme.GenAIWorkshopTheme
import id.firman.geminitest.viewmodel.TextViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GenAIWorkshopTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val textSummaryViewModel: TextViewModel = viewModel(
                        factory = ViewModelFactory
                    )
                    val textSummaryUiState by textSummaryViewModel.uiState.collectAsState()

                    TextSummaryScreen(
                        uiState = textSummaryUiState,
                        onTextSummaryClick = { inputText, switchState ->
                            textSummaryViewModel.summarize(
                                inputText,
                                switchState
                            )
                        }
                    )
                }
            }
        }
    }
}