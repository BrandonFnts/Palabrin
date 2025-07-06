package mx.uttt.palabrin.presentation.utils

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null,
)
