package mx.uttt.palabrin.presentation.screens.auth.signUp.content

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
class SignUpViewModel @Inject constructor(
    private val _fireAuthUseCases: FireAuthUseCases,
    private val _validations: Validations,
    private val _dataStoreUseCases: DataStoreUseCases,
): ViewModel() {

    // Flow para estado de carga y respuesta
    private val _isLoading = MutableStateFlow<Response<Boolean>?>(null)
    val isLoading: StateFlow<Response<Boolean>?> = _isLoading

    // Campos del formulario
    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    private val _confirmPassword = MutableLiveData("")
    val confirmPassword: LiveData<String> = _confirmPassword

    private val _name = MutableLiveData("")
    val name: LiveData<String> = _name

    // Errores para mostrar en UI
    private val _emailError = MutableLiveData<String?>(null)
    val emailError: LiveData<String?> = _emailError

    private val _passwordError = MutableLiveData<String?>(null)
    val passwordError: LiveData<String?> = _passwordError

    private val _repeatedPasswordError = MutableLiveData<String?>(null)
    val repeatedPasswordError: LiveData<String?> = _repeatedPasswordError

    private val _nameError = MutableLiveData<String?>(null)
    val nameError: LiveData<String?> = _nameError

    fun resetInitState(){
        _isLoading.value = null
    }

    fun onEvent(event: SignUpFormEvent){
        when(event){
            is SignUpFormEvent.EmailChanged -> {
                _email.value = event.email
            }
            is SignUpFormEvent.PasswordChanged -> {
                _password.value = event.password
            }
            is SignUpFormEvent.RepeatedPasswordChanged -> {
                _confirmPassword.value = event.repeatedPassword
            }
            is SignUpFormEvent.NameChanged -> {
                _name.value = event.name
            }
            SignUpFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailResult = _validations.validateEmail(_email.value ?: "")
        val passwordResult = _validations.validateStrongPassword(_password.value ?: "")
        val repeatedPasswordResult = _validations.validateRepeatedPassword(_password.value ?: "", _confirmPassword.value ?: "")
        val nameResult = _validations.validateBasicText(_name.value ?: "")

        val hasError = listOf(emailResult, passwordResult, repeatedPasswordResult, nameResult)
            .any { !it.successful }

        if (hasError) {
            _emailError.value = emailResult.errorMessage
            _passwordError.value = passwordResult.errorMessage
            _repeatedPasswordError.value = repeatedPasswordResult.errorMessage
            _nameError.value = nameResult.errorMessage
            return
        }

        viewModelScope.launch {
            signUpUser()
        }
    }

    private suspend fun signUpUser() {
        try {
            _isLoading.value = Response.Loading

            val userModel = UserModel(
                name = _name.value ?: "",
                email = _email.value ?: "",
                password = _password.value ?: "",
            )

            val registerValue = _fireAuthUseCases.registerUser(userModel)

            if (registerValue) {
                loginUser()
            } else {
                _isLoading.value = Response.Error(Exception("Error en registro"))
            }
        } catch (e: Exception) {
            _isLoading.value = Response.Error(Exception("Error durante el registro"))
        }
    }

    private suspend fun loginUser() = viewModelScope.async {
        try {
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