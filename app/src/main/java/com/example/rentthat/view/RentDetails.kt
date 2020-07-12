package com.example.rentthat.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.rentthat.R
import com.example.rentthat.backend.UserService
import com.example.rentthat.model.Type
import com.example.rentthat.model.User
import com.example.rentthat.utils.RetrofitProvider
import kotlinx.android.synthetic.main.activity_rent_details.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RentDetails : AppCompatActivity() {
     var  userService:UserService=RetrofitProvider.createService(this,UserService::class.java)
         var user: MutableLiveData<User> = MutableLiveData()
     var id:Long =0
    lateinit  var userId:String
    lateinit  var adress:String
    lateinit   var description:String
     var price:Double=0.0
    lateinit var type1:String
    fun initUser(id:String){
        GlobalScope.launch {
            user.postValue(userService.getById(id))
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rent_details)
         id =intent.getLongExtra("id",0)
         userId=intent.getStringExtra("userId")
         adress=intent.getStringExtra("adress")
         description=intent.getStringExtra("description")
         price=intent.getDoubleExtra("price",0.0)
        var type1:String = intent.getStringExtra("type")

        var type:Type= Type.valueOf(type1)
        initUser(userId)
        adressRentDetailsTextView.text=adress
        descriptionRentDetailsTextView.text=description
        typeRentDetailsTextView.text=type1
        priceRentDetailsTextView.text=price.toString()
        user.observe(this, Observer {
            ownerRentDetailsTextView.text=it.displayName
        })
        rentRentDetailsButton.setOnClickListener {
            sendEmail()
        }
    }
    fun sendEmail(){
        val mIntent = Intent(Intent.ACTION_SEND)
        mIntent.data = Uri.parse("mailto:")
        mIntent.type = "text/plain"
       // mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("ads@gmail.com"))
        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(user.value!!.email))

        mIntent.putExtra(Intent.EXTRA_SUBJECT, "Rent")
        mIntent.putExtra(Intent.EXTRA_TEXT, "Hello i want to rent the ad with id="+id)
        try {

            startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
        }
        catch (e: Exception){
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }

    }
}
