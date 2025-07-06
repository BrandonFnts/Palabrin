package mx.uttt.palabrin.domain.useCases.firebase

import mx.uttt.palabrin.data.repository.firebase.FireAuthRepository
import mx.uttt.palabrin.domain.models.users.UserModel
import javax.inject.Inject

class LoginUser @Inject constructor(
    private val _fireAuthRepository: FireAuthRepository
) {
    suspend operator fun invoke(email: String, password: String): UserModel? = _fireAuthRepository.loginUser(email, password)
}