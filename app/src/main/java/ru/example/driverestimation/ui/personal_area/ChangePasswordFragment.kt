package ru.example.driverestimation.ui.personal_area

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fr_change_password.*
import ru.example.driverestimation.R
import ru.example.driverestimation.model.User
import ru.example.driverestimation.utils.UserController

class ChangePasswordFragment : Fragment(R.layout.fr_change_password) {

    private lateinit var userController: UserController
    private var user: User? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        userController = UserController(activity!!)
        user = userController.getUser(ProfileFragment.userId)

        btn_submit_password_change.setOnClickListener {
            if (isValidPassword) {
                user!!.password = et_new_password.text.toString()
                userController.addUser(user!!)
                fragmentManager!!.popBackStack()
            }
        }
    }

    @get:SuppressLint("ResourceAsColor")
    private val isValidPassword: Boolean
        get() {
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