package mx.uttt.palabrin.domain.models.users

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("id") val id: String? = null,
    @SerializedName("name") val name: String = "",
    @SerializedName("email") val email: String = "",
    @Transient val password: String? = null,
)