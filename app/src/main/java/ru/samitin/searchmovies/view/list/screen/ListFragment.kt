package ru.samitin.searchmovies.view.list.screen

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.samitin.searchmovies.R
import ru.samitin.searchmovies.databinding.FragmentMainListBinding
import ru.samitin.searchmovies.utils.hide
import ru.samitin.searchmovies.utils.show
import ru.samitin.searchmovies.utils.showSnackBar
import ru.samitin.searchmovies.view.list.adapter.MyItemRecyclerViewAdapter
import ru.samitin.searchmovies.view.details.screen.DetailsFragment
import ru.samitin.searchmovies.state.AppState
import ru.samitin.searchmovies.view.list.viewModel.ListViewModel

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        _binding = FragmentMainListBinding.inflate(inflater,container,false)
        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
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

        var obsrver = Observer<AppState>{renderData(it)}
        viewModel.getLiveData().observe(viewLifecycleOwner,obsrver)
        viewModel.getMovieFromLocalStorage()
    }

    private fun renderData(appState: AppState?) {
        when(appState){
            is AppState.Success ->{
                binding.loadingLayout.hide()
                adapter.setMovies(appState.movies)
            }
            is AppState.Loading ->{
                binding.loadingLayout.show()
            }
            is AppState.Error -> {
                binding.loadingLayout.hide()
                binding.rvList.showSnackBar(R.string.snackBarError,R.string.snackBarReload) {
                    viewModel.getMovieFromLocalStorage()
                }
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
            ListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}