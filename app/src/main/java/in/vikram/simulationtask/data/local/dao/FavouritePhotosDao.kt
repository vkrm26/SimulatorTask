package `in`.vikram.simulationtask.data.local.dao

import `in`.vikram.simulationtask.data.local.entity.FavouritePhoto
import `in`.vikram.simulationtask.data.model.Photo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavouritePhotosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoritePhoto(photo: FavouritePhoto)

    @Query("SELECT * FROM favourite_photo")
    fun getFavoritePhotos() : List<FavouritePhoto>

    @Delete
    suspend fun deleteFavouritePhoto(photo: FavouritePhoto)

}