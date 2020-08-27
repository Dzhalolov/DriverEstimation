package ru.example.driverestimation

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.ac_main.*
import ru.example.driverestimation.ui.auth.AuthActivity
import ru.example.driverestimation.ui.auth.AuthFragment
import ru.example.driverestimation.ui.personal_area.ProfileFragment
import ru.example.driverestimation.utils.Auth
import ru.example.driverestimation.utils.showMsg

class MainActivity : AppCompatActivity() {

    private lateinit var userAuth: Auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_main)
        setSupportActionBar(mainToolbar)
        title = ""

        userAuth = Auth(this)

        //get data from auth activity
        val bundle = intent.extras
        val userEmail = bundle?.getString(AuthFragment.USER_CODE)

        if (userEmail != null) {
            //remember user auth
            userAuth.addUser(userEmail)
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

        imageGoBack.setOnClickListener {
            val fragmentManager =
                supportFragmentManager
            if (fragmentManager.backStackEntryCount == 1) {
                finish()
            } else {
                fragmentManager.popBackStack()
            }
        }

        /*btn_logout.setOnClickListener {
            userAuth.deleteUser()
            goToAuthActivity()
        }*/

    }

    private fun goToAuthActivity() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        this.finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.profile_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {
                userAuth.deleteUser()
                goToAuthActivity()
            }
            R.id.action_about -> showMsg(this, "About app")
        }

        return super.onOptionsItemSelected(item)
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