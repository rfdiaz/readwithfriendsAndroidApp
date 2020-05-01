package com.readwithfriends.ui.home

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.readwithfriends.R
import com.readwithfriends.extensions.getViewModel
import com.readwithfriends.extensions.observe
import com.readwithfriends.ui.signup.LoginActivity
import com.readwithfriends.ui.signup.SignupActivity
import com.readwithfriends.viewmodel.AuthenticationViewModel
import com.readwithfriends.viewmodel.UserDataViewModel
import com.readwithfriends.viewmodel.state.AuthenticationState
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, HomeActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val activity = this@HomeActivity

        // UserDataViewModel to display user data
        getViewModel(UserDataViewModel::class.java) {
            email.observe(activity) {
                showUsername(it)
            }
        }

        // AuthenticationViewModel to handle the sign out event
        getViewModel(AuthenticationViewModel::class.java) {
            authenticationState.observe(activity) {
                handleAuthenticationState(it)
            }
            signOut.setOnClickListener {
                signOut()
            }
        }
    }

    private fun showUsername(email: String) {
        list.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        list.adapter = ListAdapter(listOf(email))
    }

    private fun handleAuthenticationState(state: AuthenticationState) {
        if (!state.isAuthenticated()) {
            LoginActivity.startActivity(this)
            finish()
        }
    }
}