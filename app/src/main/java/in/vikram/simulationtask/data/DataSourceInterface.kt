package `in`.vikram.simulationtask.data

import `in`.vikram.simulationtask.custom.Resource
import `in`.vikram.simulationtask.data.model.Photo
import `in`.vikram.simulationtask.data.model.PhotosList

interface DataSourceInterface {

    suspend fun getCachedData(): Resource<MutableList<Photo>?>

    suspend fun getSearchResults(query : String, page : Int) : Resource<MutableList<Photo>?>

    suspend fun saveFavourite(photo: Photo) : Resource<Photo>

    suspend fun undoFavourite(photo: Photo) : Resource<Photo>

    suspend fun getFavourites() : Resource<List<Photo>>

}