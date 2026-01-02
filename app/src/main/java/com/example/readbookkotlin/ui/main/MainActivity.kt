package com.example.readbookkotlin.ui.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.readbookkotlin.R
import com.example.readbookkotlin.data.preferences.SharePrefConstants
import com.example.readbookkotlin.databinding.ActivityMainBinding
import com.example.readbookkotlin.domain.model.Person

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        val selectedId = savedInstanceState?.getInt(SharePrefConstants.KEY_STATE) ?: R.id.bottom_home
        binding.bottomNavigation.selectedItemId = selectedId

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }

        binding.bottomNavigation.setOnItemSelectedListener { item->
            if (item.itemId == R.id.bottom_home) {
                replaceFragment(HomeFragment())
                true
            } else if (item.itemId == R.id.bottom_search) {
                replaceFragment(SearchFragment())
                true
            } else if (item.itemId == R.id.bottom_Lib) {
                replaceFragment(LibFragment())
                true
            } else if (item.itemId == R.id.bottom_person) {
                replaceFragment(PersonFragment())
                true
            }

            val fragment = when(item.itemId) {
                R.id.bottom_home->HomeFragment()
                R.id.bottom_search->SearchFragment()
                R.id.bottom_Lib->LibFragment()
                R.id.bottom_person->PersonFragment()
                else->null
            }
            fragment?.let {
                replaceFragment(it)
                true
            } ?:false
        }
    }

    fun replaceFragment(fragment:Fragment) {
        val fragmentTransaction:FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }
}