package com.example.rentthat.model

data class Propertie(var id: Long?,
                      var userId: String,
                      var adress:String,
                      var description:String,
                      var type:Type,
                      var price:Double)