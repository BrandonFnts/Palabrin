package mx.uttt.palabrin.presentation

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import mx.uttt.palabrin.domain.useCases.dataStore.DataStoreUseCases
import mx.uttt.palabrin.presentation.enums.Routes
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val _fireAuth: FirebaseAuth,
    private val _dataStoreUseCases: DataStoreUseCases
): ViewModel() {

    //Variables
    private val _isLoggedIn = MutableStateFlow<Boolean?>(null)
    val isLoggedIn: StateFlow<Boolean?> = _isLoggedIn

    private val _isAllOk = MutableStateFlow<Boolean?>(null)
    val isAllOk: StateFlow<Boolean?> = _isAllOk

    private var authChecked = false

    // Init
    init {
        verifyUser()
    }

    //Functions
    private fun verifyUser() {
        _fireAuth.addAuthStateListener { firebaseAuth ->
            if (!authChecked) {
                _isLoggedIn.value = firebaseAuth.currentUser != null
                authChecked = true
            }
        }
    }


    fun verifyRouteTop(currentRoute: String?): Boolean {
        return !(currentRoute == Routes.LOGIN.name || currentRoute == Routes.SIGNUP.name)
    }

    fun verifyRouteBottom(currentRoute: String?): Boolean {
        return !(currentRoute == Routes.LOGIN.name || currentRoute == Routes.SIGNUP.name)
    }
}