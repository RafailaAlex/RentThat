package com.example.rentthat.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentthat.backend.AuthService
import com.example.rentthat.backend.PropertiesService
import com.example.rentthat.model.Propertie
import com.example.rentthat.model.Type
import com.example.rentthat.utils.RetrofitProvider
import kotlinx.coroutines.launch

class RentDetailsForOwnerViewModel (application: Application) : AndroidViewModel(application) {
    var propertiesService : PropertiesService = RetrofitProvider.createService(application, PropertiesService::class.java)

    fun addPropertie( id:Long,adress :String, description :String, type:String,price :Double){
        if(AuthService.idUser!=null){
            var p : Propertie = Propertie(id, AuthService.idUser!!,adress,description, Type.valueOf(type),price)
            viewModelScope.launch {
                var s  = propertiesService.addPropertie(p)

            }
        }

    }
    fun deletePropertie(id:Long){
        viewModelScope.launch {
            propertiesService.deletePropertie(id)
        }
    }
}