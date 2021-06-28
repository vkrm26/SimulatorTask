package `in`.vikram.simulationtask.search

import `in`.vikram.simulationtask.R
import `in`.vikram.simulationtask.data.model.Photo
import `in`.vikram.simulationtask.databinding.FragmentFullPhotoBinding
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide

class FullPhotoFragment : Fragment() {

    private var binding : FragmentFullPhotoBinding?= null
    private var photo : Photo ? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFullPhotoBinding.inflate(layoutInflater)

        photo = arguments?.getParcelable<Photo>("photo")

        val navController = findNavController()
        binding?.toolbar?.setupWithNavController(navController)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding?.let {

            ViewCompat.setTransitionName(binding!!.imgFullSize, "full_image")

            Glide.with(binding!!.imgFullSize)
                .load(binding!!.imgFullSize.context.getString(R.string.image_url, photo?.farm, photo?.server, photo?.id, photo?.secret))
                .into(binding!!.imgFullSize)
        }


    }

}