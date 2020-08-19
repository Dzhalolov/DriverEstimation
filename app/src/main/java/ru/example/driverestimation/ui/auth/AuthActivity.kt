package ru.example.driverestimation.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.example.driverestimation.R

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_auth)

        supportFragmentManager.beginTransaction()
            .replace(R.id.auth_container, AuthFragment.newInstance())
            .addToBackStack(AuthFragment::class.java.name)
            .commit()
    }
}