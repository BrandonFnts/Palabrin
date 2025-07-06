package mx.uttt.palabrin.presentation.screens.auth.login.content

sealed class LoginFormEvent {
    data class EmailChanged(val email: String): LoginFormEvent()
    data class PasswordChanged(val password: String): LoginFormEvent()
    data object Submit: LoginFormEvent()
}