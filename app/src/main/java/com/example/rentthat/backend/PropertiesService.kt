package com.example.rentthat.backend

import com.example.rentthat.model.Propertie
import retrofit2.http.*

interface PropertiesService {
    @GET("propertie/all")
   suspend fun getAllProperties():ArrayList<Propertie>
    @GET("propertie/get/fromUser/{id}")
    suspend fun getAllPropertiesForUser(@Path("id") id: String):ArrayList<Propertie>
    @DELETE("propertie/delete/{id}")
    suspend fun deletePropertie(@Path("id") id:Long)

    @POST("propertie/savepropertie")
    suspend fun addPropertie(@Body propertie: Propertie) :Propertie
}