package mx.uttt.palabrin.data.network.user

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import mx.uttt.palabrin.domain.models.Response
import mx.uttt.palabrin.core.constants.Constants
import mx.uttt.palabrin.domain.models.users.UserModel
import javax.inject.Inject

class UserService @Inject constructor(
    private val _fireStore: FirebaseFirestore
) {
    suspend fun getUser(userId: String): Response<UserModel> {
        return try {
            val documentSnapshot = _fireStore.collection(Constants.USERS_COLLECTION)
                .document(userId)
                .get()
                .await()
            val user = documentSnapshot.toObject(UserModel::class.java)
            if (user != null) {
                Response.Success(user)
            } else {
                Response.Error(Exception("User not found"))
            }
        } catch (e: Exception) {
            Response.Error(e)
        }
    }
}