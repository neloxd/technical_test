package com.jesus.villa.technicaltest.data.repository

import android.util.Log
import com.google.gson.Gson
import com.jesus.villa.technicaltest.domain.entity.UserData
import com.jesus.villa.technicaltest.util.getDefaultSharedPreferencesMode
import com.jesus.villa.technicaltest.util.getDefaultSharedPreferencesName
import com.jesus.villa.technicaltest.domain.entity.Core
import java.lang.Exception


/**
 * Created by Jesús Villa on 22/01/23
 */
class PersistenceRepository : IPersistenceRepository {


    companion object {
        private const val REMEMBER_USER = "key_remember_user"
    }

    override fun saveRememberedUser(user: UserData) {

        val mContext = Core.instance.context
        val sharedPref = mContext.getSharedPreferences(
            getDefaultSharedPreferencesName(mContext),
            getDefaultSharedPreferencesMode()
        ) ?: return

        with(sharedPref.edit()) {
            val text = Gson().toJson(user)
            putString(REMEMBER_USER, text)
            commit()
        }
    }

    override fun recoverRememberedUser(): UserData? {
        val mContext = Core.instance.context

        val sharedPref = mContext.getSharedPreferences(
            getDefaultSharedPreferencesName(mContext),
            getDefaultSharedPreferencesMode()
        ) ?: return null
        val user = sharedPref.getString(REMEMBER_USER, null)
        user?.let {
            return try {
                Gson().fromJson(it, UserData::class.java)
            } catch (ex: Exception) {
                Log.e("PersistenceRepository", ex.message.orEmpty())
                null
            }
        }
        return null
    }

    override fun removeRememberedUser() {
        val mContext = Core.instance.context

        val sharedPref = mContext.getSharedPreferences(
            getDefaultSharedPreferencesName(mContext),
            getDefaultSharedPreferencesMode()
        ) ?: return

        with(sharedPref.edit()) {
            remove(REMEMBER_USER)
            remove(REMEMBER_USER + "secret")
            commit()
        }
    }

    override fun putString(key: String, value: String) {
        val mContext = Core.instance.context
        val sharedPref = mContext.getSharedPreferences(
            getDefaultSharedPreferencesName(mContext),
            getDefaultSharedPreferencesMode()
        ) ?: return

        with(sharedPref.edit()) {
            putString(key, value)
            commit()
        }
    }

    override fun getString(key: String, defaultValue: String): String {
        val mContext = Core.instance.context

        val sharedPref = mContext.getSharedPreferences(
            getDefaultSharedPreferencesName(mContext),
            getDefaultSharedPreferencesMode()
        ) ?: return defaultValue

        return sharedPref.getString(key, defaultValue) ?: ""
    }

    override fun putBoolean(key: String, value: Boolean) {
        val mContext = Core.instance.context
        val sharedPref = mContext.getSharedPreferences(
            getDefaultSharedPreferencesName(mContext),
            getDefaultSharedPreferencesMode()
        ) ?: return

        with (sharedPref.edit()) {
            putBoolean(key, value)
            commit()
        }
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        val mContext = Core.instance.context

        val sharedPref = mContext.getSharedPreferences(
            getDefaultSharedPreferencesName(mContext),
            getDefaultSharedPreferencesMode()
        ) ?: return defaultValue

        return sharedPref.getBoolean(key, defaultValue)
    }

    override fun remove(vararg keys: String) {
        val mContext = Core.instance.context
        val sharedPref = mContext.getSharedPreferences(
            getDefaultSharedPreferencesName(mContext),
            getDefaultSharedPreferencesMode()
        ) ?: return

        with (sharedPref.edit()) {
            keys.forEach { remove(it) }
            commit()
        }
    }
}