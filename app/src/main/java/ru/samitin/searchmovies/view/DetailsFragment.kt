package ru.samitin.searchmovies.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.samitin.searchmovies.databinding.FragmentDetailsBinding
import ru.samitin.searchmovies.entities.Movie
import ru.samitin.searchmovies.viewmodel.MainViewModel

class DetailsFragment: Fragment() {

    companion object {
        const val BUNDLE_EXTRA = "BUNDLE_EXTRA"
        fun newInstance(movie: Movie) : DetailsFragment{
            val bundle = Bundle()
            bundle.putParcelable(BUNDLE_EXTRA,movie)
            val detailsFragment = DetailsFragment()
            detailsFragment.arguments = bundle
            return detailsFragment
        }
    }
    private var movie : Movie ?= null
    private var _binding : FragmentDetailsBinding?= null
    private val binding get() = _binding!!
   // private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        movie = arguments?.get(BUNDLE_EXTRA) as Movie
        _binding = FragmentDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        /*viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        val observer = Observer<AppState>{renderData(it)}
        viewModel.getLiveData().observe(viewLifecycleOwner,observer)
        viewModel.getMovieFromLocalStorage()*/
        binding.loadingLayout.visibility = View.GONE
        setData(movie!!)
    }

   /* private fun renderData(appState: AppState) {
        when(appState){
            is AppState.Success ->{
                binding.loadingLayout.visibility = View.GONE
              // setData(appState.movie)

            }
            is AppState.Loading ->{
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error ->{
                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(binding.mainView,"Error",Snackbar.LENGTH_SHORT)
                    .setAction("Reload"){viewModel.getMovieFromLocalStorage()}
                    .show()
            }
            else -> {}
        }
    }*/

    private fun setData(movie: Movie) {
        binding.apply {
            desName.text = movie.name
            //desImage.
            desDate.text = movie.date
            desRating.text = movie.rating.toString()
            desDescription.text = movie.description
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}