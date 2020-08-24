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
import ru.example.driverestimation.MainActivity
import ru.example.driverestimation.R
import ru.example.driverestimation.model.User
import ru.example.driverestimation.utils.CircleTransform
import ru.example.driverestimation.utils.SharedPreferencesHelper


class ProfileFragment : Fragment(R.layout.fr_profile) {

    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private var user: User? = null
    private val TAG = "DEBUG_TAG_PROFILE"

    companion object {
        var userId: Long = 0

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
            activity!!.getSharedPreferences(MainActivity.USER_ID_KEY, Context.MODE_PRIVATE)
        userId = sharedPreferences.getLong(MainActivity.USER_ID_KEY, 0)

        sharedPreferencesHelper =
            SharedPreferencesHelper(activity!!)

        btn_edit_profile.setOnClickListener(switchToEditProfileFragment())
        btn_change_password.setOnClickListener(switchToChangePasswordFragment())
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: $userId")

        user = sharedPreferencesHelper.getUser(userId)

        Log.d(TAG, "onResume: $user")

        //set data from user obj
        et_name.setText(user!!.name)
        et_login.setText(user!!.email)
        et_car.setText(user!!.car)

        /* set profile photo
        * if user hadn't add photo set default photo */
        Picasso.with(activity)
            .load(Uri.parse(user!!.uri))
            .placeholder(R.mipmap.ic_profile_photo)
            .transform(CircleTransform())
            .fit()
            .into(iv_photo)
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