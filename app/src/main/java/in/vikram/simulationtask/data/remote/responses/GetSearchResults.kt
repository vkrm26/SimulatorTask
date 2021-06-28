package `in`.vikram.simulationtask.data.remote.responses

import `in`.vikram.simulationtask.data.model.PhotosList
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetSearchResults {

    @SerializedName("photos")
    @Expose
    var photosList: PhotosList? = null

    @SerializedName("stat")
    @Expose
    var stat: String? = null
}