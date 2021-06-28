package `in`.vikram.simulationtask.data.local

import `in`.vikram.simulationtask.const.Const
import `in`.vikram.simulationtask.const.Const.DATABASE_VERSION
import `in`.vikram.simulationtask.data.local.converter.PhotoConverter
import `in`.vikram.simulationtask.data.local.dao.FavouritePhotosDao
import `in`.vikram.simulationtask.data.local.dao.PhotoListDao
import `in`.vikram.simulationtask.data.local.entity.FavouritePhoto
import `in`.vikram.simulationtask.data.model.Photo
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Photo::class, FavouritePhoto::class], version = DATABASE_VERSION, exportSchema = true)
@TypeConverters(PhotoConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun photoDao(): PhotoListDao

    abstract fun favouritePhotoDao(): FavouritePhotosDao

    companion object {

        private var INSTANCE: AppDatabase?=null

        private val lock=Any()

        fun getInstance(context: Context): AppDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE=Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, Const.DB_NAME
                    ).build()
                }
                return INSTANCE!!
            }
        }
    }


}