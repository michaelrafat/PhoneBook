package com.example.phonebook.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.afollestad.vvalidator.form

import com.example.phonebook.R
import com.example.phonebook.activities.DialCodeActivity
import com.example.phonebook.database.PhoneBookEntity
import com.example.phonebook.viewmodel.PhoneBookViewModel

import kotlinx.android.synthetic.main.phone_book_form.*


class AddPhoneBookFragment : Fragment() {

    lateinit var phoneBookViewModel: PhoneBookViewModel
    private lateinit var phoneBookEntity: PhoneBookEntity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_add_phone_book, container, false)
        phoneBookViewModel = ViewModelProviders.of(this).get(PhoneBookViewModel::class.java)
        phoneBookEntity = PhoneBookEntity()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkRunTimePermission()
        savePhoneBook()
        setCountryDialCode()
    }

    private fun checkRunTimePermission() {

        iv_phone_book_photo.setOnClickListener {
            //check runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (activity?.let { it1 ->
                        ContextCompat.checkSelfPermission(it1, Manifest.permission.READ_CONTACTS)
                    }
                    != PackageManager.PERMISSION_GRANTED) {
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE)
                } else {
                    //permission already granted
                    pickImageFromGallery()
                }
            } else {
                //system OS is < Marshmallow
                pickImageFromGallery()
            }
        }
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission from popup granted
                    pickImageFromGallery()
                } else {
                    //permission from popup denied
                    Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"

        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object {
        //image pick code
        private const val IMAGE_PICK_CODE = 1000
        //Permission code
        private const val PERMISSION_CODE = 1001
        //DIAL COUNTRY code
        private const val DIAL_CODE = 1002
    }

    private fun validateInputs() {

        form {

            input(R.id.txtName) {
                isNotEmpty()
                length().atLeast(3)
                matches("[a-zA-Z]+")
            }
            input(R.id.txtLastName) {
                isNotEmpty()
                length().atLeast(3)
                matches("[a-zA-Z]+")
            }
            input(R.id.txtMob) {
                isNotEmpty()
                length().exactly(10)
            }
            input(R.id.txtCountryCode) {
                isNotEmpty()
            }
            input(R.id.txtEmail) {
                isNotEmpty()
                isEmail()
            }
            input(R.id.txtAddress) {
                isNotEmpty()
            }
            submitWith(R.id.btn_save) { result ->
                // this block is only called if form is valid.
                // do something with a valid form state.

                if (phoneBookEntity.photo.isEmpty()) {
                    Toast.makeText(context, "Select a photo!", Toast.LENGTH_SHORT).show()

                } else {
                    phoneBookEntity.firstName = txtName.text.toString()
                    phoneBookEntity.lastName = txtLastName.text.toString()
                    phoneBookEntity.email = txtEmail.text.toString()
                    phoneBookEntity.address = txtAddress.text.toString()
                    phoneBookEntity.phoneNumber = txtMob.text.toString()
                    phoneBookEntity.dialCode = txtCountryCode.text.toString()

                    phoneBookViewModel.insert(phoneBookEntity)
                    Toast.makeText(context, "PhoneBook Added!", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    private fun savePhoneBook() {

        btn_save.setOnClickListener {
            validateInputs()
        }
    }

    private fun setCountryDialCode() {

        txtCountryCode.setOnClickListener {
            val intent = Intent(context, DialCodeActivity::class.java)
            intent.putExtra("select", 1)
            startActivityForResult(intent, DIAL_CODE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        //handle result of picked image
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            iv_phone_book_photo.setImageURI(data?.data)
            phoneBookEntity.photo = data?.dataString!!
        }
        //Get selected country dial code
        if (resultCode == Activity.RESULT_OK && requestCode == DIAL_CODE) {
            txtCountryCode.setText(data?.data.toString())
        }

    }
}