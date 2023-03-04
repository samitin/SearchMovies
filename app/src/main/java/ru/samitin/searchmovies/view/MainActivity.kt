package ru.samitin.searchmovies.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.samitin.searchmovies.R
import ru.samitin.searchmovies.view.details.screen.DetailsFragment
import ru.samitin.searchmovies.view.list.screen.ListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ListFragment.newInstance("1"))
                .commitNow()
        }
    }
}