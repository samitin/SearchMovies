package ru.samitin.searchmovies.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.get
import ru.samitin.searchmovies.R
import ru.samitin.searchmovies.view.list.screen.ListFragment
import ru.samitin.searchmovies.view.list.viewModel.NO_RATING

const val SETTINGS_RATING_KEY="RATING_KEY"
@Suppress("UNREACHABLE_CODE")
class MainActivity : AppCompatActivity() {


    private lateinit var fragment: ListFragment
    var rating = NO_RATING
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         rating = getPreferences(Context.MODE_PRIVATE).getInt(SETTINGS_RATING_KEY, NO_RATING)

        fragment = ListFragment.newInstance("1",rating)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commitNow()
        }
    }

    private fun saveRating(rating:Int){
        getPreferences(Context.MODE_PRIVATE).edit().apply {
            putInt(SETTINGS_RATING_KEY, rating)
            apply()
        }
        fragment.setRating9(rating)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_screen_menu,menu)
        val isChecked: Boolean = rating >= 0
        menu?.get(index = 0)?.isChecked = isChecked
        return super.onCreateOptionsMenu(menu)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.menu_rating ->{
                item.isChecked = !item.isChecked
                if (item.isChecked)
                    saveRating(9)
                else
                    saveRating(NO_RATING)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }


    }
}