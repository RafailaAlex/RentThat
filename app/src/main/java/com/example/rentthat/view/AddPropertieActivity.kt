package com.example.rentthat.view

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.rentthat.R
import com.example.rentthat.viewmodel.AddPropertieViewModel
import kotlinx.android.synthetic.main.activity_add_propertie.*


class AddPropertieActivity : AppCompatActivity() {
        lateinit var addPropertieViewModel: AddPropertieViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_propertie)
        addPropertieViewModel= ViewModelProvider(this).get(AddPropertieViewModel::class.java)
        var typeSpinner:Spinner =findViewById<Spinner>(R.id.typePropertieSpinner)
        var adresEdit =findViewById<EditText>(R.id.adressAddActivityEditText)
        var descriptionEdit=findViewById<EditText>(R.id.descriptionAddActivityEditText)
        var priceEdit=findViewById<EditText>(R.id.priceAddActivityEditText)
        addAddActivityButton.setOnClickListener {
            if (adresEdit.text.toString() == "" || descriptionEdit.text.toString() == "" || priceEdit.text.toString() == "") {
                Toast.makeText(this,"You cannot have blank fields",Toast.LENGTH_SHORT).show()
            } else {
                addPropertieViewModel.addPropertie(
                    adresEdit.text.toString(),
                    descriptionEdit.text.toString(),
                    typeSpinner.selectedItem.toString(),
                    priceEdit.text.toString().toDouble()
                )
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
            }
        }
    }

}
