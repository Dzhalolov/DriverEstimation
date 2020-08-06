package ru.example.driverestimation

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class ChangePasswordFragment : Fragment() {
    private var etCurrPassword: EditText? = null
    private var etNewPassword: EditText? = null
    private var etNewPasswordAgain: EditText? = null
    private var btnSubmitChange: Button? = null
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
        val view = inflater.inflate(R.layout.fr_change_password, container, false)
        etCurrPassword = view.findViewById(R.id.et_curr_password)
        etNewPassword = view.findViewById(R.id.et_new_password)
        etNewPasswordAgain = view.findViewById(R.id.et_new_password_again)
        btnSubmitChange = view.findViewById(R.id.btn_submit_password_change)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sharedPreferencesHelper = SharedPreferencesHelper(activity!!)
        user = sharedPreferencesHelper!!.getUser(ProfileFragment.USER_ID)
        btnSubmitChange!!.setOnClickListener {
            if (isValidPassword) {
                user!!.password = etNewPassword!!.text.toString()
                sharedPreferencesHelper!!.addUser(user!!)
                fragmentManager!!.popBackStack()
            }
        }
    }

    @get:SuppressLint("ResourceAsColor")
    private val isValidPassword: Boolean
        private get() {
            val currPass = etCurrPassword!!.text.toString()
            val newPass = etNewPassword!!.text.toString()
            val newPassAgain = etNewPasswordAgain!!.text.toString()
            return if (TextUtils.isEmpty(currPass) || TextUtils.isEmpty(newPass) || TextUtils.isEmpty(
                    newPassAgain
                )
            ) {
                Toast.makeText(activity, "Please, fill all fields", Toast.LENGTH_SHORT).show()
                false
            } else if (currPass != user!!.password) {
                etCurrPassword!!.setBackgroundColor(R.color.color_error_bg)
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
                etNewPasswordAgain!!.setBackgroundColor(R.color.color_error_bg)
                false
            }
        }
}