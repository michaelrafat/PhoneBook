package com.example.phonebook.repository

import androidx.lifecycle.LiveData
import com.example.phonebook.database.PhonebookDao
import com.example.phonebook.database.PhoneBookEntity

class PhonebookRepository(private val phoneBook: PhonebookDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allPhoneBooks: LiveData<List<PhoneBookEntity>> = phoneBook.getPhonebooks()

    fun insertPhoneBook(phoneBook: PhoneBookEntity) {
        this.phoneBook.insertPhonebook(phoneBook)
    }
}