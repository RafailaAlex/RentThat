package com.example.rentthat.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rentthat.R
import com.example.rentthat.model.Propertie
import com.example.rentthat.model.Type
import com.example.rentthat.utils.PropertiesRecyclerViewAdapter
import com.example.rentthat.viewmodel.FilteredPropertieViewModel
import kotlinx.android.synthetic.main.activity_filtered_properties.*

class FilteredPropertiesActivity : AppCompatActivity() ,PropertiesRecyclerViewAdapter.onItemClickListener{
    lateinit var filteredPropertiesViewModel: FilteredPropertieViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtered_properties)
        filteredPropertiesViewModel= ViewModelProvider(this).get(FilteredPropertieViewModel::class.java)
        var recyclerView: RecyclerView =findViewById(R.id.recyclerViewForFilteredProperties)
        recyclerView.layoutManager= LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        FilteredPropertieViewModel.init()
        val adapter= PropertiesRecyclerViewAdapter(FilteredPropertieViewModel.properties,this)
        FilteredPropertieViewModel.properties.observe(this,object: Observer<ArrayList<Propertie>> {
            override fun onChanged(t: ArrayList<Propertie>?) {
                if(t!=null){
                    adapter.submitList(t)
                }
            }
        })
        buttonTypeFiltered.setOnClickListener {
            var ts :Spinner = findViewById(R.id.spinnerFiltered)
            var tp : String =ts.selectedItem.toString()
            filteredPropertiesViewModel.modifyPropertiesByType(Type.valueOf(tp))
        }
        buttonFilterByAdress.setOnClickListener {
            var adressstxt: EditText= findViewById(R.id.editTextFilterByAdress)
            var adressvalue: String = adressstxt.text.toString()
            if(adressvalue.equals("")){
                Toast.makeText(this,"You cannot use filter on blank fields", Toast.LENGTH_SHORT).show()
            }else{
                filteredPropertiesViewModel.modifyPropertieByAdress(adressvalue)
                adressstxt.setText("")
            }

        }
        buttonResetFilter.setOnClickListener {
            filteredPropertiesViewModel.initAll()
            var adressstxt: EditText= findViewById(R.id.editTextFilterByAdress)
            adressstxt.setText("")
        }
        filteredPropertiesViewModel.initAll()
        recyclerView.adapter=adapter
    }
    override fun onItemClick(propertie: Propertie, position:Int) {
        //println(propertie)
        val intent= Intent(this,RentDetails::class.java)
        intent.putExtra("id",propertie.id)
        intent.putExtra("userId",propertie.userId)
        intent.putExtra("adress",propertie.adress)
        intent.putExtra("description",propertie.description)
        intent.putExtra("price",propertie.price)
        intent.putExtra("type",propertie.type.toString())
        startActivity(intent)
    }
}
