package ru.samitin.searchmovies.view.ui.home.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.samitin.searchmovies.R
import ru.samitin.searchmovies.databinding.FragmentHomeBinding
import ru.samitin.searchmovies.entities.CardMovie
import ru.samitin.searchmovies.entities.Category
import ru.samitin.searchmovies.state.AppState
import ru.samitin.searchmovies.utils.hide
import ru.samitin.searchmovies.utils.show
import ru.samitin.searchmovies.utils.showSnackBar
import ru.samitin.searchmovies.view.details.screen.DetailsFragment
import ru.samitin.searchmovies.view.list.screen.ListFragment
import ru.samitin.searchmovies.view.list.viewModel.NO_RATING
import ru.samitin.searchmovies.view.ui.home.adapter.VerticalRecyclerViewAdapter
import ru.samitin.searchmovies.view.ui.home.viewModel.HomeViewModel

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }
    private var adapter : VerticalRecyclerViewAdapter? = null
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel : HomeViewModel by lazy {
        ViewModelProvider(this) [HomeViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding=FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initRecyclerView()
        initViewModel()
    }
    private fun initAdapter(){
        adapter =  VerticalRecyclerViewAdapter(context)
        adapter?.setOnHorisontalClickListener(object : VerticalRecyclerViewAdapter.OnHorisontalClickListener{
            override fun onClick(cardMovie: CardMovie) {
                Toast.makeText(context,cardMovie.name, Toast.LENGTH_SHORT).show()
                var bundle = bundleOf(DetailsFragment.ID_MOVIE_DETAILS to cardMovie.id)
                view?.findNavController()?.navigate(R.id.action_navigation_home_to_detailsFragment, bundle)

            }
        })
        adapter?.setOnVerticalClickListener(object :VerticalRecyclerViewAdapter.OnVerticalClickListener{
            override fun onClick(idMovies:Int) {
                Toast.makeText(context,"Category",Toast.LENGTH_SHORT).show()
                var bundle = bundleOf(ListFragment.GENRES_ID to idMovies)
                view?.findNavController()?.navigate(R.id.action_navigation_home_to_listFragment, bundle)
            }
        })
    }
    private fun initRecyclerView(){
        binding.verticalRv.adapter = adapter
        binding.verticalRv.setHasFixedSize(true)
        binding.verticalRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
    }
    private fun initViewModel(){
        viewModel.liveDataToObserve.observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getGenresAndCountries()
    }

    private fun renderData(appState: AppState){
        when(appState){
            is AppState.SuccessGenresAndCountries ->{
                binding.loadingLayout.hide()
                appState.genresAndCountries.genres.let {

                    viewModel.getListMovieFromRemoteStorage(it[0])
                    viewModel.getListMovieFromRemoteStorage(it[1])
                }
            }
            is AppState.Success->{
                binding.loadingLayout.hide()
                adapter?.addCategoryModel(appState.movies)
            }
            is AppState.Loading ->{
                binding.loadingLayout.show()
            }
            is AppState.Error -> {
                binding.loadingLayout.hide()
                binding.verticalRv.showSnackBar(appState.error.message ?:"Ошибка!!!", R.string.snackBarReload) {
                    viewModel.getGenresAndCountries()
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