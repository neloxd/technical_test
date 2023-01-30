package com.jesus.villa.technicaltest.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jesus.villa.technicaltest.data.repository.IPersistenceRepository
import com.jesus.villa.technicaltest.domain.entity.UserData


/**
 * Created by Jesús Villa on 29/01/23
 */
class OperationViewModel(private val onIPersistenceRepository: IPersistenceRepository): ViewModel(){

    private val mUserData= MutableLiveData<UserData>()
    fun getUserData(): LiveData<UserData> = mUserData

    fun saveUser(userData: UserData) {
        mUserData.value = userData
    }

    fun saveData() {
        onIPersistenceRepository.saveRememberedUser(mUserData.value!!)
    }
}