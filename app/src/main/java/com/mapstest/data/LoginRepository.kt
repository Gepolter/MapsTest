package com.mapstest.data

import com.mapstest.data.model.LoggedInUser

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val dataSource: LoginDataSource) {

    // in-memory cache of the loggedInUser object
    var user: RetrofitStuff.RetrofitHelper.Result? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        dataSource.logout()
    }

    fun login(username: String, password: String): RetrofitStuff.RetrofitHelper.Result {
        // handle login
        val result = dataSource.login(username, password)

        if (result != null) {
            setLoggedInUser(result)
        }

        return result
    }

    private fun setLoggedInUser(loggedInUser: RetrofitStuff.RetrofitHelper.Result) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}