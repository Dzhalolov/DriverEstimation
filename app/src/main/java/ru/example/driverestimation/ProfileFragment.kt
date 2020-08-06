package ru.example.driverestimation

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso


class ProfileFragment : Fragment() {
    private var ivPhoto: AppCompatImageView? = null
    private var etName: EditText? = null
    private var etLogin: EditText? = null
    private var etCar: EditText? = null
    private lateinit var btnEditProfile: Button
    private lateinit var btnChangePassword: Button
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
        val view = inflater.inflate(R.layout.fr_profile, container, false)
        sharedPreferencesHelper = SharedPreferencesHelper(activity!!)
        ivPhoto = view.findViewById(R.id.iv_photo)
        etName = view.findViewById(R.id.et_name)
        etLogin = view.findViewById(R.id.et_login)
        etCar = view.findViewById(R.id.et_car)
        btnEditProfile = view.findViewById(R.id.btn_edit_profile)
        btnChangePassword = view.findViewById(R.id.btn_change_password)
        btnEditProfile.setOnClickListener(switchToEditProfileFragment())
        btnChangePassword.setOnClickListener(switchToChangePasswordFragment())
        return view
    }

    override fun onResume() {
        super.onResume()
        user = sharedPreferencesHelper!!.getUser(USER_ID)
        for (u in sharedPreferencesHelper!!.users) {
            Log.d(TAG, "user: " + user.toString())
        }
        etName!!.setText(user!!.name)
        etLogin!!.setText(user!!.login)
        etCar!!.setText(user!!.car)
        Picasso.with(activity)
            .load(Uri.parse(user!!.uri))
            .placeholder(R.mipmap.ic_profile_photo)
            .transform(CircleTransform(150))
            .into(ivPhoto)
    }

    private fun switchToEditProfileFragment(): View.OnClickListener {
        return View.OnClickListener {
            if (fragmentManager != null) {
                fragmentManager!!.beginTransaction()
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
                    .replace(R.id.container, ChangePasswordFragment.newInstance())
                    .addToBackStack(ChangePasswordFragment::class.java.name)
                    .commit()
            }
        }
    }
}