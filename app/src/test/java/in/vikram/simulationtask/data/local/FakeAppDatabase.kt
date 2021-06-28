package `in`.vikram.simulationtask.data.local

import `in`.vikram.simulationtask.data.local.dao.FavouritePhotosDao
import `in`.vikram.simulationtask.data.local.dao.PhotoListDao
import android.content.Context
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.sqlite.db.SupportSQLiteOpenHelper
import org.mockito.*

class FakeAppDatabase(context: Context) : AppDatabase() {

    override fun photoDao(): PhotoListDao {
        return Mockito.mock(PhotoListDao::class.java)
    }

    override fun favouritePhotoDao(): FavouritePhotosDao {
        return Mockito.mock(FavouritePhotosDao::class.java)
    }

    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
        return Mockito.mock(SupportSQLiteOpenHelper::class.java)
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        return Mockito.mock(InvalidationTracker::class.java)
    }

    override fun clearAllTables() {
    }
}