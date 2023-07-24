package com.mapstest.data

import android.provider.Settings.Global
import android.util.Log
import com.mapstest.data.model.LoggedInUser
import com.mapstest.data.RetrofitStuff.RetrofitHelper.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.http.GET
import java.io.IOException


/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class LoginDataSource : RetrofitStuff {

     fun login(username: String, password: String) : RetrofitStuff.RetrofitHelper.Result {
        Log.d("loginProcess", "$password $username")
        var result = Result()
        try {
            // TODO: handle loggedInUser authentication
            val ooeApi = RetrofitStuff.RetrofitHelper.getInstance()
                .create(RetrofitStuff.RetrofitHelper.OoeApi::class.java)
            //the coroutine encapsulates the asynchronous logic of the login function

            val coroutineResultsult = GlobalScope.async {
                var userPw = username.plus("/").plus(password)
                result = Result(
                    ooeApi.getUserData(userPw).body()!!.artists,
                    ooeApi.getUserData(userPw).body()!!.user
                )
                Log.d("loginProcess", result.artists.toString() + result.user.toString())
                return@async result
            }
            return coroutineResultsult.getCompleted()
        } catch (e: Throwable) {
            Log.d("e", e.toString())
        }
         //i want js back..
         var i = 0
         while (
             result.user.userName == "defaultName" && i < 7
         ){
             Thread.sleep(1_000)
             Log.d("loginProcess", "waiting for userData")
             i++
         }
         return result
    }

    fun logout() {
        // TODO: revoke authentication
    }
}