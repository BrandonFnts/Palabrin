package mx.uttt.palabrin.data.network.dataStore

interface DataStoreClient {

    suspend fun setDataString(key: String, value: String)

    suspend fun getDataString(key: String): String

    suspend fun setDataBoolean(key: String, value: Boolean)

    suspend fun getDataBoolean(key: String): Boolean

    suspend fun setDataInt(key: String, value: Int)

    suspend fun getDataInt(key: String): Int

    suspend fun setDataFloat(key: String, value: Float)

    suspend fun getDataFloat(key: String): Float

}