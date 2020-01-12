package com.example.phonebook.viewmodel

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.phonebook.database.PhonebookDatabase
import com.example.phonebook.database.PhoneBookEntity
import com.example.phonebook.repository.PhonebookRepository
import kotlinx.coroutines.launch

class PhoneBookViewModel(application: Application) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: PhonebookRepository
    // LiveData gives us updated wordPhoneBooks when they change.
    val allPhoneBooks: LiveData<List<PhoneBookEntity>>

    init {
        // Gets reference to PhoneBookDao from PhoneBookDatabase to construct
        // the correct PhoneBookRepository.
        val phoneBookDao = PhonebookDatabase.getDatabase(application).phoneBookDao()
        repository = PhonebookRepository(phoneBookDao)
        allPhoneBooks = repository.allPhoneBooks
    }

    /**
     * The implementation of insert() in the database is completely hidden from the UI.
     * Room ensures that you're not doing any long running operations on
     * the main thread, blocking the UI, so we don't need to handle changing Dispatchers.
     * ViewModels have a coroutine scope based on their lifecycle called
     * viewModelScope which we can use here.
     */
    fun insert(phoneBook: PhoneBookEntity) = viewModelScope.launch{
        InsertTask(phoneBook, repository).execute()
    }

    // An AsyncTask to prevent blocking the UI with a Database query
    private class InsertTask(
        var phoneBookEntity: PhoneBookEntity,
        var repository: PhonebookRepository
    ) : AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean {
            repository.insertPhoneBook(phoneBookEntity)
            return true
        }
    }
}