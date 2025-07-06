package mx.uttt.palabrin.data.network.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.suspendCancellableCoroutine
import mx.uttt.palabrin.core.constants.Constants
import mx.uttt.palabrin.domain.models.users.UserModel
import mx.uttt.palabrin.presentation.utils.fromJson
import mx.uttt.palabrin.presentation.utils.toJson
import javax.inject.Inject
import kotlin.coroutines.resume

class FireAuthService @Inject constructor(
    private val _fireAuth: FirebaseAuth,
    private val _fireStore: FirebaseFirestore
) {
    suspend fun registerUser(user: UserModel): Boolean {
        return try {
            suspendCancellableCoroutine { continuation ->
                _fireAuth.createUserWithEmailAndPassword(user.email, user.password!!)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userId = task.result.user?.uid ?: return@addOnCompleteListener

                            val completeUser = user.copy(id = userId)

                            val userMap: Map<String, Any> =
                                completeUser.toJson().fromJson()

                            _fireStore.collection(Constants.USERS_COLLECTION)
                                .document(userId)
                                .set(userMap)
                                .addOnSuccessListener {
                                    continuation.resume(true)
                                }
                                .addOnFailureListener {
                                    continuation.resume(false)
                                }
                        } else {
                            continuation.resume(false)
                        }
                    }
                    .addOnFailureListener {
                        continuation.resume(false)
                    }
            }
        } catch (e: Exception) {
            false
        }
    }

    suspend fun loginUser(email: String, password: String): UserModel? {
        return try {
            suspendCancellableCoroutine { continuation ->
                _fireAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener { authResult ->
                        val userId = authResult.user?.uid
                        if (userId == null) {
                            continuation.resume(null)
                            return@addOnSuccessListener
                        }

                        _fireStore.collection(Constants.USERS_COLLECTION)
                            .document(userId)
                            .get()
                            .addOnSuccessListener { document ->
                                if (document.exists()) {
                                    val userModel = document.toObject(UserModel::class.java)
                                    continuation.resume(userModel)
                                } else {
                                    continuation.resume(null)
                                }
                            }
                            .addOnFailureListener {
                                continuation.resume(null)
                            }
                    }
                    .addOnFailureListener {
                        continuation.resume(null)
                    }
            }
        } catch (e: Exception) {
            null
        }
    }

}