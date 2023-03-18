package ru.samitin.searchmovies.view.ui.favorites.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.samitin.searchmovies.R
import ru.samitin.searchmovies.databinding.FragmentFavoritesBinding
import ru.samitin.searchmovies.state.AppState
import ru.samitin.searchmovies.utils.hide
import ru.samitin.searchmovies.utils.show
import ru.samitin.searchmovies.utils.showSnackBar
import ru.samitin.searchmovies.view.details.screen.DetailsFragment
import ru.samitin.searchmovies.view.list.viewModel.NO_RATING
import ru.samitin.searchmovies.view.ui.favorites.adapter.FavoritesListAdapter
import ru.samitin.searchmovies.view.ui.favorites.viewmodel.FavoritesViewModel


class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val viewModel :FavoritesViewModel by lazy {
        ViewModelProvider(this)[FavoritesViewModel::class.java]
    }
    val adapter:FavoritesListAdapter = FavoritesListAdapter {
        var bundle = bundleOf(DetailsFragment.ID_MOVIE_DETAILS to it.id)
        view?.findNavController()?.navigate(R.id.action_navigation_favorite_to_detailsFragment, bundle)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.liveDataToObserve.observe(viewLifecycleOwner){renderData(it)}
        binding.rvList.layoutManager = LinearLayoutManager(context)
        binding.rvList.adapter = adapter
        viewModel.getIsLikeMovies()


    }
    private fun renderData(appState: AppState?) {
        when(appState){
            is AppState.SuccessFavorites ->{
                binding.loadingLayout.hide()
                adapter.setMovies(appState.listCardMovie)
            }
            is AppState.Loading ->{
                binding.loadingLayout.show()
            }
            is AppState.Error -> {
                binding.loadingLayout.hide()
                binding.rvList.showSnackBar(appState.error.message ?:"Ошибка!!!", R.string.snackBarReload) {
                    viewModel.getIsLikeMovies()
                }
            }
            else -> {}
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}