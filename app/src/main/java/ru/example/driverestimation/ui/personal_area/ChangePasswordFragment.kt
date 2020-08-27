package ru.example.driverestimation.ui.personal_area

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fr_change_password.*
import ru.example.driverestimation.R
import ru.example.driverestimation.model.User
import ru.example.driverestimation.utils.UserController
import ru.example.driverestimation.utils.showMsg

class ChangePasswordFragment : Fragment(R.layout.fr_change_password) {

    private lateinit var userController: UserController
    private var user: User? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        userController = UserController(activity!!)
        user = ProfileFragment.userEmail?.let { userController.getUser(it) }

        btnSubmitPasswordChange.setOnClickListener {
            if (isValidPassword()) {
                user!!.password = etNewPassword.text.toString()
                userController.addUser(user!!)
                fragmentManager!!.popBackStack()
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun isValidPassword(): Boolean {
        val currPass = etCurrPassword.text.toString()
        val newPass = etNewPassword.text.toString()
        val newPassAgain = etNewPasswordAgain.text.toString()
        return when {
            TextUtils.isEmpty(currPass) || TextUtils.isEmpty(newPass) || TextUtils.isEmpty(
                newPassAgain
            ) -> {
                showMsg(activity!!, "Please, fill all fields")
                false
            }
            currPass != user!!.password -> {
                etCurrPassword.setBackgroundColor(R.color.color_error_bg)
                showMsg(activity!!, "Wrong current password!")
                false
            }
            currPass == newPass -> {
                showMsg(activity!!, "New password equals your old password")
                false
            }
            else -> {
                if (newPass == newPassAgain) {
                    return true
                }
                showMsg(activity!!, "Please, confirm your address")
                etNewPassword.setBackgroundColor(R.color.color_error_bg)
                false
            }
        }
    }


}