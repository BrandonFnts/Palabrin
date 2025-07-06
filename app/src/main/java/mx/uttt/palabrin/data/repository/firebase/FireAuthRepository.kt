package mx.uttt.palabrin.data.repository.firebase

import mx.uttt.palabrin.data.network.firebase.FireAuthService
import mx.uttt.palabrin.domain.models.users.UserModel
import javax.inject.Inject

class FireAuthRepository @Inject constructor(
    private val _fireAuthService: FireAuthService
) {
    suspend fun registerUser(user: UserModel): Boolean {
        val success = _fireAuthService.registerUser(user = user)
        return success
    }

    suspend fun loginUser(email: String, password: String): UserModel? {
        return _fireAuthService.loginUser(email, password)
    }
}