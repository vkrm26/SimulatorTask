package `in`.vikram.simulationtask.data.model

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "photo")
open class Photo() : Parcelable {

    @SerializedName("id")
    @Expose
    @PrimaryKey
    var id: String = ""

    @SerializedName("owner")
    @Expose
    @ColumnInfo
    var owner: String? = null

    @SerializedName("secret")
    @Expose
    @ColumnInfo
    var secret: String? = null

    @SerializedName("server")
    @Expose
    @ColumnInfo
    var server: String? = null

    @SerializedName("farm")
    @Expose
    @ColumnInfo
    var farm: Int? = null

    @SerializedName("title")
    @Expose
    @ColumnInfo
    var title: String? = null

    @SerializedName("ispublic")
    @Expose
    @ColumnInfo
    var ispublic: Int? = null

    @SerializedName("isfriend")
    @Expose
    @ColumnInfo
    var isfriend: Int? = null

    @SerializedName("isfamily")
    @Expose
    @ColumnInfo
    var isfamily: Int? = null

    @ColumnInfo
    var isFav: Boolean? = false

    constructor(parcel: Parcel) : this() {
        id = parcel.readString().toString()
        owner = parcel.readString()
        secret = parcel.readString()
        server = parcel.readString()
        farm = parcel.readValue(Int::class.java.classLoader) as? Int
        title = parcel.readString()
        ispublic = parcel.readValue(Int::class.java.classLoader) as? Int
        isfriend = parcel.readValue(Int::class.java.classLoader) as? Int
        isfamily = parcel.readValue(Int::class.java.classLoader) as? Int
        isFav = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeString(id)
        p0?.writeString(owner)
        p0?.writeString(secret)
        p0?.writeString(server)
        p0?.writeValue(farm)
        p0?.writeString(title)
        p0?.writeValue(ispublic)
        p0?.writeValue(isfriend)
        p0?.writeValue(isfamily)
        p0?.writeValue(isFav)
    }

    companion object CREATOR : Creator<Photo> {

        override fun createFromParcel(parcel: Parcel): Photo {
            return Photo(parcel)
        }

        override fun newArray(size: Int): Array<Photo?> {
            return arrayOfNulls(size)
        }
    }

    constructor(id: String, owner : String, secret: String, server: String, farm : Int, title : String,
        ispublic: Int, isfriend : Int, isfamily: Int, isFav: Boolean ) : this() {
        this.id = id
        this.owner = owner
        this.secret = secret
        this.server = server
        this.farm = farm
        this.title = title
        this.ispublic = ispublic
        this.isfriend = isfriend
        this.isfamily = isfamily
        this.isFav = isFav
    }
}