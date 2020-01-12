package com.example.phonebook.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.phonebook.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_phone_book_details.*

class PhoneBookDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_book_details)

        val fname = intent.getStringExtra("fname")
        val lname = intent.getStringExtra("lname")
        val address = intent.getStringExtra("address")
        val email = intent.getStringExtra("email")
        val mobile = intent.getStringExtra("mobile")
        val countryCode = intent.getStringExtra("country_code")
        val photo = intent.getStringExtra("photo")

        txtName.setText(fname)
        txtLastName.setText(lname)
        txtAddress.setText(address)
        txtEmail.setText(email)
        txtCountryCode.setText(countryCode)
        txtMob.setText(mobile)
        Picasso.get().load(photo)
            .placeholder(R.drawable.user_pic)
            .into(iv_phone_book_photo)

    }
}