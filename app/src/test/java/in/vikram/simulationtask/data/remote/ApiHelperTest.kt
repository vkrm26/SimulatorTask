package `in`.vikram.simulationtask.data.remote

import `in`.vikram.simulationtask.custom.Resource
import `in`.vikram.simulationtask.data.remote.responses.GetSearchResults
import retrofit2.Response

class ApiHelperTest() : ApiHelper {


    override suspend fun getSearchResults(query: String, page: Int): Resource<GetSearchResults> {
        return Resource.success(GetSearchResults())
    }
}