package com.example.phonebook.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.phonebook.R
import com.example.phonebook.model.DialCode
import com.example.phonebook.viewmodel.DialCodesViewModel
import com.example.phonebook.network.Result
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import com.example.phonebook.view.DialCodeRecyclerAdapter
import kotlinx.android.synthetic.main.activity_dial_code.*

class DialCodeActivity : AppCompatActivity() {

    private lateinit var dialCode: List<DialCode>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dial_code)

        getCountryDialCodes()
        searchFilter()
    }

    private fun getCountryDialCodes() {

        val viewModel =
            ViewModelProviders.of(this@DialCodeActivity).get(DialCodesViewModel::class.java)
        viewModel.fetchLocations()?.observe(this, Observer<Result<List<DialCode>>>
        { resource ->

            if (resource != null) {
                dialCode = resource.data!!
                when (resource.status) {
                    Result.Status.SUCCESS -> {
                        Toast.makeText(this, "Country Dial Codes...", Toast.LENGTH_SHORT).show()
                        displayCountryDialCodes(resource.data as ArrayList<DialCode>)
                    }
                    Result.Status.ERROR -> {
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                    }
                    Result.Status.LOADING -> {
                        Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun displayCountryDialCodes(dialCodes: ArrayList<DialCode>) {

        val dialCodeRecyclerAdapter = DialCodeRecyclerAdapter(
            this,
            dialCodes,
            object : DialCodeRecyclerAdapter.ClickListener {
                override fun onClick(position: Int) {
                    val code = dialCodes[position]

                    val data: Intent = Intent()
                    data.data = Uri.parse(code.callingCodes[0])
                    setResult(RESULT_OK, data)
                    finish()
                }
            })
        rv_dial_codes.adapter = dialCodeRecyclerAdapter
        rv_dial_codes.layoutManager = LinearLayoutManager(this)
        dialCodeRecyclerAdapter.notifyDataSetChanged()
    }

    private fun searchFilter() {

        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                // task HERE
                val search: MutableList<DialCode> = ArrayList()
                for (items in dialCode) {
                    if (items.name.contains(query) || items.callingCodes[0].contains(query)) {
                        search.add(items)
                    }
                }
                displayCountryDialCodes(search as ArrayList<DialCode>)
                return false
            }
        })
    }
}