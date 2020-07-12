package com.example.rentthat.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rentthat.backend.PropertiesService
import com.example.rentthat.model.Propertie
import com.example.rentthat.utils.RetrofitProvider
import kotlinx.coroutines.launch

class PropertiesViewModel(application: Application) : AndroidViewModel(application)  {
    var propertiesService : PropertiesService =RetrofitProvider.createService(application,PropertiesService::class.java)
    companion object{
        var properties: MutableLiveData<ArrayList<Propertie>> = MutableLiveData()
        var backgroundproperties: MutableLiveData<ArrayList<Propertie>> = MutableLiveData()
        fun init(){
            properties.value =ArrayList()
            backgroundproperties.value=ArrayList()
        }

        fun getProperties(): LiveData<ArrayList<Propertie>> {
            return properties
        }
        fun verifyChanges() : Boolean{
            if(properties.value != null && backgroundproperties.value!=null){
                if(properties.value!!.size== backgroundproperties.value!!.size){
                    var i=0
                    var ok =false
                    for(i in 0 until properties.value!!.size){
                        if(properties.value!![i] != backgroundproperties.value!![i])
                            ok=true

                    }
                    return ok
                }
                return true
            }
            return false
        }
    }
    fun addPropertie(propertie: Propertie){
        viewModelScope.launch {
            var p :Propertie =  propertiesService.addPropertie(propertie)
        }
    }
    fun getPropertiesFromApi(){
        viewModelScope.launch {
            properties.value=propertiesService.getAllProperties()
            //println(propertiesService.getAllProperties())
        }
        println("from api")
    }
    fun getBackPropertiesFromApi(){
        viewModelScope.launch {
            backgroundproperties.value=propertiesService.getAllProperties()
            //println(propertiesService.getAllProperties())
        }
        println("from api")
    }




}