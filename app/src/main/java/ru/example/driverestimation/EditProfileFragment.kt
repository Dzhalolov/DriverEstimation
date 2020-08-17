package ru.example.driverestimation

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fr_edit_profile.*

class EditProfileFragment : Fragment() {

    private var user: User? = null
    private var sharedPreferencesHelper: SharedPreferencesHelper? = null
    private val CODE_GET_PHOTO = 101
    private val TAG: String = "DEBUG_TAG"
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
        return inflater.inflate(R.layout.fr_edit_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sharedPreferencesHelper = SharedPreferencesHelper(activity!!)
        user = sharedPreferencesHelper!!.getUser(ProfileFragment.USER_ID)

        et_profile_name.setText(user!!.name)
        et_profile_car.setText(user!!.car)
        Picasso.with(activity)
            .load(Uri.parse(user!!.uri))
            .placeholder(R.mipmap.ic_profile_photo)
            .transform(CircleTransform())
            .fit()
            .into(iv_profile_photo)

        iv_profile_photo.setOnClickListener {
            val builder = AlertDialog.Builder(activity!!)
                .setTitle("What do you want to do?")
                .setPositiveButton("Change photo") { _: DialogInterface, _: Int
                    ->
                    selectPhotoFromGallery()
                }
                .setNegativeButton("Delete photo") { _: DialogInterface, _: Int
                    ->
                    deletePhoto()
                }
            builder.show()
        }
        tv_change_photo.setOnClickListener {
            selectPhotoFromGallery()
        }
        btn_save_profile_changes.setOnClickListener(onBtnSaveClickListener())

        et_profile_car.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                et_profile_car.showDropDown()
            }
        }
        availableCarsAdapter = ArrayAdapter(
            activity!!,
            android.R.layout.simple_dropdown_item_1line,
            arrayOf("LADA Vesta", "LADA Priora", "LADA Kalina")
        )
        et_profile_car.setAdapter<ArrayAdapter<String>>(availableCarsAdapter)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == CODE_GET_PHOTO) {
                data.data?.let { uri ->
                    launchImageCrop(uri)
                }
            }
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                val result = CropImage.getActivityResult(data)
                val uri = result.uri
                setImage(uri)
                user!!.uri = uri.toString()
            }
        } else {
            Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show()
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun deletePhoto() {
        user!!.uri = ""
        Picasso.with(activity)
            .load(Uri.parse(user!!.uri))
            .placeholder(R.mipmap.ic_profile_photo)
            .fit()
            .into(iv_profile_photo)
    }

    private fun selectPhotoFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png", "image/jpg")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivityForResult(intent, CODE_GET_PHOTO)
    }

    private fun setImage(uri: Uri) {
        Picasso.with(activity)
            .load(uri)
            .placeholder(R.mipmap.ic_profile_photo)
            .transform(CircleTransform())
            .into(iv_profile_photo)
    }

    private fun launchImageCrop(uri: Uri) {
        context?.let {
            CropImage.activity(uri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1080, 1080)
                .setCropShape(CropImageView.CropShape.OVAL)
                .start(it, this)
        }
    }

    private fun onBtnSaveClickListener(): View.OnClickListener {
        return View.OnClickListener {
            if (isValidInput) {
                user!!.name = et_profile_name.text.toString()
                user!!.car = et_profile_car.text.toString()
                Log.d(TAG, "onClick: " + user.toString())
                sharedPreferencesHelper!!.addUser(user!!)
                fragmentManager!!.popBackStack()
            } else
                Toast.makeText(activity, "Error!!!\nFields are empty!", Toast.LENGTH_SHORT).show()
        }
    }


    private val isValidInput: Boolean
        get() = if (TextUtils.isEmpty(et_profile_name.text.toString())
            || TextUtils.isEmpty(et_profile_car.text.toString())
        ) {
            Toast.makeText(activity, "Empty fields!", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
}