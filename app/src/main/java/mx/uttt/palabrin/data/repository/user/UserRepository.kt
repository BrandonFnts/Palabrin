package mx.uttt.palabrin.data.repository.user

import mx.uttt.palabrin.domain.models.Response
import mx.uttt.palabrin.data.network.user.UserService
import mx.uttt.palabrin.domain.models.users.UserModel
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val _service: UserService
) {
    suspend fun getUser(userId: String): Response<UserModel> {
        return try {
            _service.getUser(userId = userId)
        } catch (e: Exception) {
            Response.Error(exception = e)
        }
    }
}
