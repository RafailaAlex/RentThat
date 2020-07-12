package com.example.rentthat.backend

import com.example.rentthat.model.User
import retrofit2.http.GET
import retrofit2.http.Path
interface UserService {
    @GET("user/all")
    fun getAllUsers():ArrayList<User>
    @GET("user/get/{id}")
    suspend fun getById(@Path("id") id: String): User
}