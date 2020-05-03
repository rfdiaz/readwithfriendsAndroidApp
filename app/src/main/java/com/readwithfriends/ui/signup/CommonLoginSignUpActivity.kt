package com.readwithfriends.ui.signup

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.readwithfriends.extensions.gone
import com.readwithfriends.extensions.visible
import com.readwithfriends.ui.home.HomeActivity
import com.readwithfriends.viewmodel.state.AuthenticationState
import kotlinx.android.synthetic.main.activity_signup.*

open class CommonLoginSignUpActivity : AppCompatActivity(){

    fun handleAuthenticationState(state: AuthenticationState) {
        when (state) {
            is AuthenticationState.Loading -> showLoading()
            is AuthenticationState.Authenticated -> showSuccess()
            is AuthenticationState.AuthenticatingError -> showError(state)
        }
    }

    private fun showLoading() {
        loading.visible()
    }

    private fun showSuccess() {
        loading.gone()
        HomeActivity.startActivity(this)
        finish()
    }

    private fun showError(state: AuthenticationState.AuthenticatingError) {
        loading.gone()
        Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
    }

}