package mx.uttt.palabrin.domain.useCases.dataStore

import mx.uttt.palabrin.data.repository.dataStore.DataStoreRepository
import javax.inject.Inject

class GetDataFloat @Inject constructor(
    private val _repository: DataStoreRepository
) {
    suspend operator fun invoke(key: String): Float = _repository.getDataFloat(key)
}