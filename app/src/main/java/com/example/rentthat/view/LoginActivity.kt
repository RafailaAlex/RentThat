package com.example.rentthat.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.rentthat.R
import com.example.rentthat.viewmodel.LoginViewModel


import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    val RC_SIGN_IN=0
  lateinit var  loginViewModel :LoginViewModel

    override fun onStart() {
        super.onStart()

    }
    fun logat(){
        val account=GoogleSignIn.getLastSignedInAccount(this)
        updateUI(account)
    }
    fun updateUI(account: GoogleSignInAccount?) {
        if (account != null) {
            val intent=Intent(this, ProfileActivity::class.java)
            if(loginViewModel!=null){

                loginViewModel.setCurentAccount(account)
                startActivity(intent)
            }else{
                Toast.makeText(this,"dasdad",Toast.LENGTH_SHORT)
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
      loginViewModel= ViewModelProvider(this).get(LoginViewModel::class.java)


        logat()
        val gso: GoogleSignInOptions=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        sign_in_button.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);

        if (requestCode == RC_SIGN_IN) { // The Task returned from this call is always completed, no need to attach
// a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            handleSignInResult(task)
        }
    }
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            // Signed in successfully, show authenticated UI.
            updateUI(account)
        } catch (e: ApiException) {
            updateUI(null)
        }
    }
}
