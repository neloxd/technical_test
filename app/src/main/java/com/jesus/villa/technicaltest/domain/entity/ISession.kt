package com.jesus.villa.technicaltest.domain.entity


/**
 * Created by Jesús Villa on 22/01/23
 */
interface ISession {

    var userData: UserData?

    fun isLoggedIn(): Boolean

    fun saveSession()

    fun clean()

    fun saveUserData()
}