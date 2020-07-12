package com.example.rentthat.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rentthat.R
import com.example.rentthat.model.Propertie
import com.example.rentthat.model.Type
import com.example.rentthat.utils.PropertiesRecyclerViewAdapter
import com.example.rentthat.viewmodel.PropertiesViewModel
import kotlinx.android.synthetic.main.activity_properties.*
import java.util.*
import kotlin.collections.ArrayList


class PropertiesActivity : AppCompatActivity(),PropertiesRecyclerViewAdapter.onItemClickListener {
    lateinit var propertiesViewModel: PropertiesViewModel
    lateinit var timer:Timer
    lateinit var timerTask: TimerTask
    var handler: Handler= Handler()
    var propertiess: MutableLiveData<ArrayList<Propertie>> = MutableLiveData()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_properties)
        propertiesViewModel= ViewModelProvider(this).get(PropertiesViewModel::class.java)
        var recyclerView:RecyclerView=findViewById(R.id.propertiesRecyclerView)
        recyclerView.layoutManager= LinearLayoutManager(this,RecyclerView.VERTICAL,false)

        PropertiesViewModel.init()
        val adapter=PropertiesRecyclerViewAdapter(PropertiesViewModel.properties,this)

        PropertiesViewModel.properties.observe(this,object: Observer<ArrayList<Propertie>>{
            override fun onChanged(t: ArrayList<Propertie>?) {
                if(t!=null){
                    adapter.submitList(t)
                }
            }
        })
        PropertiesViewModel.backgroundproperties.observe(this, object: Observer<ArrayList<Propertie>>{
            override fun onChanged(t: ArrayList<Propertie>?) {
                if(t!=null){
                    var ok=PropertiesViewModel.verifyChanges()
                    println(ok)
                    if(ok==true){
                        PropertiesViewModel.properties.value=PropertiesViewModel.backgroundproperties.value
                    }
                }
            }
        })
        propertiesViewModel.getPropertiesFromApi()
        propertiesViewModel.getBackPropertiesFromApi()
        recyclerView.adapter=adapter
        startTimer()

        buttonGoToFilter.setOnClickListener {
            val intent= Intent(this, FilteredPropertiesActivity::class.java)
            startActivity(intent)
        }
    }
    private fun startTimer() {
        timer = Timer()
        timerTask = object : TimerTask() {
            override fun run() {
                handler.post(Runnable {
                    propertiesViewModel.getBackPropertiesFromApi()
                    println("din back")
                    println(PropertiesViewModel.backgroundproperties)
                })
            }
        }
        timer.schedule(timerTask, 3000, 3000)
    }

    override fun onItemClick(propertie: Propertie, position:Int) {
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

