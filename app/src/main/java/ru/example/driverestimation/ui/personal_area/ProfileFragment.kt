package ru.example.driverestimation.ui.personal_area

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fr_profile.*
import ru.example.driverestimation.R
import ru.example.driverestimation.model.User
import ru.example.driverestimation.utils.Auth
import ru.example.driverestimation.utils.CircleTransform
import ru.example.driverestimation.utils.UserController


class ProfileFragment : Fragment(R.layout.fr_profile) {

    private lateinit var sharedPreferencesHelper: UserController
    private var user: User? = null
    private val TAG = "DEBUG_TAG_PROFILE"

    companion object {
        var userEmail: String? = ""

        /*fun newInstance(userId: Long): ProfileFragment {
            val args = Bundle()
            args.putLong(AuthFragment.USER_CODE, userId)
            val fragment = ProfileFragment()
            fragment.arguments = args
            return fragment
        }*/
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val sharedPreferences =
            activity!!.getSharedPreferences(Auth.USER_EMAIL_KEY, Context.MODE_PRIVATE)
        userEmail = sharedPreferences.getString(Auth.USER_EMAIL_KEY, "")

        sharedPreferencesHelper =
            UserController(activity!!)

        btnEditProfile.setOnClickListener(switchToEditProfileFragment())
        btnChangePassword.setOnClickListener(switchToChangePasswordFragment())
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: $userEmail")

        user = userEmail?.let { sharedPreferencesHelper.getUser(it) }

        Log.d(TAG, "onResume: $user")

        //set data from user obj
        etName.setText(user!!.name)
        etLogin.setText(user!!.email)
        etCar.setText(user!!.car)

        /* set profile photo
        * if user hadn't add photo set default photo */
        Picasso.with(activity)
            .load(Uri.parse(user!!.uri))
            .placeholder(R.mipmap.ic_profile_photo)
            .transform(CircleTransform())
            .fit()
            .into(ivPhoto)
    }

    private fun switchToEditProfileFragment(): View.OnClickListener {
        return View.OnClickListener {
            if (fragmentManager != null) {
                fragmentManager!!.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .replace(
                        R.id.container,
                        EditProfileFragment()
                    )
                    .addToBackStack(EditProfileFragment::class.java.name)
                    .commit()
            }
        }
    }

    private fun switchToChangePasswordFragment(): View.OnClickListener {
        return View.OnClickListener {
            if (fragmentManager != null) {
                fragmentManager!!.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .replace(
                        R.id.container,
                        ChangePasswordFragment()
                    )
                    .addToBackStack(ChangePasswordFragment::class.java.name)
                    .commit()
            }
        }
    }
}