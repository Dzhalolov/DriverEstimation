package ru.example.driverestimation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso


class EditProfileFragment : Fragment() {
    private var etName: EditText? = null
    private var etCar: AutoCompleteTextView? = null
    private var ivPhoto: AppCompatImageView? = null
    private var btnSave: Button? = null
    private var user: User? = null
    private var sharedPreferencesHelper: SharedPreferencesHelper? = null
    private val CODE_GET_PHOTO = 123
    private val TAG = "DEBUG_TAG"
    private var availableCarsAdapter: ArrayAdapter<String>? = null

    companion object {
        fun newInstance(): EditProfileFragment {
            val args = Bundle()
            val fragment = EditProfileFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fr_edit_profile, container, false)
        ivPhoto = view.findViewById(R.id.iv_profile_photo)
        etName = view.findViewById(R.id.et_profile_name)
        etCar = view.findViewById(R.id.et_profile_car)
        btnSave = view.findViewById(R.id.btn_save_profile_changes)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sharedPreferencesHelper = SharedPreferencesHelper(activity!!)
        user = sharedPreferencesHelper!!.getUser(ProfileFragment.USER_ID)
        etName!!.setText(user!!.name)
        etCar!!.setText( user!!.car)
        Picasso.with(activity)
            .load(Uri.parse(user!!.uri))
            .placeholder(R.mipmap.ic_profile_photo)
            .transform(CircleTransform(150))
            .into(ivPhoto)
        ivPhoto!!.setOnClickListener { selectPhotoFromGallery() }
        btnSave!!.setOnClickListener(OnBtnSaveClickListener())
        etCar!!.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                etCar!!.showDropDown()
            }
        }
        availableCarsAdapter = ArrayAdapter(
            activity!!,
            android.R.layout.simple_dropdown_item_1line,
            arrayOf("LADA Vesta", "LADA Priora", "LADA Kalina")
        )
        etCar!!.setAdapter<ArrayAdapter<String>>(availableCarsAdapter)
    }

    private fun selectPhotoFromGallery() {
        val openGallery = Intent()
        openGallery.type = "image/*"
        openGallery.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(openGallery, CODE_GET_PHOTO)
    }

    private fun OnBtnSaveClickListener(): View.OnClickListener {
        return View.OnClickListener {
            if (isValidInput) {
                user!!.name = etName!!.text.toString()
                user!!.car = etCar!!.text.toString()
                Log.d(TAG, "onClick: " + user.toString())
                sharedPreferencesHelper!!.addUser(user!!)
                fragmentManager!!.popBackStack()
            } else Toast.makeText(
                activity,
                "Error!!!\nFields are empty!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (requestCode == CODE_GET_PHOTO && resultCode == Activity.RESULT_OK && data != null
        ) {
            val uri = data.data
            Picasso.with(activity)
                .load(uri)
                .placeholder(R.mipmap.ic_profile_photo)
                .transform(CircleTransform(150))
                .into(ivPhoto)
            user!!.uri = uri.toString()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private val isValidInput: Boolean
        private get() = if (TextUtils.isEmpty(etName!!.text.toString())
            || TextUtils.isEmpty(etCar!!.text.toString())
        ) {
            Toast.makeText(activity, "Empty fields!", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
}