package `in`.vikram.simulationtask.data.remote

import `in`.vikram.simulationtask.custom.Resource
import `in`.vikram.simulationtask.data.remote.responses.GetSearchResults

interface ApiHelper {

    suspend fun getSearchResults(query : String, page : Int): Resource<GetSearchResults>

}