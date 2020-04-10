package com.readwithfriends.ui.signup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.readwithfriends.R
import com.readwithfriends.extensions.getViewModel
import com.readwithfriends.extensions.gone
import com.readwithfriends.extensions.observe
import com.readwithfriends.extensions.visible
import com.readwithfriends.ui.home.HomeActivity
import com.readwithfriends.viewmodel.AuthenticationViewModel
import com.readwithfriends.viewmodel.state.AuthenticationState
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, SignupActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) // Finishes all previous activities
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val activity = this@SignupActivity
        getViewModel(AuthenticationViewModel::class.java) {
            authenticationState.observe(activity) {
                handleAuthenticationState(it)
            }
            button.setOnClickListener {
                this.signUp(username.text.toString(), password.text.toString())
            }
        }
    }

    private fun handleAuthenticationState(state: AuthenticationState) {
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