package `in`.vikram.simulationtask.viewholders

import `in`.vikram.simulationtask.R
import `in`.vikram.simulationtask.data.model.Photo
import `in`.vikram.simulationtask.databinding.ItemPhotoBinding
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener

class PhotoViewHolder(private val itemPhotoBinding: ItemPhotoBinding) : RecyclerView.ViewHolder(itemPhotoBinding.root) {

    val img_like = itemPhotoBinding.imgLike

    fun bind(photo: Photo) {
        itemPhotoBinding.txtTitle.text = photo.title
        itemPhotoBinding.imgLike.isSelected = photo.isFav == true

        Glide.with(itemPhotoBinding.imgPhoto)
            .load(itemPhotoBinding.imgPhoto.context.getString(R.string.image_url, photo.farm, photo.server, photo.id, photo.secret))
            .into(itemPhotoBinding.imgPhoto)

    }

}
