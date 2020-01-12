package com.example.phonebook.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.phonebook.model.DialCode
import com.example.phonebook.network.Result
import com.example.phonebook.network.ApiInterface
import com.example.phonebook.repository.ApiRepository
import javax.inject.Inject

class DialCodesViewModel(application: Application) : AndroidViewModel(application) {

    private var mService = ApiRepository(ApiInterface.create())

    fun fetchLocations(): MutableLiveData<Result<List<DialCode>>>? {
        return mService.loadDialCodes()
    }

    @Inject
    fun init(repository: ApiRepository) {
        this.mService = repository
    }

}