package ru.example.driverestimation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.ac_personal_area.*
import ru.example.driverestimation.ui.auth.AuthActivity
import ru.example.driverestimation.ui.auth.AuthFragment
import ru.example.driverestimation.ui.personal_area.ProfileFragment

class MainActivity : AppCompatActivity() {

    companion object {
        val USER_ID_KEY: String = "USER_ID_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_personal_area)

        val sharedPreferences = this.getSharedPreferences(USER_ID_KEY, Context.MODE_PRIVATE)

        val bundle = intent.extras
        val userId = bundle?.getLong(AuthFragment.USER_CODE)

        if (userId != null) {
            sharedPreferences.edit().putLong(USER_ID_KEY, userId).apply()
        }

        if (sharedPreferences.getLong(USER_ID_KEY, 0L) != 0L) {
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
            sharedPreferences.edit().putLong(USER_ID_KEY, 0).apply()
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            this.finish()
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
            sharedPreferences.edit().putLong(USER_ID_KEY, 0).apply()
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            this.finish()
        }

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