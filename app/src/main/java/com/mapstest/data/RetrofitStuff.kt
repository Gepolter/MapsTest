package com.mapstest.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.Serializable


interface RetrofitStuff {

    object RetrofitHelper {
        //Backend - URL perspective of localhost of emulated devices
        val baseUrl = "http://10.0.2.2:3050/"


        fun getInstance(): Retrofit {
            //Used this to debug the req param of the login function. Had to disable encryption...
            var logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            var httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)

                    return Retrofit.Builder().baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        // we need to add converter factory to
                        // convert JSON object to Java object
                        .client(httpClient.build())
                        .build()
        }

        interface OoeApi {
            //parameterize

            @GET("data/UserData/{userPw}")
            suspend fun getUserData(@Path(value="userPw", encoded = true) userPw: String): Response<Result>
        }
        data class Result(
            val artists: MutableList<Artist> = mutableListOf<Artist>(),
            val user: User = User("defaultName", "defaultPw", "defaultMail", listOf<Double>(0.0, 0.0), "", listOf<ArtistSub>())
        ): Serializable
        data class Artist(
            val artistName: String,
            val artistMarker: String,
            val gigData: List<Venue>
        ): Serializable
        data class Venue(
            val gigs: List<Gig>,
            val venueName: String,
            val venueAddress: String,
            val venueLatLng: List<Double>
        ): Serializable
        data class Gig(
            val date: String,
            val link: String
        ): Serializable

        data class User(
            val userName: String,
            //terrible, terrible, terrible handling :D for actual use never http GET
            //instead write backend function to return true/false for given pw
            val pw: String,
            val mail: String,
            val locationLatLng: List<Double>,
            val distMetric: String,
            val artistSubs: List<ArtistSub>
        ): Serializable
        data class ArtistSub(
            val artistName: String,
            val maxGigDistance: Number
        ): Serializable
    }

}
