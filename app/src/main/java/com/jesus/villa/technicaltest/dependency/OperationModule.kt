package com.jesus.villa.technicaltest.dependency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.jesus.villa.technicaltest.data.repository.IPersistenceRepository
import com.jesus.villa.technicaltest.data.repository.PersistenceRepository
import com.jesus.villa.technicaltest.presentation.OperationViewModel
import dagger.Provides
import javax.inject.Singleton


/**
 * Created by Jesús Villa on 29/01/23
 */
object OperationModule {

    //region ViewModel
    class OperationViewModelFactory(
        private val persistenceRepository: IPersistenceRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(
                IPersistenceRepository::class.java
            )
                .newInstance(persistenceRepository)
        }
    }

    fun provideOperationViewModelFactory(): OperationViewModelFactory {
        return OperationViewModelFactory(providePersistenceRepository())
    }

    fun provideLoginViewModel(viewModelStoreOwner: ViewModelStoreOwner): OperationViewModel {
        val loginViewModelFactory = OperationViewModelFactory(
            providePersistenceRepository()
        )
        return ViewModelProvider(
            viewModelStoreOwner,
            loginViewModelFactory
        )
            .get(OperationViewModel::class.java)
    }

    private fun providePersistenceRepository(): IPersistenceRepository {
        return PersistenceRepository()
    }
    //endregion
}