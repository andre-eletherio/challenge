package br.com.edutech.service

import android.util.Log
import br.com.edutech.model.RegisterLike
import br.com.edutech.model.RegisterResponse
import br.com.edutech.model.RegisterUser
import br.com.edutech.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    @POST("fiap")
    fun registerUser(@Body registerUser: RegisterUser): Call<RegisterResponse>

    @PATCH("fiap/{id}")
    fun updateUser(@Path("id") id: String, @Body user: User): Call<User>

    @GET("fiap")
    fun getUser(
        @Query("seen") seen: Boolean? = null,
        @Query("email") email: String? = null,
        @Query("degree") degree: String? = null,
        @Query("location") location: String? = null,
        @Query("availability") availability: Int? = null,
        @Query("liked") liked: Boolean? = null
    ): Call<List<User>>

    @POST("fiap/like")
    fun like(@Body registerLike: RegisterLike): Call<String>

    @GET("fiap/likes")
    fun getLikes(): Call<List<User>>

//    @POST("fiap/login")
//    fun login(@Body registerUser: RegisterUser): Call<User>
}