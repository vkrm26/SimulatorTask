package `in`.vikram.simulationtask.viewholders

import `in`.vikram.simulationtask.R
import `in`.vikram.simulationtask.data.model.Photo
import `in`.vikram.simulationtask.databinding.FavPhotoItemBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FavPhotoVH(private val itemBinding: FavPhotoItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(photo : Photo) {
        Glide.with(itemBinding.imgPhoto)
            .load(itemBinding.imgPhoto.context.getString(R.string.image_url, photo.farm, photo.server, photo.id, photo.secret))
            .into(itemBinding.imgPhoto)
    }

}