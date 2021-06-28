package `in`.vikram.simulationtask.data.local

import `in`.vikram.simulationtask.data.model.Photo
import androidx.annotation.VisibleForTesting

class AppDBHelper(val appDatabase: AppDatabase) : DBHelper {

    companion object {
        private var INSTANCE: AppDBHelper? = null

        @JvmStatic
        fun getInstance(database: AppDatabase): AppDBHelper {
            if (INSTANCE == null) {
                synchronized(AppDBHelper::javaClass) {
                    INSTANCE =
                        AppDBHelper(database)
                }
            }
            return INSTANCE!!
        }

        @VisibleForTesting
        fun clearInstance() {
            INSTANCE = null
        }
    }

    override suspend fun getAllPhotos(): MutableList<Photo> {
        return appDatabase.photoDao().getPhotos()
    }

    override suspend fun insertSearchResults(photoList: List<Photo>) {
        appDatabase.photoDao().insertAllPhotos(photoList)
    }

    override suspend fun clearSearchResults() {
        appDatabase.photoDao().deleteOldStoredPhotos()
    }

    override suspend fun saveFavourite(photo: Photo) {
        appDatabase.photoDao().update(photo)
    }

    override suspend fun undoFavourite(photo: Photo) {
        appDatabase.photoDao().update(photo)
    }

    override suspend fun getFavouritePhotos(): List<Photo> {
        return appDatabase.photoDao().getFavPhotos()
    }

}