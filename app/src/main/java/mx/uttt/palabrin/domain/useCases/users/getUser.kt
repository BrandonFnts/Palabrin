package mx.uttt.palabrin.domain.useCases.users

import mx.uttt.palabrin.data.repository.user.UserRepository
import mx.uttt.palabrin.domain.models.Response
import mx.uttt.palabrin.domain.models.users.UserModel
import javax.inject.Inject

class GetUser @Inject constructor(
    private val _repository: UserRepository,
)  {
    suspend fun invoke(userId: String): Response<UserModel> = _repository.getUser(userId = userId)

}