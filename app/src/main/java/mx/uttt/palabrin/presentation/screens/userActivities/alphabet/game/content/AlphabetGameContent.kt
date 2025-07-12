package mx.uttt.palabrin.presentation.screens.userActivities.alphabet.game.content

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.app.ComponentActivity
import java.util.Locale

@Composable
fun AlphabetGameContent(
    padding: PaddingValues,
    viewModel: AlphabetViewModel
) {
    val context = LocalContext.current
    val letter by viewModel.selectedLetter.observeAsState("A")
    val currentWord by viewModel.currentWord.observeAsState("")
    val currentIndex by viewModel.currentIndex.observeAsState(0)

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val data = result.data
        if (result.resultCode == Activity.RESULT_OK && data != null) {
            val results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val spokenText = results?.firstOrNull() ?: ""
            viewModel.onRecognizedSpeech(spokenText)
        }
    }

    fun startListening() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-MX")
        }
        launcher.launch(intent)
    }

    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LetterHeader(viewModel = viewModel ,letter = letter)
        CurrentWordCard(currentWord = currentWord)
        SpeakButton(onClick = { startListening() })

        if (currentWord.isEmpty()) {
            CompletionMessage(onRestart = { viewModel.resetProgress() })
        }
    }
}

@Composable
fun LetterHeader(viewModel: AlphabetViewModel, letter: String) {
    val context = LocalContext.current
    val letterToSpeak by viewModel.speakLetter.observeAsState()

    val tts = remember(context) {
        TextToSpeech(context) {}
    }

    LaunchedEffect(letterToSpeak) {
        if (letterToSpeak != null) {
            tts.language = Locale("es", "MX")
            tts.speak(letterToSpeak, TextToSpeech.QUEUE_FLUSH, null, null)
            viewModel.resetSpeakEvent()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            tts.shutdown()
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .height(100.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = letter,
                style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )

            IconButton(onClick = {
                viewModel.speakCurrentLetter()
            }) {
                Icon(Icons.Default.VolumeUp, contentDescription = "Escuchar letra")
            }
        }
    }
}


@Composable
fun CurrentWordCard(currentWord: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(80.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = currentWord,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}

@Composable
fun SpeakButton(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Icon(Icons.Default.Mic, contentDescription = "Hablar")
        Spacer(Modifier.width(8.dp))
        Text("Decir palabra")
    }
}

@Composable
fun CompletionMessage(onRestart: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "¡Has completado todas las palabras!",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(8.dp))
        Button(onClick = onRestart) {
            Text("Reiniciar")
        }
    }
}