package mx.uttt.palabrin.presentation.screens.userActivities.alphabet.content

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Locale

@HiltViewModel
class AlphabetViewModel @Inject constructor() : ViewModel() {

    private val allLetters: List<String> =
        ('A'..'N').map { it.toString() } + listOf("Ñ") + ('O'..'Z').map { it.toString() }

    private val _speakLetter = MutableLiveData<String?>()
    val speakLetter: LiveData<String?> = _speakLetter

    private val _selectedLetter = MutableLiveData<String>()
    val selectedLetter: LiveData<String> = _selectedLetter

    private val _currentIndex = MutableLiveData(0)
    val currentIndex: LiveData<Int> = _currentIndex

    private val _finishedRound = MutableLiveData(false)
    val finishedRound: LiveData<Boolean> = _finishedRound

    private val _streak = MutableStateFlow(0)
    val streak: StateFlow<Int> = _streak

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        startNewRound()
    }

    val examples: LiveData<List<String>> = _selectedLetter.map { letter ->
        when (letter) {
            "A"  -> listOf("Abeja", "Amigo", "Avión")
            "B"  -> listOf("Beso", "Barco", "Bosque")
            "C"  -> listOf("Casa", "Coche", "Corazón")
            "D"  -> listOf("Dedo", "Dado", "Diente")
            "E"  -> listOf("Elefante", "Estrella", "Escuela")
            "F"  -> listOf("Fuego", "Flor", "Faro")
            "G"  -> listOf("Gato", "Globo", "Guitarra")
            "H"  -> listOf("Helado", "Hormiga", "Hotel")
            "I"  -> listOf("Isla", "Iglesia", "Imán")
            "J"  -> listOf("Jardín", "Juguete", "Jirafa")
            "K"  -> listOf("Kilo", "Kiwi", "Kárate")
            "L"  -> listOf("Libro", "Lápiz", "Luna")
            "M"  -> listOf("Manzana", "Mariposa", "Montaña")
            "Ñ"  -> listOf("Ñu", "Ñoño", "Ñandú")
            "O"  -> listOf("Oso", "Ojo", "Oreja")
            "P"  -> listOf("Perro", "Pájaro", "Playa")
            "Q"  -> listOf("Queso", "Quinto", "Química")
            "R"  -> listOf("Reloj", "Río", "Rana")
            "S"  -> listOf("Sol", "Silla", "Sombrero")
            "T"  -> listOf("Taza", "Toro", "Tren")
            "U"  -> listOf("Uva", "Unicornio", "Universo")
            "V"  -> listOf("Vaca", "Viento", "Valle")
            "W"  -> listOf("Wifi", "Whisky", "Wafle")
            "X"  -> listOf("Xilófono", "Xenón", "Xilografía")
            "Y"  -> listOf("Yate", "Yerno", "Yoyo")
            "Z"  -> listOf("Zapato", "Zorro", "Zumo")
            else -> List(3) { "$letter${it + 1}" }
        }
    }

    val currentWord: LiveData<String> = MediatorLiveData<String>().apply {
        addSource(examples) { list ->
            value = list.getOrNull(_currentIndex.value ?: 0) ?: ""
        }
        addSource(_currentIndex) { idx ->
            value = examples.value?.getOrNull(idx) ?: ""
        }
    }

    init {
        startNewRound()
    }

    private fun randomLetter() =
        allLetters.random().also { _selectedLetter.value = it }

    fun startNewRound() {
        _currentIndex.value = 0
        _finishedRound.value = false
        _error.value = null
        randomLetter()
    }

    fun speakCurrentLetter() {
        _speakLetter.value = _selectedLetter.value
    }

    fun resetSpeakEvent() {
        _speakLetter.value = null
    }

    fun onRecognizedSpeech(text: String) {
        _error.value = null

        if (_finishedRound.value == true) return

        val expected = currentWord.value ?: return
        if (text.trim().equals(expected, ignoreCase = true)) {
            val next = (_currentIndex.value ?: 0) + 1
            if (next < (examples.value?.size ?: 0)) {
                _currentIndex.value = next
            } else {
                _finishedRound.value = true
                _streak.value += 1
            }
        } else {
            _error.value = "Respuesta incorrecta $text"
        }
    }
}