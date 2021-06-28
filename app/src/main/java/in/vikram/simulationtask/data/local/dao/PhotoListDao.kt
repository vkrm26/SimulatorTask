package `in`.vikram.simulationtask.data.local.dao

import `in`.vikram.simulationtask.data.model.Photo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PhotoListDao {


    @Query("SELECT * FROM photo")
    suspend fun getPhotos(): MutableList<Photo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPhotos(photosList : List<Photo>)

    @Delete
    suspend fun deletePhotos(photo: Photo)

    @Update
    suspend fun update(photos : Photo)

    @Query("DELETE FROM photo WHERE isFav = 0")
    suspend fun deleteOldStoredPhotos()

    @Query("SELECT * FROM photo WHERE isFav = 1")
    suspend fun getFavPhotos(): List<Photo>
}