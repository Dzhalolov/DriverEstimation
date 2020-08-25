package ru.example.driverestimation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.ac_main.*
import ru.example.driverestimation.ui.auth.AuthActivity
import ru.example.driverestimation.ui.auth.AuthFragment
import ru.example.driverestimation.ui.personal_area.ProfileFragment
import ru.example.driverestimation.utils.Auth

class MainActivity : AppCompatActivity() {

    private lateinit var userAuth: Auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_main)

        userAuth = Auth(this)

        //get data from auth activity
        val bundle = intent.extras
        val userId = bundle?.getLong(AuthFragment.USER_CODE)

        if (userId != null) {
            //remember user auth
            userAuth.addUser(userId)
        }

        if (userAuth.isAuthed()) {
            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.container,
                        ProfileFragment()
                    )
                    .addToBackStack(ProfileFragment::class.java.name)
                    .commit()
            }
        } else {
            goToAuthActivity()
        }

        iv_back.setOnClickListener {
            val fragmentManager =
                supportFragmentManager
            if (fragmentManager.backStackEntryCount == 1) {
                finish()
            } else {
                fragmentManager.popBackStack()
            }
        }

        btn_logout.setOnClickListener {
            userAuth.deleteUser()
            goToAuthActivity()
        }

    }

    private fun goToAuthActivity() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        this.finish()
    }

    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager

        if (fragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            fragmentManager.popBackStack()
        }
    }

}