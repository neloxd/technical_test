package com.jesus.villa.technicaltest.data.repository

import com.jesus.villa.technicaltest.domain.entity.UserData


/**
 * Created by Jesús Villa on 22/01/23
 */
interface IPersistenceRepository {

    @Throws(Exception::class)
    fun saveRememberedUser(user : UserData)

    @Throws(Exception::class)
    fun recoverRememberedUser() : UserData?

    @Throws(Exception::class)
    fun putString(key: String, value: String)
    fun getString(key: String, defaultValue: String): String
    fun putBoolean(key: String, value: Boolean)
    fun getBoolean(key: String, defaultValue: Boolean): Boolean
    fun remove(vararg keys: String)
}