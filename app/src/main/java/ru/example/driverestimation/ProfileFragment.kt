package ru.example.driverestimation

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fr_profile.*


class ProfileFragment : Fragment() {

    private var sharedPreferencesHelper: SharedPreferencesHelper? = null
    private var user: User? = null
    private val TAG = "DEBUG_TAG_PROFILE"

    companion object {
        const val USER_ID: Long = 20202020
        fun newInstance(): ProfileFragment {
            val args = Bundle()
            val fragment = ProfileFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fr_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sharedPreferencesHelper = SharedPreferencesHelper(activity!!)

        btn_edit_profile.setOnClickListener(switchToEditProfileFragment())
        btn_change_password.setOnClickListener(switchToChangePasswordFragment())
    }

    override fun onResume() {
        super.onResume()
        if (sharedPreferencesHelper!!.getUser(USER_ID) != null)
            user = sharedPreferencesHelper!!.getUser(USER_ID)
        else {
            user = User(
                USER_ID, "danil.danil@mail.ru", "Danil", "123",
                "LADA Priora", ""
            )
            sharedPreferencesHelper!!.addUser(user!!)
        }
        for (u in sharedPreferencesHelper!!.users) {
            Log.d(TAG, "user: " + user.toString())
        }
        et_name.setText(user!!.name)
        et_login.setText(user!!.login)
        et_car.setText(user!!.car)
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
                    .replace(R.id.container, EditProfileFragment.newInstance())
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
                    .replace(R.id.container, ChangePasswordFragment.newInstance())
                    .addToBackStack(ChangePasswordFragment::class.java.name)
                    .commit()
            }
        }
    }
}