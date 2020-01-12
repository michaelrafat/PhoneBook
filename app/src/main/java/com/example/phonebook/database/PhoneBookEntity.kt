package com.example.phonebook.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PhoneBookEntity (

    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "photo")
    var photo: String,

    @ColumnInfo(name = "first_name")
    var firstName: String,

    @ColumnInfo(name = "last_name")
    var lastName: String,

    @ColumnInfo(name = "dial_code")
    var dialCode: String,

    @ColumnInfo(name = "phone_number")
    var phoneNumber: String,

    @ColumnInfo(name = "email")
    var email: String,

    @ColumnInfo(name = "address")
    var address: String,

    @ColumnInfo(name = "latitude")
    var latitude: Long,

    @ColumnInfo(name = "longitude")
    var longitude: Long,

    @ColumnInfo(name = "create_date")
    var createDate: Long,

    @ColumnInfo(name = "update_date")
    var updateDate: Long

) {
    constructor() : this(

        0, "", "", "", "", "", "", "", -1, -1, -1, -1
    )
}