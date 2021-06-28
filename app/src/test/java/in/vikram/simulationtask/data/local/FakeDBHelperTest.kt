package `in`.vikram.simulationtask.data.local

import `in`.vikram.simulationtask.data.model.Photo

class FakeDBHelperTest(appDatabase: AppDatabase) : DBHelper {

    override suspend fun getAllPhotos(): MutableList<Photo> {
        val list = mutableListOf<Photo>()
        list.add(Photo("123", "owner", "34ndmd", "32432423", 343, "Photo1", 0, 1, 1, true))
        list.add(Photo("234", "owner", "34ndmd", "32432423", 343, "Photo2", 0, 1, 1, false))
        list.add(Photo("236", "owner", "34ndmd", "32432423", 343, "Photo3", 0, 1, 1, false))
        return list
    }

    override suspend fun insertSearchResults(photoList: List<Photo>) {

    }

    override suspend fun clearSearchResults() {

    }

    override suspend fun saveFavourite(photo: Photo) {

    }

    override suspend fun undoFavourite(photo: Photo) {

    }

    override suspend fun getFavouritePhotos(): List<Photo> {
        val list = mutableListOf<Photo>()
        list.add(Photo("123", "owner", "34ndmd", "32432423", 343, "Photo1", 0, 1, 1, true))
        list.add(Photo("234", "owner", "34ndmd", "32432423", 343, "Photo2", 0, 1, 1, true))
        return list
    }
}