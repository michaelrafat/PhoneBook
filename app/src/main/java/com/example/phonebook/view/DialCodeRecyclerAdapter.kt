package com.example.phonebook.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.phonebook.R
import com.example.phonebook.model.DialCode

class DialCodeRecyclerAdapter(
    context: Context,
    private val dialCodeList: ArrayList<DialCode>,
    val listener: ClickListener
) : RecyclerView.Adapter<DialCodeRecyclerAdapter.DialCodeViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    companion object {
        var clickListener: ClickListener? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialCodeViewHolder {
        val itemView = inflater.inflate(R.layout.dial_code_item, parent, false)
        return DialCodeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DialCodeViewHolder, position: Int) {

        val phoneBook = dialCodeList[position]
        clickListener = listener

        holder.countryNameTV.text = phoneBook.name
        holder.countryDialCodeTV.text = phoneBook.callingCodes[0]

        holder.countryNameTV.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (clickListener != null)
                    clickListener?.onClick(position)
            }
        })

    }

    override fun getItemCount() = dialCodeList.size

    inner class DialCodeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val countryNameTV: TextView = itemView.findViewById(R.id.tv_country_name)
        val countryDialCodeTV: TextView = itemView.findViewById(R.id.tv_country_dial_code)
    }

    interface ClickListener {
        fun onClick(position: Int)
    }
}