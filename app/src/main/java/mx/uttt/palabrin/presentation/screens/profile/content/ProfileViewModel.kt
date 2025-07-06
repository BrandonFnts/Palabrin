package mx.uttt.palabrin.presentation.screens.profile.content

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import mx.uttt.palabrin.core.constants.Constants.USER_EMAIL
import mx.uttt.palabrin.core.constants.Constants.USER_NAME
import mx.uttt.palabrin.core.constants.Constants.USER_UID
import mx.uttt.palabrin.domain.useCases.dataStore.DataStoreUseCases
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val _dataStoreUseCases: DataStoreUseCases,
    private val _fireAuth: FirebaseAuth
): ViewModel() {
    private val _userName = MutableLiveData("")
    val userName: LiveData<String> = _userName

    private val _userEmail = MutableLiveData("")
    val userEmail: LiveData<String> = _userEmail

    private val _userType = MutableLiveData("")
    val userType: LiveData<String> = _userType

    init{
        setUserInfo()
    }

    private fun setUserInfo() {
        viewModelScope.launch {
            _userName.value = _dataStoreUseCases.getDataString(USER_NAME)
            _userEmail.value = _dataStoreUseCases.getDataString(USER_EMAIL)
            _userType.value = "Padre"
        }
    }

    suspend fun logOut(){
        viewModelScope.async {
            _dataStoreUseCases.setDataString.invoke(USER_UID, "")
            _dataStoreUseCases.setDataString.invoke(USER_NAME, "")
            _dataStoreUseCases.setDataString.invoke(USER_EMAIL, "")
            _fireAuth.signOut()
        }.await()
    }
}