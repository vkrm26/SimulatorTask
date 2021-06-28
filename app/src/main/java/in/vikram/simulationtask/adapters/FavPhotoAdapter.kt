package `in`.vikram.simulationtask.adapters

import `in`.vikram.simulationtask.data.model.Photo
import `in`.vikram.simulationtask.databinding.FavPhotoItemBinding
import `in`.vikram.simulationtask.viewholders.FavPhotoVH
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class FavPhotoAdapter(private val mAlbumList: MutableList<Photo>, private val onPhotoClicked: (Photo) -> Unit) : RecyclerView.Adapter<FavPhotoVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavPhotoVH {
        val albumBinding = FavPhotoItemBinding.inflate(LayoutInflater.from(parent.context))
        return FavPhotoVH(albumBinding)
    }

    override fun onBindViewHolder(holder: FavPhotoVH, position: Int) {
        holder.bind(mAlbumList[position])
        holder.itemView.setOnClickListener {
            onPhotoClicked(mAlbumList[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return mAlbumList.size
    }

    fun clearItems() {
        mAlbumList.clear()
    }

    fun addItems(albumList: List<Photo>) {
        this.mAlbumList.clear();
        this.mAlbumList.addAll(albumList);
    }


}