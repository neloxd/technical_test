package com.jesus.villa.technicaltest.domain.usecases

import com.jesus.villa.technicaltest.data.repository.IPersistenceRepository
import com.jesus.villa.technicaltest.data.repository.PersistenceRepository
import com.jesus.villa.technicaltest.domain.entity.UserData
import java.util.*


/**
 * Created by Jesús Villa on 27/01/23
 */
interface IPersistenceUseCase {
    fun saveUser(user: UserData)
    fun recoverUser(): UserData?
    fun removeUser()
    fun isLogged(): Boolean
    fun putString(key: String, value: String)
    fun getString(key: String, defaultValue: String): String
    fun putBoolean(key: String, value: Boolean)
    fun getBoolean(key: String, defaultValue: Boolean): Boolean
    fun remove(vararg key: String)
}

open class PersistenceUseCase(private val persistenceRepository: IPersistenceRepository = PersistenceRepository()): IPersistenceUseCase {

    override fun saveUser(user: UserData) {
        return persistenceRepository.saveRememberedUser(user)
    }

    override fun recoverUser() : UserData? {
        return persistenceRepository.recoverRememberedUser()
    }

    override fun removeUser() {
        return persistenceRepository.removeRememberedUser()
    }

    override fun isLogged(): Boolean {
        return recoverUser() != null
    }

    override fun putString(key: String, value: String) {
        persistenceRepository.putString(key, value)
    }

    override fun getString(key: String, defaultValue: String): String {
        return persistenceRepository.getString(key, defaultValue)
    }

    override fun putBoolean(key: String, value: Boolean) {
        persistenceRepository.putBoolean(key, value)
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return persistenceRepository.getBoolean(key, defaultValue)
    }

    override fun remove(vararg key: String) = persistenceRepository.remove(*key)
}