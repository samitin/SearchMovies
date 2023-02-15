package ru.samitin.searchmovies.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import ru.samitin.searchmovies.R
import ru.samitin.searchmovies.databinding.FragmentMainListBinding
import ru.samitin.searchmovies.entities.Movie
import ru.samitin.searchmovies.viewmodel.AppState
import ru.samitin.searchmovies.viewmodel.MainViewModel

/**
 * A fragment representing a list of Items.
 */
class MainFragment : Fragment() {

    private var _binding :FragmentMainListBinding ?= null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter :MyItemRecyclerViewAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        _binding = FragmentMainListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MyItemRecyclerViewAdapter() {movie ->
                val manager = activity?.supportFragmentManager
                        if (manager != null) {
                            manager.beginTransaction()
                                .add(R.id.container, DetailsFragment.newInstance(movie))
                                .addToBackStack("")
                                .commitAllowingStateLoss()
                        }
        }

        binding.rvList.layoutManager = GridLayoutManager(context,3)
        binding.rvList.adapter = adapter

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        var obsrver = Observer<AppState>{renderData(it)}
        viewModel.getLiveData().observe(viewLifecycleOwner,obsrver)
        viewModel.getMovieFromLocalStorage()
    }

    private fun renderData(appState: AppState?) {
        when(appState){
            is AppState.Success ->{
                binding.loadingLayout.visibility = View.GONE
                adapter.setMovies(appState.movies)
            }
            is AppState.Loading ->{
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error ->{
                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(binding.rvList,"Error", Snackbar.LENGTH_SHORT)
                    .setAction("Reload"){viewModel.getMovieFromLocalStorage()}
                    .show()
            }
            else -> {}
        }

    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}