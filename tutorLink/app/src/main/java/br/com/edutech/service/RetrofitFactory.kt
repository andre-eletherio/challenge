package br.com.edutech.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {

    private val URL = "https://caa32ee1224e410b84f3.free.beeceptor.com/"

    private val retrofitFactory = Retrofit
        .Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun userService(): UserService {
        return retrofitFactory.create(UserService::class.java)
    }
}