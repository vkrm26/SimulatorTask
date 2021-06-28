package `in`.vikram.simulationtask.search

import `in`.vikram.simulationtask.R
import `in`.vikram.simulationtask.adapters.FavPhotoAdapter
import `in`.vikram.simulationtask.custom.SpaceItemDecoration
import `in`.vikram.simulationtask.custom.Status.DUMMY
import `in`.vikram.simulationtask.custom.Status.ERROR
import `in`.vikram.simulationtask.custom.Status.LOADING
import `in`.vikram.simulationtask.custom.Status.SUCCESS
import `in`.vikram.simulationtask.data.model.Photo
import `in`.vikram.simulationtask.databinding.FragmentFavPhotosBinding
import `in`.vikram.simulationtask.viewmodel.SearchViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import java.util.ArrayList

class FavPhotosFragment : Fragment() {

    private var binding : FragmentFavPhotosBinding?= null
    private var viewModel : SearchViewModel?= null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentFavPhotosBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        val navController = findNavController()
        binding?.toolbar?.setupWithNavController(navController)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gridLayoutManager = GridLayoutManager(this.context, 3)
        binding?.rvFavPics?.layoutManager = gridLayoutManager

        // adapter
        val favPhotoAdapter = FavPhotoAdapter(ArrayList<Photo>()) { photo ->
            val bundle = bundleOf("photo" to photo)
            findNavController().navigate(R.id.action_fav_photo_fragment_to_full_photo_fragment, bundle)

        }

        viewModel?.getFavouritePhotos()

        binding?.rvFavPics?.adapter = favPhotoAdapter

        // item decoration in recycler
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.dimen_space)
        binding?.rvFavPics?.addItemDecoration(SpaceItemDecoration(spacingInPixels))


        viewModel?.favouritePhotos?.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                LOADING -> {
                    binding?.progressBar?.visibility = View.VISIBLE
                    binding?.rvFavPics?.visibility = View.GONE
                    binding?.empty?.visibility = View.GONE
                }
                ERROR -> {
                    binding?.progressBar?.visibility = View.GONE
                    binding?.rvFavPics?.visibility = View.GONE
                    binding?.empty?.text = getString(R.string.error)
                    binding?.empty?.visibility = View.VISIBLE
                }
                SUCCESS -> {

                    it?.data?.let { it1 -> favPhotoAdapter.addItems(it1) }
                    binding?.progressBar?.visibility = View.GONE
                    binding?.rvFavPics?.visibility = View.VISIBLE
                    binding?.empty?.visibility = View.GONE

                }
                DUMMY -> {
                    binding?.rvFavPics?.visibility = View.GONE
                }
            }
        })
    }


}