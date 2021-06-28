package `in`.vikram.simulationtask.data

import `in`.vikram.simulationtask.data.local.AppDBHelper
import `in`.vikram.simulationtask.data.local.AppDatabase
import `in`.vikram.simulationtask.data.local.FakeAppDatabase
import `in`.vikram.simulationtask.data.local.FakeDBHelperTest
import `in`.vikram.simulationtask.data.remote.ApiHelperTest
import android.content.Context
import kotlinx.coroutines.test.TestCoroutineDispatcher

object Injection {

    fun provideDataRepository(context: Context): DataSourceInterface {
        return DataRepository.getInstance(
            TestCoroutineDispatcher(),
            ApiHelperTest(),
            FakeDBHelperTest(FakeAppDatabase(context)))

    }
}