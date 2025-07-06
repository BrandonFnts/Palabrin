package mx.uttt.palabrin.presentation.screens.auth.signUp.content

sealed class SignUpFormEvent {
    data class EmailChanged(val email: String): SignUpFormEvent()
    data class PasswordChanged(val password: String): SignUpFormEvent()
    data class RepeatedPasswordChanged(val repeatedPassword: String): SignUpFormEvent()
    data class NameChanged(val name: String): SignUpFormEvent()
    data object Submit: SignUpFormEvent()
}