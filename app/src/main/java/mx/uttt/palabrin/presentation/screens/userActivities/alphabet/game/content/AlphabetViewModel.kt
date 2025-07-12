package mx.uttt.palabrin.presentation.screens.userActivities.alphabet.game.content

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import java.util.Locale

@HiltViewModel
class AlphabetViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _selectedLetter = MutableLiveData<String>().apply {
        val letterArg = savedStateHandle.get<String>("letter")?.firstOrNull() ?: 'A'
        value = letterArg.toString()
    }
    val selectedLetter: LiveData<String> = _selectedLetter

    private val _speakLetter = MutableLiveData<String?>()
    val speakLetter: LiveData<String?> = _speakLetter

    private val _currentIndex = MutableLiveData(0)
    val currentIndex: LiveData<Int> = _currentIndex

    init {
        selectedLetter.observeForever {
            resetProgress()
        }
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
        addSource(examples) { value = it.getOrNull(_currentIndex.value ?: 0) }
        addSource(_currentIndex) { index ->
            value = examples.value?.getOrNull(index)
        }
    }

    fun onRecognizedSpeech(text: String) {
        val expected = currentWord.value ?: return
        val spoken = text.trim().lowercase(Locale.ROOT)
        val target = expected.trim().lowercase(Locale.ROOT)

        if (spoken == target) {
            _currentIndex.value = (_currentIndex.value ?: 0) + 1
        }
    }

    fun speakCurrentLetter() {
        _selectedLetter.value?.let {
            _speakLetter.value = it
        }
    }

    fun resetSpeakEvent() {
        _speakLetter.value = null
    }

    fun resetProgress() {
        _currentIndex.value = 0
    }
}