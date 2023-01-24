package com.jesus.villa.technicaltest.domain.entity


/**
 * Created by Jesús Villa on 22/01/23
 */
interface ISession {

    var userData: UserData?

    var accountData: AccountData?

    var applicationData: ApplicationData?

    var token: IToken?

    var profileId: String

    fun isLoggedIn(): Boolean

    fun getAuthorization(): String

    fun saveSession()

    fun invalidate()

    fun clean()

    fun getAuthType(): String

    fun saveApplicationData()

    fun saveUserData()
}