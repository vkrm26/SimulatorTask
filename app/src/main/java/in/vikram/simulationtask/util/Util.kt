package `in`.vikram.simulationtask.util

import `in`.vikram.simulationtask.BuildConfig
import `in`.vikram.simulationtask.data.model.Photo
import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.notifyObserver() {
    this.value = this.value
}