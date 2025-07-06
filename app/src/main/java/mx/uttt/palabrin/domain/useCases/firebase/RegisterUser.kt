package mx.uttt.palabrin.domain.useCases.firebase

import mx.uttt.palabrin.data.repository.firebase.FireAuthRepository
import mx.uttt.palabrin.domain.models.users.UserModel
import javax.inject.Inject

class RegisterUser @Inject constructor(
    private val fireAuthRepository: FireAuthRepository
) {
    suspend operator fun invoke(user: UserModel): Boolean = fireAuthRepository.registerUser(user = user)
}