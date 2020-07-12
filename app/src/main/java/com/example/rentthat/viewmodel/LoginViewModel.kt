package com.example.rentthat.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.rentthat.backend.AuthService
import com.example.rentthat.model.User
import com.example.rentthat.utils.RetrofitProvider
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class LoginViewModel( application: Application) : AndroidViewModel(application) {

   var authService : AuthService = RetrofitProvider.createService(application,AuthService::class.java)

    fun setCurentAccount(account :GoogleSignInAccount) {
        if(account!=null)
        {
            println(account.email+account.displayName+account.photoUrl)
         authService.login(User(account.email!!,account.displayName!!, account.email!!,account.photoUrl.toString()))
               .enqueue(object : Callback<User> {
                   override fun onFailure(call: Call<User>?, t: Throwable?) {

                   }
                   override fun onResponse(call: Call<User>?, response: Response<User>?) {
                       AuthService.updateFullUser(response!!.body())
                       AuthService.updateAuthenticationUser(User(account.email!!,account.displayName!!, account.email!!,account.photoUrl.toString()))
                   }
               })




        }

    }
}