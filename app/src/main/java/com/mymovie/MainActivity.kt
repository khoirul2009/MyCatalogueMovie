package com.mymovie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mymovie.databinding.ActivityMainBinding
import com.mymovie.home.HomeFragment
import com.mymovie.search.SearchFragment
import com.mymovie.settings.SettingsFragment


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_activity_main, HomeFragment()).commit()
        }
        binding.bottomNavigation.setOnItemSelectedListener {
            val fragment: Fragment? = when(it.itemId) {
                R.id.home_page -> {
                    HomeFragment()
                }
                R.id.search_page -> {
                    SearchFragment()
                }
                R.id.bookmarks_page -> {
                    instantiateFragment("com.mymovie.bookmarks.BookmarksFragment")
                }
                R.id.setting_page -> {
                    SettingsFragment()
                }
                else -> null

            }
            if(fragment != null) {
                supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_activity_main, fragment).commit()
                true
            } else {
                true
            }
        }

        supportActionBar?.hide()
    }

    private fun instantiateFragment(className: String) : Fragment? {
        return try {
            Class.forName(className).newInstance() as Fragment
        } catch (e: Exception) {
            Toast.makeText(this, "Module Not Installed", Toast.LENGTH_SHORT).show()
            null
        }
    }
    
}