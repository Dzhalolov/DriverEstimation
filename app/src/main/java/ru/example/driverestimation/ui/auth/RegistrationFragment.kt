package ru.example.driverestimation.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fr_auth.*
import kotlinx.android.synthetic.main.fr_registration.*
import kotlinx.android.synthetic.main.fr_registration.et_email
import ru.example.driverestimation.R
import ru.example.driverestimation.model.User
import ru.example.driverestimation.utils.SharedPreferencesHelper
import java.util.*

class RegistrationFragment : Fragment() {

    private val TAG: String = "DEBUG_TAG"
    var mSharedPreferencesHelper: SharedPreferencesHelper? = null

    companion object {
        fun newInstance(): RegistrationFragment {
            val args = Bundle()

            val fragment = RegistrationFragment()
            fragment.arguments = args

            return fragment
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

        btn_registration_submit.setOnClickListener {
            if (isDataValid()) {
                val user = User(
                    (UUID.randomUUID().toString()).toLong(),
                    et_email.text.toString(),
                    et_registration_name.text.toString(),
                    et_password.text.toString(),
                    et_registration_car_name.text.toString(),
                    ""
                )
                Log.d(TAG, "onActivityCreated: " + user.toString())
                //mSharedPreferencesHelper?.addUser(user)
                fragmentManager?.popBackStack()
                Toast.makeText(activity, "Successful registration!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isDataValid(): Boolean {
        return true
    }

}