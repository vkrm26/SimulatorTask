package `in`.vikram.simulationtask.viewmodel

import `in`.vikram.simulationtask.custom.Resource
import `in`.vikram.simulationtask.custom.Resource.Companion
import `in`.vikram.simulationtask.custom.Status
import `in`.vikram.simulationtask.custom.Status.ERROR
import `in`.vikram.simulationtask.custom.Status.LOADING
import `in`.vikram.simulationtask.custom.Status.SUCCESS
import `in`.vikram.simulationtask.data.DataSourceInterface
import `in`.vikram.simulationtask.data.Injection
import `in`.vikram.simulationtask.data.model.Photo
import `in`.vikram.simulationtask.data.model.PhotosList
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel : AndroidViewModel {

    private var dataRepository : DataSourceInterface

    private var searchJob: Job? = null
    private var textQuery = ""

    private val dummyObject = PhotosList()
    private var page = 0

    private val _favouritePhotos = MutableLiveData<Resource<List<Photo>>>()
    val favouritePhotos : LiveData<Resource<List<Photo>>> = _favouritePhotos

    private val _searchResults = MutableLiveData<Resource<MutableList<Photo>?>>()
    val searchResults: LiveData<Resource<MutableList<Photo>?>> = _searchResults

    private val _isEmpty = MutableLiveData(false)
    val isEmpty: LiveData<Boolean> = _isEmpty

    constructor(application: Application) : super(application) {
        dataRepository = Injection.provideDataRepository(application.applicationContext)
        getCachedData()
    }


    fun onSearchQueryChanged(query: String) {
        val newQuery = query.trim().takeIf { it.length >= 2 } ?: ""
        if (textQuery != newQuery) {
            textQuery = newQuery
            page = 0
            _searchResults.value = Resource.loading()
            executeSearch()
        }
    }

    fun getNextPage() {
        if (textQuery.isEmpty()) {
            return
        }
        page++
        executeSearch()
    }

    private fun executeSearch() {
        // Cancel any in-flight searches
        searchJob?.cancel()

        if (textQuery.isEmpty()) {
            clearSearchResults()
            return
        }


        searchJob = viewModelScope.launch {
            // The user could be typing or toggling filters rapidly. Giving the search job
            // a slight delay and cancelling it on each call to this method effectively debounces.
            delay(200)

            val searchResult = dataRepository.getSearchResults(textQuery, page)
            processSearchResult(searchResult)
        }
    }

    private fun clearSearchResults() {
        _searchResults.value = Resource.success(ArrayList())
        // Explicitly set false to not show the "No results" state
        _isEmpty.value = false
        page = 0
    }

    private fun getCachedData() {
        viewModelScope.launch {
            val searchResult = dataRepository.getCachedData()
            processSearchResult(searchResult)
        }
    }

    private fun processSearchResult(searchResult: Resource<MutableList<Photo>?>) {
        if (searchResult.status == Status.LOADING) {
            return // avoids UI flickering
        }

        if (page == 0 && searchResult.status == ERROR) {
            _searchResults.value = searchResult
            return
        }

        if (searchResult.status == SUCCESS) {
            if (page > 0) {
                searchResult.data?.let { _searchResults.value?.data?.addAll(it) }
                _searchResults.notifyObserver()
            } else _searchResults.value = searchResult

            _isEmpty.value = _searchResults.value?.data == null || _searchResults.value?.data!!.isEmpty()
        }

    }


    fun getFavouritePhotos() {

        if (_favouritePhotos?.value?.status == LOADING) {
            return // requests already going on
        }

        viewModelScope.launch {
            _favouritePhotos.value = Resource.loading()
            val favouritePhotos = dataRepository.getFavourites()
            _favouritePhotos.value = favouritePhotos
        }
    }

    fun saveFavouritePhoto(photo: Photo) {
        viewModelScope.launch {
            val resource = dataRepository.saveFavourite(photo)
        }
    }

    fun undoFavouritePhoto(photo: Photo) {
        viewModelScope.launch {
            val resource = dataRepository.undoFavourite(photo)
        }
    }

    fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }

}