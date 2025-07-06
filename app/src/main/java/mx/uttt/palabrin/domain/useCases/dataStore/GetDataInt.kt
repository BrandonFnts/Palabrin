package mx.uttt.palabrin.domain.useCases.dataStore

import mx.uttt.palabrin.data.repository.dataStore.DataStoreRepository
import javax.inject.Inject

class GetDataInt @Inject constructor(
    private val _repository: DataStoreRepository
) {
    suspend operator fun invoke(key: String): Int = _repository.getDataInt(key)
}