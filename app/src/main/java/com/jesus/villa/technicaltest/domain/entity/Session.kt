package com.jesus.villa.technicaltest.domain.entity


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
            listAuthenticationListener.forEach { listener ->
                listener.onUserLoggedIn(user)
            }
        }
    }

    override fun saveSession() {
        _token?.let {
            PersistenceUseCase().saveToken(it)
            listTokenListener.forEach{ it2 ->
                it2.onTokenChanged(it)
            }
        }
        _profileId?.let {
            PersistenceUseCase().putString(PROFILE_ID, it)
        }
        saveUserData()
        saveApplicationData()
    }

    override fun invalidate() {
        // sending logged out event to it's listener
        // i.e: Activity, Fragment, Service
        listAuthenticationListener.forEach {
            it.onUserLoggedOut()
        }

        listApplicationListener.forEach{
            it.onUserLoggedOut()
        }

        // get called when user become logged out
        // delete token and other user info
        PersistenceUseCase().removeToken()
        _token = null
        listTokenListener.forEach{
            it.onTokenChanged(EmptyToken)
        }
    }

    override fun clean() {
        // get called when user become logged out
        // delete token and other user info
        PersistenceUseCase().removeUser()
        PersistenceUseCase().removeApplicationData()
        PersistenceUseCase().removeToken()
        PersistenceUseCase().putString(PROFILE_ID, "")

        _userData = null
        _accountData = null
        _applicationData = null
        _token = null
        _profileId = null
    }

    override fun getAuthType(): String {
        var customerType = if (userData?.authType.isNullOrEmpty())
            userData?.customerType
        else
            userData?.authType

        if (customerType.isNullOrEmpty()) customerType = "payments"

        return customerType
    }

    override fun onUnauthorizedAccess(code: Int) {
        if(code == 401 || code == 403)
            this.invalidate()
    }

    override fun isDeviceValidated(): Boolean {
        val customer = PersistenceUseCase().recoverUser()?.customerId
        if(customer == null || _userData == null)
            return false
        return customer == _userData?.customerId
    }
}