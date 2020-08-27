package ru.example.driverestimation.ui.personal_area

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fr_edit_profile.*
import ru.example.driverestimation.R
import ru.example.driverestimation.model.User
import ru.example.driverestimation.utils.CircleTransform
import ru.example.driverestimation.utils.UserController

class EditProfileFragment : Fragment(R.layout.fr_edit_profile) {

    private var user: User? = null
    private lateinit var userController: UserController
    private val CODE_GET_PHOTO = 101
    private val TAG = "DEBUG_TAG"
    private lateinit var availableCarsAdapter: ArrayAdapter<String>


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        userController =
            UserController(activity!!)
        user = ProfileFragment.userEmail?.let { userController.getUser(it) }

        etProfileName.setText(user!!.name)
        etProfileCar.setText(user!!.car)
        setImage(Uri.parse(user!!.uri))

        /* on profile photo click listener
        * show dialog window with 2 variants:
        * - change photo (select from gallery)
        * - delete photo
        * */
        ivProfilePhoto.setOnClickListener {
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

        /*
        * one more way to change profile photo
        * */
        tvChangePhoto.setOnClickListener {
            selectPhotoFromGallery()
        }
        btnSaveProfileChanges.setOnClickListener(onBtnSaveClickListener())

        etProfileCar.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                etProfileCar.showDropDown()
            }
        }

        /*
        * set adapter for dropdown list on editText car
        * */
        availableCarsAdapter = ArrayAdapter(
            activity!!,
            android.R.layout.simple_dropdown_item_1line,
            arrayOf("LADA Vesta", "LADA Priora", "LADA Kalina")
        )
        etProfileCar.setAdapter<ArrayAdapter<String>>(availableCarsAdapter)
    }

    /*
    * handling request
    * */
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            when (requestCode) {
                CODE_GET_PHOTO -> {
                    data.data?.let { uri ->
                        launchImageCrop(uri)
                    }
                }
                CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                    val result = CropImage.getActivityResult(data)
                    val uri = result.uri
                    setImage(uri)
                    user!!.uri = uri.toString()
                }
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
            .into(ivProfilePhoto)
    }

    private fun selectPhotoFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png", "image/jpg")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        startActivityForResult(intent, CODE_GET_PHOTO)
    }

    private fun setImage(uri: Uri) {
        Picasso.with(activity)
            .load(uri)
            .placeholder(R.mipmap.ic_profile_photo)
            .transform(CircleTransform())
            .into(ivProfilePhoto)
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
                user!!.name = etProfileName.text.toString()
                user!!.car = etProfileCar.text.toString()
                Log.d(TAG, "onClick: " + user.toString())
                userController.addUser(user!!)
                fragmentManager!!.popBackStack()
            } else
                Toast.makeText(activity, "Error!!!\nFields are empty!", Toast.LENGTH_SHORT).show()
        }
    }


    private val isValidInput: Boolean
        get() = if (TextUtils.isEmpty(etProfileName.text.toString())
            || TextUtils.isEmpty(etProfileCar.text.toString())
        ) {
            Toast.makeText(activity, "Empty fields!", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
}