package com.example.rentthat.backend

import androidx.lifecycle.MutableLiveData
import com.example.rentthat.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthService {
companion object{
    var idUser: String? = null
    val authenticationUser: MutableLiveData<User> = MutableLiveData()

    fun updateAuthenticationUser( user: User) {
        authenticationUser.value = user
    }
    fun updateFullUser(user: User?) {
        idUser = user?.id
    }
}

    @POST("user/saveuser")
     fun login(@Body user:User): Call<User>
}