package mx.uttt.palabrin.presentation.screens.userActivities.draw.content

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.common.model.RemoteModelManager
import com.google.mlkit.vision.digitalink.DigitalInkRecognitionModel
import com.google.mlkit.vision.digitalink.DigitalInkRecognitionModelIdentifier
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.google.mlkit.vision.digitalink.DigitalInkRecognizer
import com.google.mlkit.vision.digitalink.Ink
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import mx.uttt.palabrin.domain.models.Response


@HiltViewModel
class DrawViewModel @Inject constructor(

): ViewModel() {

    // region Flow
    private val _isLoading = MutableStateFlow<Response<Boolean>?>(null)
    val isLoading: MutableStateFlow<Response<Boolean>?> = _isLoading
    // endregion


    private val _streak = MutableStateFlow(0)
    val streak: MutableStateFlow<Int> = _streak

    private val _snackbarMessage = MutableSharedFlow<String>()
    val snackbarMessage = _snackbarMessage.asSharedFlow()

    suspend fun showSnackbar(message: String) {
        _snackbarMessage.emit(message)
    }

    val modelManager = RemoteModelManager.getInstance()
    val modelIdentifier = DigitalInkRecognitionModelIdentifier.fromLanguageTag("es")!!
    val model = DigitalInkRecognitionModel.builder(modelIdentifier).build()

    init {
        modelConfig()
    }

    fun generateRandomLetter(): String {
        return ('A'..'Z').random().toString()
    }

    fun recognizeInk(
        strokes: List<List<Offset>>,
        recognizer: DigitalInkRecognizer,
        onResult: (String) -> Unit
    ) {
        val inkBuilder = Ink.builder()

        strokes.forEach { strokePoints ->
            val strokeBuilder = Ink.Stroke.builder()
            strokePoints.forEach { point ->
                strokeBuilder.addPoint(
                    Ink.Point.create(point.x, point.y, System.currentTimeMillis())
                )
            }
            inkBuilder.addStroke(strokeBuilder.build())
        }

        val ink = inkBuilder.build()

        recognizer.recognize(ink)
            .addOnSuccessListener { result ->
                val recognizedText = result.candidates.firstOrNull()?.text ?: "No reconocido"
                onResult(recognizedText)
            }
            .addOnFailureListener { e ->
                onResult("Error: ${e.message}")
            }
    }


    fun modelConfig(){
        _isLoading.value = Response.Loading
        viewModelScope.launch {
        modelManager.download(model, DownloadConditions.Builder().build())
            .addOnSuccessListener {
                _isLoading.value = Response.Success(true)
            }
            .addOnFailureListener { e ->
                _isLoading.value = Response.Error(e)
            }
    }
    }

    fun resetState(){
        _isLoading.value = null
    }

    fun resetstreak(recognizedText: String){
        _streak.value = 0
        viewModelScope.launch {
            showSnackbar("Oh no! $recognizedText no es la letra correcta")
        }
        }

    fun incrementeStreak(){
        _streak.value++
    }
}