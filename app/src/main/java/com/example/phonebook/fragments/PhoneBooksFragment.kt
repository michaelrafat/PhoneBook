package com.example.phonebook.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.phonebook.R
import com.example.phonebook.activities.PhoneBookDetailsActivity
import com.example.phonebook.database.PhoneBookEntity
import com.example.phonebook.view.PhoneBooksRecyclerAdapter
import com.example.phonebook.viewmodel.PhoneBookViewModel

class PhoneBooksFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var phoneBookViewModel: PhoneBookViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_phone_books, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rv_phone_book)

        phoneBookViewModel = ViewModelProviders.of(this).get(PhoneBookViewModel::class.java)
        phoneBookViewModel.allPhoneBooks.observe(viewLifecycleOwner, Observer { updateViews(it) })
    }

    private fun updateViews(phoneBookList: List<PhoneBookEntity>) {

        val adapter = context?.let {
            PhoneBooksRecyclerAdapter(
                it, phoneBookList as ArrayList<PhoneBookEntity>
                , object : PhoneBooksRecyclerAdapter.ClickListener {
                    override fun onClick(position: Int) {
                        val intent = Intent(context, PhoneBookDetailsActivity::class.java)
                        intent.putExtra("fname", phoneBookList[position].firstName)
                        intent.putExtra("lname", phoneBookList[position].lastName)
                        intent.putExtra("address", phoneBookList[position].address)
                        intent.putExtra("email", phoneBookList[position].email)
                        intent.putExtra("mobile", phoneBookList[position].phoneNumber)
                        intent.putExtra("country_code", phoneBookList[position].dialCode)
                        intent.putExtra("photo", phoneBookList[position].photo)

                        startActivity(intent)

//                        val gmmIntentUri = Uri.parse("google.streetview:cbll=90,120")
//                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
//
//                        mapIntent.setPackage("com.google.android.apps.maps");
//                        startActivity(mapIntent)
                    }
                })
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        Log.d("Num:", phoneBookList.size.toString())

    }
}