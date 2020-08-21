package ru.example.driverestimation.ui.auth

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fr_registration.*
import ru.example.driverestimation.R
import ru.example.driverestimation.model.User
import ru.example.driverestimation.utils.SharedPreferencesHelper
import java.util.*
import kotlin.math.abs

class RegistrationFragment : Fragment() {

    private val TAG: String = "DEBUG_TAG"
    var mSharedPreferencesHelper: SharedPreferencesHelper? = null

    companion object {
        fun newInstance(): RegistrationFragment {
            return RegistrationFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fr_registration, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mSharedPreferencesHelper = SharedPreferencesHelper(activity!!)

        /* on button submit click listener.
        * if valid data:
        * add new user
        * close registration fragment*/
        btn_registration_submit.setOnClickListener {
            if (isValidData()) {
                val user = User(
                    (abs(Random().nextLong())),
                    et_email.text.toString(),
                    et_registration_name.text.toString(),
                    et_registration_password.text.toString(),
                    et_registration_car_name.text.toString(),
                    ""
                )
                mSharedPreferencesHelper?.addUser(user)
                fragmentManager?.popBackStack()
                Toast.makeText(activity, "Successful registration!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidData(): Boolean {
        val email = et_email.text.toString()
        val name = et_registration_name.text.toString()
        val password = et_registration_password.text.toString()
        val passwordAgain = et_registration_password_again.text.toString()
        val car = et_registration_car_name.text.toString()

        return if (
            TextUtils.isEmpty(email)
            || TextUtils.isEmpty(name)
            || TextUtils.isEmpty(password)
            || TextUtils.isEmpty(passwordAgain)
            || TextUtils.isEmpty(car)
        ) {
            Toast.makeText(activity, "Please, fill all the fields.", Toast.LENGTH_SHORT).show()
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || password.length < 6) {
            Toast.makeText(activity, "Invalid email or password", Toast.LENGTH_SHORT).show()
            false
        } else if (password != passwordAgain) {
            Toast.makeText(activity, "Passwords don\'t match", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }
}