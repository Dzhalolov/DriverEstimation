package ru.example.driverestimation.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fr_auth.*
import ru.example.driverestimation.MainActivity
import ru.example.driverestimation.R
import ru.example.driverestimation.utils.UserController
import ru.example.driverestimation.utils.showMsg

class AuthFragment : Fragment(R.layout.fr_auth) {

    companion object {
        val USER_CODE: String = "USER_CODE"
    }

    private lateinit var userController: UserController

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        userController = UserController(activity!!)

        //on button sign in click listener
        btnSignIn.setOnClickListener {
            if (isValidData()) {
                //get user from sharedPref
                val user = userController.login(etEmail.text.toString())

                //if correct data go to the personal area
                if (user != null) {

                    val startPersonalAreaActivity =
                        Intent(activity, MainActivity::class.java)

                    //sending user id to PersonalAreaActivity
                    val extras = Bundle()
                    extras.putString(USER_CODE, user.email)
                    startPersonalAreaActivity.putExtras(extras)

                    startActivity(startPersonalAreaActivity)

                    //destroying AuthActivity
                    activity?.finish()
                } else {
                    showMsg(activity!!, "Wrong email or password.")
                }
            }
        }

        //on button sign up click listener
        btnSignUp.setOnClickListener {
            //go to the registration fragment
            fragmentManager?.beginTransaction()
                ?.replace(R.id.authContainer, RegistrationFragment())
                ?.addToBackStack(RegistrationFragment::class.java.name)
                ?.commit()
        }

        //not yet implemented
        tvForgotPassword.setOnClickListener {
            showMsg(activity!!, "I forgot password")
        }
    }

    private fun isValidData(): Boolean {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        return if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            showMsg(activity!!, "Please, fill all the fields.")
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || password.length < 6) {
            showMsg(activity!!, "Invalid email or password")
            false
        } else {
            true
        }
    }

}