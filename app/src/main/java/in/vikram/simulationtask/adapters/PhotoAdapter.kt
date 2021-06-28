package `in`.vikram.simulationtask.adapters

import `in`.vikram.simulationtask.data.model.Photo
import `in`.vikram.simulationtask.databinding.ItemPhotoBinding
import `in`.vikram.simulationtask.viewholders.PhotoViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


class PhotoAdapter(private val mAlbumList: MutableList<Photo>, private val onPhotoClicked: (Photo, View) -> Unit, private val onLikeClicked: (Photo, Boolean) -> Unit) : RecyclerView.Adapter<PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val albumBinding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context))
        return PhotoViewHolder(albumBinding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(mAlbumList[position])
        holder.img_like.setOnClickListener {

            it.isSelected = !it.isSelected

            onLikeClicked(mAlbumList[holder.adapterPosition], it.isSelected)

            if (holder.img_like.isSelected) {
                //Handle selected state change
            } else {
                //Handle de-select state change
            }
        }
        holder.itemView.setOnClickListener {
            onPhotoClicked(mAlbumList[holder.adapterPosition], holder.itemView)
        }
    }

    override fun getItemCount(): Int {
        return mAlbumList.size
    }

    fun clearItems() {
        mAlbumList.clear()
    }

    fun addItems(albumList: List<Photo>) {
        val photosDiffUtil = DiffUtil.calculateDiff(PhotosDiffUtil(this.mAlbumList, albumList))
        this.mAlbumList.clear();
        this.mAlbumList.addAll(albumList);
        photosDiffUtil.dispatchUpdatesTo(this)
    }

    class PhotosDiffUtil(private val oldList: List<Photo>, private val newList : List<Photo>) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].id == newList[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]

        @Nullable
        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? =
            //you can return particular field for changed item.
            super.getChangePayload(oldItemPosition, newItemPosition)

    }




}
