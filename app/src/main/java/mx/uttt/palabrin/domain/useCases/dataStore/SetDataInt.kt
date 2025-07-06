package mx.uttt.palabrin.domain.useCases.dataStore

import mx.uttt.palabrin.data.repository.dataStore.DataStoreRepository
import javax.inject.Inject

class SetDataInt @Inject constructor(
    private val _repository: DataStoreRepository
) {
    suspend operator fun invoke(key: String, value: Int) = _repository.setDataInt(key, value)
}