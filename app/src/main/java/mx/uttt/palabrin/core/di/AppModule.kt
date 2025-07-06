package mx.uttt.palabrin.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mx.uttt.palabrin.data.repository.dataStore.DataStoreRepository
import mx.uttt.palabrin.data.repository.firebase.FireAuthRepository
import mx.uttt.palabrin.data.repository.user.UserRepository
import mx.uttt.palabrin.domain.useCases.dataStore.DataStoreUseCases
import mx.uttt.palabrin.domain.useCases.dataStore.GetDataBoolean
import mx.uttt.palabrin.domain.useCases.dataStore.GetDataFloat
import mx.uttt.palabrin.domain.useCases.dataStore.GetDataInt
import mx.uttt.palabrin.domain.useCases.dataStore.GetDataString
import mx.uttt.palabrin.domain.useCases.dataStore.SetDataBoolean
import mx.uttt.palabrin.domain.useCases.dataStore.SetDataFloat
import mx.uttt.palabrin.domain.useCases.dataStore.SetDataInt
import mx.uttt.palabrin.domain.useCases.dataStore.SetDataString
import mx.uttt.palabrin.domain.useCases.firebase.FireAuthUseCases
import mx.uttt.palabrin.domain.useCases.firebase.LoginUser
import mx.uttt.palabrin.domain.useCases.firebase.RegisterUser
import mx.uttt.palabrin.domain.useCases.users.GetUser
import mx.uttt.palabrin.domain.useCases.users.UserUseCases
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideDataStoreUseCases(dataStoreRepository: DataStoreRepository): DataStoreUseCases =
        DataStoreUseCases(
            setDataString = SetDataString(dataStoreRepository),
            getDataString = GetDataString(dataStoreRepository),
            setDataBoolean = SetDataBoolean(dataStoreRepository),
            getDataBoolean = GetDataBoolean(dataStoreRepository),
            setDataInt = SetDataInt(dataStoreRepository),
            getDataInt = GetDataInt(dataStoreRepository),
            setDataFloat = SetDataFloat(dataStoreRepository),
            getDataFloat = GetDataFloat(dataStoreRepository)
        )

    @Singleton
    @Provides
    fun provideFireAuthUseCases(fireAuthRepository: FireAuthRepository): FireAuthUseCases =
        FireAuthUseCases(
            registerUser = RegisterUser(fireAuthRepository),
            loginUser = LoginUser(fireAuthRepository)
        )

    @Singleton
    @Provides
    fun provideUserUseCases(userRepository: UserRepository): UserUseCases =
        UserUseCases(
            getUser = GetUser(userRepository)
        )

}