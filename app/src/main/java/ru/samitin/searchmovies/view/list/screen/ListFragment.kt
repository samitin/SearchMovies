package ru.samitin.searchmovies.view.list.screen

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import ru.samitin.searchmovies.R
import ru.samitin.searchmovies.databinding.FragmentMainListBinding
import ru.samitin.searchmovies.utils.hide
import ru.samitin.searchmovies.utils.show
import ru.samitin.searchmovies.utils.showSnackBar
import ru.samitin.searchmovies.view.list.adapter.MyItemRecyclerViewAdapter
import ru.samitin.searchmovies.view.details.screen.DetailsFragment
import ru.samitin.searchmovies.state.AppState

import ru.samitin.searchmovies.view.list.viewModel.ListViewModel
import ru.samitin.searchmovies.view.list.viewModel.NO_RATING

/**
 * A fragment representing a list of Items.
 */

class ListFragment : Fragment() {

    private var _binding :FragmentMainListBinding ?= null
    private val binding get() = _binding!!

    private val viewModel : ListViewModel by lazy {
        ViewModelProvider(this)[ListViewModel::class.java]
    }
    private lateinit var adapter : MyItemRecyclerViewAdapter
    private var genres: Int?=null
    private var rating:Int?= NO_RATING

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        _binding = FragmentMainListBinding.inflate(inflater,container,false)
        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        genres = arguments?.getInt(GENRES_ID)
        rating = /*arguments?.getInt(RATING_ID) ?:*/ NO_RATING
        adapter = MyItemRecyclerViewAdapter() {cardMovie ->
            var bundle = bundleOf(DetailsFragment.ID_MOVIE_DETAILS to cardMovie.id)
            view?.findNavController()?.navigate(R.id.action_listFragment_to_detailsFragment, bundle)
        }

        binding.rvList.layoutManager = GridLayoutManager(context,3)
        binding.rvList.adapter = adapter

        var obsrver = Observer<AppState>{renderData(it)}
        viewModel.liveDataToObserve.observe(viewLifecycleOwner,obsrver)

        viewModel.getListMovieFromRemoteStorage(genres!!, rating ?: NO_RATING)


    }

    private fun renderData(appState: AppState?) {
        when(appState){
            is AppState.Success ->{
                binding.loadingLayout.hide()
                adapter.setMovies(appState.movies.listMovie!!)
            }
            is AppState.Loading ->{
                binding.loadingLayout.show()
            }
            is AppState.Error -> {
                binding.loadingLayout.hide()
                binding.rvList.showSnackBar(R.string.snackBarError,R.string.snackBarReload) {

                    viewModel.getListMovieFromRemoteStorage(genres!!,rating ?: NO_RATING)
                }
            }
            else -> {}
        }

    }
    fun setRating9(rating : Int){
        this.rating = rating
        viewModel.getListMovieFromRemoteStorage(genres!!,rating)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val GENRES_ID = "GENRES_ID"
        const val RATING_ID = "RATING_ID"
        // TODO: Customize parameter initialization
    }
}