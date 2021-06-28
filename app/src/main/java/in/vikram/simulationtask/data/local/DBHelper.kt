package `in`.vikram.simulationtask.data.local

import `in`.vikram.simulationtask.data.model.Photo

interface DBHelper {

    suspend fun getAllPhotos() : MutableList<Photo>

    suspend fun insertSearchResults(photoList: List<Photo>)

    suspend fun clearSearchResults()

    suspend fun saveFavourite(photo : Photo)

    suspend fun undoFavourite(photo : Photo)

    suspend fun getFavouritePhotos() : List<Photo>

}