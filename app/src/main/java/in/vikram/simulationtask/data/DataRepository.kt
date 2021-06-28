package `in`.vikram.simulationtask.data

import `in`.vikram.simulationtask.const.Const.Error.NO_CACHED_DATA
import `in`.vikram.simulationtask.const.Const.Error.NO_INTERNET
import `in`.vikram.simulationtask.const.Const.Error.TRY_AGAIN
import `in`.vikram.simulationtask.const.Const.Error.UNABLE_TO_RESOLVE_HOST
import `in`.vikram.simulationtask.custom.Resource
import `in`.vikram.simulationtask.custom.Status.SUCCESS
import `in`.vikram.simulationtask.data.local.DBHelper
import `in`.vikram.simulationtask.data.model.Photo
import `in`.vikram.simulationtask.data.remote.ApiHelper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DataRepository(private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default, private var apiHelper: ApiHelper, private var dbHelper: DBHelper) : DataSourceInterface {


    companion object {

        private var INSTANCE: DataRepository? = null

        /**
         * Returns the single instance of this class, creating it if necessary.
         */
        @JvmStatic
        fun getInstance(
            defaultDispatcher: CoroutineDispatcher,
            apiHelper: ApiHelper,
            dbHelper: DBHelper
        ): DataRepository {
            return INSTANCE ?: DataRepository(defaultDispatcher, apiHelper, dbHelper)
                .apply { INSTANCE = this }
        }

        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }

    override suspend fun getCachedData() : Resource<MutableList<Photo>?> =
        withContext(defaultDispatcher) {
            val photoList = dbHelper.getAllPhotos()
            if (photoList.isNotEmpty()) {
                 Resource.success(photoList)
            } else {
                 Resource.error(NO_CACHED_DATA)
            }
        }




    override suspend fun getSearchResults(query: String, page: Int) : Resource<MutableList<Photo>?> =
        withContext(defaultDispatcher) {
            val results = apiHelper.getSearchResults(query, page)
            if (results.status == SUCCESS) {
                if (page == 0) dbHelper.clearSearchResults()
                results.data?.photosList?.photo?.let { dbHelper.insertSearchResults(it) }
                Resource.success(results.data?.photosList?.photo)
            } else {
                if (results.message != null) {
                    if (results.message.contains(UNABLE_TO_RESOLVE_HOST, true)) {
                        Resource.error(NO_INTERNET)
                    } else {
                        Resource.error(TRY_AGAIN)
                    }
                } else {
                    Resource.error(TRY_AGAIN)
                }

            }
        }


    override suspend fun saveFavourite(photo: Photo) : Resource<Photo> =
        withContext(defaultDispatcher) {
            dbHelper.saveFavourite(photo)
            Resource.success(photo)
        }


    override suspend fun undoFavourite(photo: Photo) : Resource<Photo> =
        withContext(defaultDispatcher) {
            dbHelper.undoFavourite(photo)
            Resource.success(photo)
        }


    override suspend fun getFavourites() : Resource<List<Photo>> =
        withContext(defaultDispatcher) {
            Resource.success(dbHelper.getFavouritePhotos())
        }

}