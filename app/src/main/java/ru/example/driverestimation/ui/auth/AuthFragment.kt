package ru.example.driverestimation.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fr_auth.*
import ru.example.driverestimation.R
import ru.example.driverestimation.ui.personal_area.PersonalAreaActivity
import ru.example.driverestimation.utils.SharedPreferencesHelper

class AuthFragment : Fragment(R.layout.fr_auth) {

    var sharedPreferencesHelper: SharedPreferencesHelper? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        sharedPreferencesHelper = SharedPreferencesHelper(activity!!)

        //on button sign in click listener
        btn_sign_in.setOnClickListener {
            if (isValidData()) {
                //get user from sharedPref
                val user = sharedPreferencesHelper!!.login(et_email.text.toString())

                //if correct data go to the personal area
                if (user != null) {
                    val startPersonalAreaActivity =
                        Intent(activity, PersonalAreaActivity::class.java)

                    //sending user id to PersonalAreaActivity
                    val extras = Bundle()
                    extras.putLong(PersonalAreaActivity.USER_CODE, user.id)
                    startPersonalAreaActivity.putExtras(extras)

                    startActivity(startPersonalAreaActivity)

                    //destroying AuthActivity
                    activity?.finish()
                } else {
                    Toast.makeText(activity, "Wrong email or password.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        //on button sign up click listener
        btn_sign_up.setOnClickListener {
            //go to the registration fragment
            fragmentManager?.beginTransaction()
                ?.replace(R.id.auth_container, RegistrationFragment())
                ?.addToBackStack(RegistrationFragment::class.java.name)
                ?.commit()
        }

        //not yet implemented
        tv_forgot_password.setOnClickListener {
            Toast.makeText(activity, "I forgot password", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isValidData(): Boolean {
        val email = et_email.text.toString()
        val password = et_password.text.toString()

        return if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(activity, "Please, fill all the fields.", Toast.LENGTH_SHORT).show()
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || password.length < 6) {
            Toast.makeText(activity, "Invalid email or password", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }

}