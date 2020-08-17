package ru.example.driverestimation

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fr_change_password.*
import kotlinx.android.synthetic.main.fr_profile.*

class ChangePasswordFragment : Fragment() {
    private var sharedPreferencesHelper: SharedPreferencesHelper? = null
    private var user: User? = null

    companion object {
        fun newInstance(): ChangePasswordFragment {
            val args = Bundle()
            val fragment = ChangePasswordFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fr_change_password, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sharedPreferencesHelper = SharedPreferencesHelper(activity!!)
        user = sharedPreferencesHelper!!.getUser(ProfileFragment.USER_ID)
        btn_change_password.setOnClickListener {
            if (isValidPassword) {
                user!!.password = et_new_password.text.toString()
                sharedPreferencesHelper!!.addUser(user!!)
                fragmentManager!!.popBackStack()
            }
        }
    }

    @get:SuppressLint("ResourceAsColor")
    private val isValidPassword: Boolean
        private get() {
            val currPass = et_curr_password.text.toString()
            val newPass = et_new_password.text.toString()
            val newPassAgain = et_new_password_again.text.toString()
            return if (TextUtils.isEmpty(currPass) || TextUtils.isEmpty(newPass) || TextUtils.isEmpty(
                    newPassAgain
                )
            ) {
                Toast.makeText(activity, "Please, fill all fields", Toast.LENGTH_SHORT).show()
                false
            } else if (currPass != user!!.password) {
                et_curr_password.setBackgroundColor(R.color.color_error_bg)
                Toast.makeText(activity, "Wrong current password!", Toast.LENGTH_SHORT).show()
                false
            } else if (currPass == newPass) {
                Toast.makeText(
                    activity,
                    "New password equals your old password",
                    Toast.LENGTH_SHORT
                ).show()
                false
            } else {
                if (newPass == newPassAgain) {
                    return true
                }
                Toast.makeText(activity, "Please, confirm your address", Toast.LENGTH_SHORT).show()
                et_new_password.setBackgroundColor(R.color.color_error_bg)
                false
            }
        }
}