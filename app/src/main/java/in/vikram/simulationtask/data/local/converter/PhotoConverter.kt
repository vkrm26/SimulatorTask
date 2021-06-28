package `in`.vikram.simulationtask.data.local.converter

import `in`.vikram.simulationtask.data.model.Photo
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PhotoConverter {

    @TypeConverter
    fun fromString(value: String): List<Photo> {
        val listType = object : TypeToken<List<Photo>>() {

        }.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromContentPhotoList(photoList : List<Photo>): String {
        val gson = Gson()
        val json = gson.toJson(photoList)
        return json
    }

}