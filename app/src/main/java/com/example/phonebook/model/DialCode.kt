package com.example.phonebook.model

import com.google.gson.annotations.SerializedName

data class DialCode(

    @SerializedName("callingCodes")
    val callingCodes: List<String>,
    @SerializedName("name")
    val name: String
)