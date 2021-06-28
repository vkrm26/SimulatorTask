package `in`.vikram.simulationtask.custom

import `in`.vikram.simulationtask.data.model.Photo

interface ItemClickListener {

     fun onPhotoClick(photo: Photo)

     fun onLikeClick(photo: Photo, isLiked : Boolean)

}