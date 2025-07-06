package mx.uttt.palabrin.data.network.dataStore

import android.content.Context
import android.util.Base64
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import mx.uttt.palabrin.core.constants.Constants.USER_PREFERENCES
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = USER_PREFERENCES
)

class DataStoreService @Inject constructor(
    @ApplicationContext private val context: Context,
    private val cryptoManager: CryptoManager
): DataStoreClient {
    override suspend fun setDataString(key: String, value: String) {
        val preferencesKey = stringPreferencesKey(key)
        val encryptedValue = cryptoManager.encrypt(value)
        val encryptedValueString = Base64.encodeToString(encryptedValue, Base64.DEFAULT)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = encryptedValueString
        }
    }

    override suspend fun getDataString(key: String): String {
        return try {
            val preferencesKey = stringPreferencesKey(key)
            val preferencesFlow = context.dataStore.data.map { preferences ->
                preferences[preferencesKey] ?: ""
            }
            val encryptedValueString = preferencesFlow.first()
            if (encryptedValueString.isNotEmpty()) {
                val encryptedValue = Base64.decode(encryptedValueString, Base64.DEFAULT)
                cryptoManager.decrypt(encryptedValue)
            } else {
                ""
            }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            "error"
        }
    }

    override suspend fun setDataBoolean(key: String, value: Boolean) {
        val preferencesKey = booleanPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun getDataBoolean(key: String): Boolean {
        return try {
            val preferencesKey = booleanPreferencesKey(key)
            val preferencesFlow = context.dataStore.data.map { preferences ->
                preferences[preferencesKey] ?: false
            }
            preferencesFlow.first()
        } catch (e: Exception){
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            false
        }
    }

    override suspend fun setDataInt(key: String, value: Int) {
        val preferencesKey = intPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun getDataInt(key: String): Int {
        return try {
            val preferencesKey = intPreferencesKey(key)
            val preferencesFlow = context.dataStore.data.map { preferences ->
                preferences[preferencesKey] ?: 0
            }
            preferencesFlow.first()
        } catch (e: Exception){
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            0
        }
    }

    override suspend fun setDataFloat(key: String, value: Float) {
        val preferencesKey = floatPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun getDataFloat(key: String): Float {
        return try {
            val preferencesKey = floatPreferencesKey(key)
            val preferencesFlow = context.dataStore.data.map { preferences ->
                preferences[preferencesKey] ?: 0f
            }
            preferencesFlow.first()
        } catch (e: Exception){
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            0f
        }
    }
}