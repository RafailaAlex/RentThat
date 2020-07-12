package com.example.rentthat.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rentthat.backend.PropertiesService
import com.example.rentthat.model.Propertie
import com.example.rentthat.model.Type
import com.example.rentthat.utils.RetrofitProvider
import kotlinx.coroutines.launch

class FilteredPropertieViewModel( application: Application) : AndroidViewModel(application) {
    var propertiesService: PropertiesService =
        RetrofitProvider.createService(application, PropertiesService::class.java)

    companion object {
        var properties: MutableLiveData<ArrayList<Propertie>> = MutableLiveData()
        var propertiesback: MutableLiveData<ArrayList<Propertie>> = MutableLiveData()

        fun init() {
            properties.value = ArrayList()
            propertiesback.value= ArrayList()
        }
    }
    fun getProperties(): LiveData<ArrayList<Propertie>> {
        return PropertiesViewModel.properties
    }
    fun modifyPropertiesByType(type: Type){
        var a :ArrayList<Propertie> = ArrayList()
        if(properties.value != null){
            for(x in properties.value!!)
            {
                if(x.type ==type){
                    a.add(x);
                }
            }
        }
        var b :ArrayList<Propertie> = ArrayList()
        if(propertiesback.value != null){
            for(x in propertiesback.value!!)
            {
                if(x.type ==type){
                    b.add(x);
                }
            }
        }
        if(a.size==0){

            properties.value=b
        }else{
            properties.value=a
        }




    }
    fun modifyPropertieByAdress(adr: String){
        var a :ArrayList<Propertie> = ArrayList()
        if(properties.value != null){
            for(x in properties.value!!)
            {
                if(x.adress.contains(adr)){
                    a.add(x);
                }
            }
        }
        var b :ArrayList<Propertie> = ArrayList()
        if(propertiesback.value != null){
            for(x in propertiesback.value!!)
            {
                if(x.adress.contains(adr)){
                    b.add(x);
                }
            }
        }
        if(a.size==0){
            properties.value=b
        }
        else{
            properties.value=a
        }
    }
    fun initAll(){
        getPropertiesFromApi()
        getBackPropertiesFromApi()
    }
    fun getBackPropertiesFromApi(){
        viewModelScope.launch {
            FilteredPropertieViewModel.propertiesback.value=propertiesService.getAllProperties()
            //println(propertiesService.getAllProperties())
        }
        println("from api")
    }

    fun getPropertiesFromApi(){
        viewModelScope.launch {
            FilteredPropertieViewModel.properties.value=propertiesService.getAllProperties()
            //println(propertiesService.getAllProperties())
        }
        println("from api")
    }

}