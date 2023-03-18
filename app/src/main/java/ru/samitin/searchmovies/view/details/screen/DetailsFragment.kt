package ru.samitin.searchmovies.view.details.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import ru.samitin.searchmovies.R
import ru.samitin.searchmovies.databinding.FragmentDetailsBinding
import ru.samitin.searchmovies.entities.Movie
import ru.samitin.searchmovies.entities.MovieStory
import ru.samitin.searchmovies.state.AppState
import ru.samitin.searchmovies.utils.hide
import ru.samitin.searchmovies.utils.show
import ru.samitin.searchmovies.utils.showSnackBar
import ru.samitin.searchmovies.view.details.viewModel.DetailsViewModel
import java.util.*

class DetailsFragment: Fragment() {

    companion object {
        const val ID_MOVIE_DETAILS = "D_MOVIE_DETAILS"

    }
    private var id : Int ?=null
    private var _binding : FragmentDetailsBinding?= null
    private val binding get() = _binding!!
    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        id = arguments?.getInt(ID_MOVIE_DETAILS,0)
        _binding = FragmentDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val observer = Observer<AppState>{renderData(it)}
        viewModel.getLiveData().observe(viewLifecycleOwner,observer)
        viewModel.getDataFromServer(id!!)
        viewModel.getDataStory(id!!)

        binding.btnSendNote.setOnClickListener {
            if (!binding.note.text.toString().equals("")) {
                Toast.makeText(context, binding.note.text.toString(), Toast.LENGTH_SHORT).show()
                viewModel.saveDate(Date())
                viewModel.saveNote(binding.note.text.toString())
                binding.noteText.text = binding.note.text.toString()
            }else
                Toast.makeText(context,"Введите текст",Toast.LENGTH_SHORT).show()
        }

        binding.checkBoxIsLike.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.saveLike(isChecked)
        }

    }

    private fun renderData(appState: AppState) {
        when(appState){
            is AppState.SuccessMovie ->{
                binding.loadingLayout.hide()
                setData(appState.movie)
            }
            is AppState.Story ->{
                setStory(appState.story)
            }
            is AppState.Loading ->{
                binding.loadingLayout.show()
            }
            is AppState.Error ->{
                binding.loadingLayout.hide()

                binding.mainView.showSnackBar(appState.error.message ?: "Ошибка!!!",R.string.snackBarReload){
                    viewModel.getDataFromServer(id!!)
                }
            }
            else -> {}
        }
    }

    private fun setStory(story: MovieStory) {
        story.apply {
            binding.checkBoxIsLike.isChecked = isLike
            if (note!="") {
                binding.nameNote.visibility = View.VISIBLE
                binding.noteText.text = "${viewingTime} \n $note"
            } else
                binding.nameNote.visibility = View.GONE

        }
    }

    private fun setData(movie: Movie) {
        binding.apply {
            desName.text = movie.name
            desImage.load(movie.image)
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