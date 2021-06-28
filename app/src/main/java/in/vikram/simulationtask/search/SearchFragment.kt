package `in`.vikram.simulationtask.search

import `in`.vikram.simulationtask.R
import `in`.vikram.simulationtask.R.string
import `in`.vikram.simulationtask.adapters.PhotoAdapter
import `in`.vikram.simulationtask.const.Const.Error
import `in`.vikram.simulationtask.custom.InfiniteScrollListener
import `in`.vikram.simulationtask.custom.SpaceItemDecoration
import `in`.vikram.simulationtask.custom.Status.DUMMY
import `in`.vikram.simulationtask.custom.Status.ERROR
import `in`.vikram.simulationtask.custom.Status.LOADING
import `in`.vikram.simulationtask.custom.Status.SUCCESS
import `in`.vikram.simulationtask.data.model.Photo
import `in`.vikram.simulationtask.databinding.FragmentSearchBinding
import `in`.vikram.simulationtask.viewmodel.SearchViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.imageview.ShapeableImageView
import java.util.ArrayList

class SearchFragment : Fragment() {

    private var binding : FragmentSearchBinding?= null
    private var viewModel : SearchViewModel ?= null
    private var scrollListener : InfiniteScrollListener ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        // layout Manager
        val gridLayoutManager = GridLayoutManager(this.context, 2)
        binding?.recyclerView?.layoutManager = gridLayoutManager

        // adapter
        val photoAdapter = PhotoAdapter(
            ArrayList<Photo>(),
            {
                photo, view ->
                val bundle = bundleOf("photo" to photo)
                findNavController().navigate(R.id.action_search_fragment_to_full_photo_fragment, bundle)
            },

            {
                photo, _isLiked ->
                if (_isLiked) {
                    viewModel?.saveFavouritePhoto(photo)
                }
                else viewModel?.undoFavouritePhoto(photo)
                photo.isFav = _isLiked
            })

        binding?.recyclerView?.adapter = photoAdapter

        // item decoration in recycler
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.dimen_space)
        binding?.recyclerView?.addItemDecoration(SpaceItemDecoration(spacingInPixels))

        // for infinite scroll in recycler
        scrollListener = InfiniteScrollListener({ viewModel?.getNextPage() }, gridLayoutManager)
        binding?.recyclerView?.addOnScrollListener(scrollListener!!)


        viewModel?.searchResults?.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                LOADING -> {
                    photoAdapter.clearItems()
                    photoAdapter.notifyDataSetChanged()
                    scrollListener?.reset()
                    binding?.placeholder?.visibility = View.VISIBLE
                    binding?.placeholder?.setAnimation(R.raw.searching_data)
                    binding?.placeholder?.playAnimation()
                    binding?.recyclerView?.visibility = View.GONE
                }
                ERROR -> {
                    binding?.recyclerView?.visibility = View.GONE
                    binding?.placeholder?.visibility = View.VISIBLE

                    when (it.message) {
                        Error.NO_CACHED_DATA -> {
                            return@Observer
                        }
                        Error.NO_INTERNET -> {
                            binding?.placeholder?.setAnimation(R.raw.network_error)
                        }
                        else -> {
                            binding?.placeholder?.setAnimation(R.raw.sad_search)
                        }
                    }

                    binding?.placeholder?.playAnimation()
                }
                SUCCESS -> {
                    it?.data?.let { it1 -> photoAdapter.addItems(it1) }
                    binding?.placeholder?.visibility = View.GONE
                    binding?.recyclerView?.visibility = View.VISIBLE
                }
                DUMMY -> {
                    photoAdapter.clearItems()
                    photoAdapter.notifyDataSetChanged()
                    scrollListener?.reset()
                    binding?.recyclerView?.visibility = View.GONE
                }
            }
        })


        viewModel?.isEmpty?.observe(viewLifecycleOwner, Observer {

            binding?.placeholder?.setAnimation(R.raw.sad_search)
            binding?.placeholder?.playAnimation()
            binding?.placeholder?.visibility = if (it) View.VISIBLE else View.GONE

        })


        viewModel?.getFavouritePhotos()
        viewModel?.favouritePhotos?.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                SUCCESS -> {

                    it.data?.let {
                        binding?.cardFavouritePhoto?.visibility = if (it.isNotEmpty()) View.VISIBLE else View.GONE
                        binding?.favourite?.playAnimation()
                    }
                }

            }
        })

        binding?.cardFavouritePhoto?.setOnClickListener {
            findNavController().navigate(R.id.action_search_fragment_to_fav_photo_fragment)
        }


        binding?.searchView?.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    dismissKeyboard(this@apply)
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    viewModel?.onSearchQueryChanged(newText)
                    return true
                }
            })

            // Set focus on the SearchView and open the keyboard
            setOnQueryTextFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    showKeyboard(view.findFocus())
                }
            }
            requestFocus()
        }
    }

    override fun onPause() {
        binding?.let { dismissKeyboard(it.searchView) }
        super.onPause()
    }

    private fun showKeyboard(view: View) {
        ViewCompat.getWindowInsetsController(view)?.show(WindowInsetsCompat.Type.ime())
    }

    private fun dismissKeyboard(view: View) {
        ViewCompat.getWindowInsetsController(view)?.hide(WindowInsetsCompat.Type.ime())
    }

    override fun onStop() {
        super.onStop()
        scrollListener?.let { binding?.recyclerView?.removeOnScrollListener(it) }
    }

}