package com.example.phonebook.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.phonebook.R
import com.example.phonebook.database.PhoneBookEntity
import com.squareup.picasso.Picasso

class PhoneBooksRecyclerAdapter(
    private val context: Context,
    private val phoneBookList: ArrayList<PhoneBookEntity>,
    val listener: ClickListener
) : RecyclerView.Adapter<PhoneBooksRecyclerAdapter.PhoneBookViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    companion object {
        var clickListener: ClickListener? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneBookViewHolder {
        val itemView = inflater.inflate(R.layout.phone_book_item, parent, false)
        return PhoneBookViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PhoneBookViewHolder, position: Int) {

        val phoneBook = phoneBookList[position]
        clickListener = listener

        holder.firstNameTV.text = phoneBook.firstName
        holder.lastNameTV.text = phoneBook.lastName
        val phone = "+" + phoneBook.dialCode + phoneBook.phoneNumber
        holder.phoneTV.text = phone
        holder.addressTV.text = phoneBook.address
        holder.emailTV.text = phoneBook.email

        holder.addressTV.setOnClickListener {
            val gmmIntentUri = Uri.parse("google.streetview:cbll=90,120")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps");
            context.startActivity(mapIntent)
        }

        holder.phoneTV.setOnClickListener {
            val intent = Intent()
            intent.data = Uri.parse("tel:" + phone)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }

        Picasso.get().load(phoneBook.photo)
            .placeholder(R.drawable.user_pic)
            .into(holder.photoIV)

        holder.phoneBookCard.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (clickListener != null)
                    clickListener?.onClick(position)
            }
        })
    }

    override fun getItemCount() = phoneBookList.size

    inner class PhoneBookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val firstNameTV: TextView = itemView.findViewById(R.id.tv_phone_book_first_name)
        val lastNameTV: TextView = itemView.findViewById(R.id.tv_phone_book_last_name)
        val photoIV: ImageView = itemView.findViewById(R.id.iv_phone_book_photo)
        val addressTV: TextView = itemView.findViewById(R.id.tv_phone_book_address)
        val phoneTV: TextView = itemView.findViewById(R.id.tv_phone_book_phone)
        val emailTV: TextView = itemView.findViewById(R.id.tv_phone_email)
        val phoneBookCard: CardView = itemView.findViewById(R.id.cv_phone_book_card)
    }

    interface ClickListener {
        fun onClick(position: Int)
    }
}