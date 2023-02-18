package ru.samitin.searchmovies.view.details.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.samitin.searchmovies.R
import ru.samitin.searchmovies.databinding.FragmentDetailsBinding
import ru.samitin.searchmovies.entities.Movie
import ru.samitin.searchmovies.state.AppState
import ru.samitin.searchmovies.utils.hide
import ru.samitin.searchmovies.utils.show
import ru.samitin.searchmovies.utils.showSnackBar
import ru.samitin.searchmovies.view.details.viewModel.DetailsViewModel

class DetailsFragment: Fragment() {

    companion object {
        const val BUNDLE_EXTRA = "BUNDLE_EXTRA"
        fun newInstance(id:String) : DetailsFragment {
            val bundle = Bundle()
            bundle.putString(BUNDLE_EXTRA,id)
            val detailsFragment = DetailsFragment()
            detailsFragment.arguments = bundle
            return detailsFragment
        }
    }
    private var id : String = ""
    private var movie : Movie ?= null
    private var _binding : FragmentDetailsBinding?= null
    private val binding get() = _binding!!
    private lateinit var viewModel: DetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        id = arguments?.getString(BUNDLE_EXTRA,"").toString()
        _binding = FragmentDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]
        val observer = Observer<AppState>{renderData(it)}
        viewModel.getLiveData().observe(viewLifecycleOwner,observer)
        viewModel.getDataFromServer(id)
    }

    private fun renderData(appState: AppState) {
        when(appState){
            is AppState.SuccessMovie ->{
                binding.loadingLayout.hide()
               setData(appState.movie)

            }
            is AppState.Loading ->{
                binding.loadingLayout.show()
            }
            is AppState.Error ->{
                binding.loadingLayout.hide()
                binding.mainView.showSnackBar(R.string.snackBarError,R.string.snackBarReload){
                    viewModel.getDataFromServer(id)
                }
            }
            else -> {}
        }
    }

    private fun setData(movie: Movie) {
        binding.apply {
            desName.text = movie.name
            desImage.setImageResource(R.drawable.the_boss_baby)
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