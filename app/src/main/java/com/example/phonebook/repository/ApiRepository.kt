package com.example.phonebook.repository

import androidx.lifecycle.MutableLiveData
import com.example.phonebook.model.DialCode
import com.example.phonebook.network.ApiInterface
import com.example.phonebook.network.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ApiRepository @Inject constructor(private val api: ApiInterface) {

    private val liveUserResponse: MutableLiveData<Result<List<DialCode>>> = MutableLiveData()
    private val mCompositeDisposable = CompositeDisposable()

    fun loadDialCodes(): MutableLiveData<Result<List<DialCode>>>? {

        mCompositeDisposable.add(

            api.getDialCodes()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .doOnNext { value ->
                    liveUserResponse.value?.data = value

                    liveUserResponse.value =
                        Result.success(
                            value
                        )
                }

                .doOnError { value ->
                    liveUserResponse.value =
                        Result.error("Something went wrong !!")
                }
                .subscribe()
        )
        return liveUserResponse
    }

}