package com.readwithfriends.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.readwithfriends.extensions.getViewModel
import com.readwithfriends.extensions.observe
import com.readwithfriends.ui.home.HomeActivity
import com.readwithfriends.ui.signup.LoginActivity
import com.readwithfriends.viewmodel.AuthenticationViewModel

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activity = this@SplashActivity
        getViewModel(AuthenticationViewModel::class.java) {
            authenticationState.observe(activity) { state ->
                if (state.isAuthenticated()) {
                    HomeActivity.startActivity(activity)
                } else {
                    LoginActivity.startActivity(activity)
                }
                finish()
            }
        }
    }
}