package com.jesus.villa.technicaltest.domain.entity

import com.jesus.villa.technicaltest.domain.usecases.PersistenceUseCase


/**
 * Created by Jesús Villa on 22/01/23
 */
object Session: ISession {

    const val PROFILE_ID = "PROFILE_ID"

    private var _userData: UserData? = null
    override var userData: UserData?
        set(newUser) {
            newUser?.let {
                _userData = newUser
            }
        }
        get() {
            if(_userData == null)
                _userData = PersistenceUseCase().recoverUser()
            return _userData
        }

    override fun saveUserData() {
        _userData?.let { user ->
            PersistenceUseCase().saveUser(user)
        }
    }

    override fun saveSession() {
        saveUserData()
    }

    override fun clean() {
        // get called when user become logged out
        PersistenceUseCase().removeUser()
    }

    override fun isLoggedIn(): Boolean {
        return userData != null
    }
}