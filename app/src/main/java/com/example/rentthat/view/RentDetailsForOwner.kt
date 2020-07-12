package com.example.rentthat.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.rentthat.R
import com.example.rentthat.model.Type
import com.example.rentthat.viewmodel.RentDetailsForOwnerViewModel
import kotlinx.android.synthetic.main.activity_rent_details_for_owner.*

class RentDetailsForOwner : AppCompatActivity() {
    lateinit var vm:RentDetailsForOwnerViewModel
    var id:Long =0
    lateinit  var adress:String
    lateinit   var description:String
    var price:Double=0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rent_details_for_owner)
        vm= ViewModelProvider(this).get(RentDetailsForOwnerViewModel::class.java)
        id =intent.getLongExtra("id",0)
        adress=intent.getStringExtra("adress")
        description=intent.getStringExtra("description")
        price=intent.getDoubleExtra("price",0.0)
        var type1:String = intent.getStringExtra("type")
        var type: Type = Type.valueOf(type1)
         idActivitYOwnerTextView.text=id.toString()
        adressActivitYOwnerEditText.setText(adress)
        descriptionActivitYOwnerEditText.setText(description)
        priceActivitYOwnerEditText.setText(price.toString())
        setSpinner(type)
        modifyActivitYOwnerButton.setOnClickListener {
            if(adressActivitYOwnerEditText.text.toString() == "" || descriptionActivitYOwnerEditText.text.toString() == "" || priceActivitYOwnerEditText.text.toString() == ""){
                Toast.makeText(this,"You cannot have blank fields", Toast.LENGTH_SHORT).show()
            }else{

            vm.addPropertie(idActivitYOwnerTextView.text.toString().toLong(),adressActivitYOwnerEditText.text.toString(),descriptionActivitYOwnerEditText.text.toString(),typeActivityOwnerSpinner.selectedItem.toString(),priceActivitYOwnerEditText.text.toString().toDouble())
            val intent= Intent(this, ProfileActivity::class.java)
            startActivity(intent)}
        }
        rentActivitYOwnerButton.setOnClickListener {
            vm.deletePropertie(idActivitYOwnerTextView.text.toString().toLong())
            val intent= Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
    fun setSpinner(t:Type){
        if(t.equals(Type.PARKING)){
            typeActivityOwnerSpinner.setSelection(0)
        }
        if(t.equals(Type.INDUSTRIAL)){
            typeActivityOwnerSpinner.setSelection(1)
        }
        if(t.equals(Type.COMMERCIAL)){
            typeActivityOwnerSpinner.setSelection(2)
        }
        if(t.equals(Type.LIVING)){
            typeActivityOwnerSpinner.setSelection(3)
        }
        if(t.equals(Type.OTHER)){
            typeActivityOwnerSpinner.setSelection(4)
        }
    }
}
