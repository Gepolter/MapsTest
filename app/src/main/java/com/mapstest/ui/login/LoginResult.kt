package com.mapstest.ui.login

import com.mapstest.data.RetrofitStuff

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: RetrofitStuff.RetrofitHelper.Result? = null,
    val error: Int? = null
)