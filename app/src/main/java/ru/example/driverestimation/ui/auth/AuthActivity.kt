package ru.example.driverestimation.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.ac_personal_area.*
import ru.example.driverestimation.R

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_auth)

        iv_back.setOnClickListener {
            val fragmentManager =
                supportFragmentManager
            if (fragmentManager.backStackEntryCount == 1) {
                finish()
            } else {
                fragmentManager.popBackStack()
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.auth_container, AuthFragment())
            .addToBackStack(AuthFragment::class.java.name)
            .commit()
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