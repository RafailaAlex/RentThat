package com.example.rentthat.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rentthat.backend.AuthService
import com.example.rentthat.backend.PropertiesService
import com.example.rentthat.backend.UserService
import com.example.rentthat.model.Propertie
import com.example.rentthat.model.User
import com.example.rentthat.utils.RetrofitProvider
import kotlinx.coroutines.launch
import java.lang.Exception

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    var propertiesService : PropertiesService =RetrofitProvider.createService(application,
        PropertiesService::class.java)
    companion object{
        var properties: MutableLiveData<ArrayList<Propertie>> = MutableLiveData()
        fun init(){
            properties.value=ArrayList()
        }
    }
    fun getProperties(): LiveData<ArrayList<Propertie>> {
        return ProfileViewModel.properties
    }
    fun getPropertiesFromApi(id:String){
        try{
        viewModelScope.launch {
            ProfileViewModel.properties.value=propertiesService.getAllPropertiesForUser(id)

        }

    }catch (e :Exception){

        }
    }
   var userService=RetrofitProvider
       .createService(application, UserService::class.java)
    fun getCurrentUser(): User? {
        var u: User? =AuthService.authenticationUser.value


        return u
    }
    fun getImageUrl():String{
        var  u= getCurrentUser()
        if(u!=null && u.profilePicture!=null && !u.profilePicture.equals("null")){
            return u.profilePicture.toString()}
        return ""
    }
    fun getUserDisplayName():String{
       var  u= getCurrentUser()
        if(u!=null)
        return u.displayName.toString()
        return ""
    }
    fun getUserEmail():String{
        var u=getCurrentUser()
        if(u!=null)
       return u.email.toString()
        return ""
    }
}