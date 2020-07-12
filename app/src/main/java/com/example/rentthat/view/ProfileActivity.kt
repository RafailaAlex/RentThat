package com.example.rentthat.view

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rentthat.R
import com.example.rentthat.backend.AuthService
import com.example.rentthat.model.Propertie
import com.example.rentthat.model.User
import com.example.rentthat.utils.PropertiesRecyclerViewAdapter
import com.example.rentthat.utils.PropertiesRecyclerViewAdapterForProfile
import com.example.rentthat.viewmodel.ProfileViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_profile.*


class ProfileActivity : AppCompatActivity(),PropertiesRecyclerViewAdapterForProfile.onItemClickListener{
    lateinit var profileViewModel:ProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        updateStats()
        AuthService.authenticationUser.observe(this, Observer<User>{updateStats()})
        logOut()

        goToRentsButton.setOnClickListener {
           val intent=Intent(this,PropertiesActivity::class.java)
            startActivity(intent)
        }
        goToAddButton.setOnClickListener {
            val intent=Intent(this,AddPropertieActivity::class.java)
            startActivity(intent)
        }

        var recyclerView: RecyclerView =findViewById(R.id.profileRecyclerView)
        recyclerView.layoutManager= LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        val adapter= PropertiesRecyclerViewAdapterForProfile(ProfileViewModel.properties,this)
        ProfileViewModel.init()
        recyclerView.adapter=adapter
       // profileViewModel.getPropertiesFromApi(AuthService.idUser.toString())
        AuthService.authenticationUser.observe(this,object: Observer<User>{
            override fun onChanged(t: User?) {
                if(t != null){
                    profileViewModel.getPropertiesFromApi(t!!.id.toString())
                }else{
                    ProfileViewModel.properties.value=ArrayList()
                    //adapter.submitList()
                }
            }


        })
        ProfileViewModel.properties.observe(this,object: Observer<ArrayList<Propertie>>{
            override fun onChanged(t: ArrayList<Propertie>?) {
                if(t!=null){
                    adapter.submitList(t) }
            }
        })
    }
    fun updateStats(){
        if(profileViewModel.getImageUrl()!=""){
          var  profileImage=findViewById<ImageView>(R.id.profilePicture)
            Glide.with(this).load(profileViewModel.getImageUrl()).into(profileImage)
        }
        displayNameTextView.text=profileViewModel.getUserDisplayName()
        emailTextView.text=profileViewModel.getUserEmail()
    }
    fun logOut(){
        val account = GoogleSignIn.getLastSignedInAccount(this)
        val gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        signOutButton.setOnClickListener {
            mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, object : OnCompleteListener<Void?> {
                    override fun onComplete(task: Task<Void?>) { // ...
                        val intent= Intent(this@ProfileActivity, LoginActivity::class.java)
                        AuthService.authenticationUser.value=null
                        startActivity(intent)
                        val t=  Toast.makeText(this@ProfileActivity,"Logged out",Toast.LENGTH_LONG)
                        t.show()
                    }
                })
        }
    }

    override fun onItemClick(propertie: Propertie, position: Int) {
        val intent= Intent(this,RentDetailsForOwner::class.java)
        intent.putExtra("id",propertie.id)
        intent.putExtra("adress",propertie.adress)
        intent.putExtra("description",propertie.description)
        intent.putExtra("price",propertie.price)
        intent.putExtra("type",propertie.type.toString())
        startActivity(intent)
    }
}
