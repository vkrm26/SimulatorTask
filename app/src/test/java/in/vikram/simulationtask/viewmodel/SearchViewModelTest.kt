package `in`.vikram.simulationtask.viewmodel

import `in`.vikram.simulationtask.data.Injection
import android.app.Application
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import junit.framework.TestCase
import org.junit.*
import org.mockito.*

class SearchViewModelTest : TestCase() {

    val context = ApplicationProvider.getApplicationContext<Context>()

    override fun setUp() {
        super.setUp()
        Mockito.mock(Application::class.java)
        Injection.provideDataRepository(context)
    }

    @Test
    suspend fun test_when_cached_data_available_then_update_livedata_with_cached_data() {

        val dataRepository = Injection.provideDataRepository(context)
//        val searchViewModel = SearchViewModel(ApplicationProvider.getApplicationContext<Context>())

        dataRepository.getCachedData()

    }

    @Test
    fun test_when_query_is_same_then_return() {

    }

    @Test
    fun test_when_query_is_empty_then_get_next_page_should_return() {

    }

    @Test
    fun test_when_query_is_unique_then_fetch_results() {

    }
}

