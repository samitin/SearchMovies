package ru.samitin.searchmovies.view

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ru.samitin.searchmovies.R
import ru.samitin.searchmovies.databinding.ActivityMainBinding
import ru.samitin.searchmovies.view.list.viewModel.NO_RATING

const val SETTINGS_RATING_KEY="RATING_KEY"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main2)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_favorite, R.id.navigation_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun saveRating(rating:Int){
        getPreferences(Context.MODE_PRIVATE).edit().apply {
            putInt(SETTINGS_RATING_KEY, rating)
            apply()
        }
       // fragment.setRating9(rating)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_screen_menu,menu)
        //val isChecked: Boolean = rating >= 0
       // menu?.get(index = 0)?.isChecked = isChecked
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
