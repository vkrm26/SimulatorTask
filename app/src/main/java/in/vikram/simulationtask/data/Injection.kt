package `in`.vikram.simulationtask.data

import `in`.vikram.simulationtask.data.local.AppDBHelper
import `in`.vikram.simulationtask.data.local.AppDatabase
import `in`.vikram.simulationtask.data.remote.ApiService
import `in`.vikram.simulationtask.data.remote.AppApiHelper
import android.content.Context
import kotlinx.coroutines.Dispatchers

object Injection {

    fun provideDataRepository(context: Context): DataSourceInterface {
        return DataRepository.getInstance(
            Dispatchers.IO,
            AppApiHelper.getInstance(ApiService.create()),
            AppDBHelper.getInstance(AppDatabase.getInstance(context)))

    }
}