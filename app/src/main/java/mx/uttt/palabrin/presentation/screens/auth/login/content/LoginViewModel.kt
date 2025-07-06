package mx.uttt.palabrin.presentation.screens.auth.login.content

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import mx.uttt.palabrin.core.constants.Constants.USER_EMAIL
import mx.uttt.palabrin.core.constants.Constants.USER_NAME
import mx.uttt.palabrin.core.constants.Constants.USER_UID
import mx.uttt.palabrin.domain.models.Response
import mx.uttt.palabrin.domain.models.users.UserModel
import mx.uttt.palabrin.domain.useCases.dataStore.DataStoreUseCases
import mx.uttt.palabrin.domain.useCases.firebase.FireAuthUseCases
import mx.uttt.palabrin.presentation.utils.Validations
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val _fireAuthUseCases: FireAuthUseCases,
    private val _validations: Validations,
    private val _dataStoreUseCases: DataStoreUseCases
): ViewModel() {
    // Flow
    private val _isLoading = MutableStateFlow<Response<Boolean>?>(value = null)
    val isLoading: MutableStateFlow<Response<Boolean>?> = _isLoading

    // Variables
    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    // Errors messages
    private val _emailError = MutableLiveData<String?>()
    val emailError: LiveData<String?> = _emailError

    private val _passwordError = MutableLiveData<String?>()
    val passwordError: LiveData<String?> = _passwordError

    fun resetInitState(){
        _isLoading.value = null
    }

    fun onEvent(event: LoginFormEvent){
        when(event){
            is LoginFormEvent.EmailChanged -> {
                _email.value = event.email
            }
            is LoginFormEvent.PasswordChanged -> {
                _password.value = event.password
            }
            is LoginFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailResult = _validations.validateEmail(_email.value!!)
        val passwordResult = _validations.validateStrongPassword(_password.value!!)

        val hasError = listOf(
            emailResult,
            passwordResult
        ).any { !it.successful }

        if (hasError){
            _emailError.value = emailResult.errorMessage
            _passwordError.value = passwordResult.errorMessage
            return
        }

        viewModelScope.launch {
            loginUser()
        }
    }

    private suspend fun loginUser() = viewModelScope.async {
        try {
            _isLoading.value = Response.Loading
            val registerValue = _fireAuthUseCases.loginUser(_email.value!!, _password.value!!)
            if (registerValue != null) {
                setDataStoreInfo(registerValue)
                _isLoading.value = Response.Success(true)
            } else {
                _isLoading.value = Response.Error(Exception("Error"))
            }
        } catch (e: Exception) {
            _isLoading.value = Response.Error(Exception("Error al iniciar sesión"))
        }
    }.await()

    private fun setDataStoreInfo(user: UserModel) = viewModelScope.launch {
        _dataStoreUseCases.setDataString.invoke(USER_UID, user.id.toString())
        _dataStoreUseCases.setDataString.invoke(USER_NAME, user.name)
        _dataStoreUseCases.setDataString.invoke(USER_EMAIL, user.email)
    }

}