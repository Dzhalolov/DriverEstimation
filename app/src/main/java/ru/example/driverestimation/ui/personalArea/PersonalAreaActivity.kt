package ru.example.driverestimation.ui.personalArea

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.ac_personal_area.*
import ru.example.driverestimation.R

class PersonalAreaActivity : AppCompatActivity() {

    companion object {
        val USER_CODE: String = "USER_CODE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_personal_area)

        iv_back.setOnClickListener {
            val fragmentManager =
                supportFragmentManager
            if (fragmentManager.backStackEntryCount == 1) {
                finish()
            } else {
                fragmentManager.popBackStack()
            }
        }

        val bundle = intent.extras
        val userId = bundle?.getLong(USER_CODE)

        if (savedInstanceState == null) {
            userId?.let { ProfileFragment.newInstance(it) }?.let {
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.container,
                        it
                    )
                    .addToBackStack(ProfileFragment::class.java.name)
                    .commit()
            }
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