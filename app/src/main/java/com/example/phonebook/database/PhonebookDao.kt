package com.example.phonebook.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PhonebookDao {

    @Query("SELECT * FROM PhoneBookEntity ORDER BY first_name")
    fun getPhonebooks(): LiveData<List<PhoneBookEntity>>

    @Query("SELECT * FROM PhoneBookEntity WHERE id LIKE :id")
    fun getPhonebookDetails(id: Int): PhoneBookEntity

    @Insert
    fun insertPhonebook(vararg phoneBook: PhoneBookEntity)

    @Update
    fun updatePhonebook(vararg phoneBook: PhoneBookEntity)

    @Delete
    fun deletePhonebook(phoneBook: PhoneBookEntity)

}