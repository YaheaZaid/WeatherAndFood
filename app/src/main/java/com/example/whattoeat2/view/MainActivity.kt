package com.example.whattoeat2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.whattoeat2.R
import com.example.whattoeat2.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mainFragment= MainFragment()
        val eatFragment= FoodFragment()
        val drinkFragment= DrinkFragment()
        setCurrentFragment(mainFragment)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        bottom_nav_bar.setOnItemSelectedListener {
            when (it) {

                R.id.nav_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frag, mainFragment).commit()
                }
                R.id.nav_food -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frag, eatFragment).commit()

                }
                R.id.nav_drink -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frag, drinkFragment).commit()
                    }
            }
        }
    }
    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_frag,fragment)
            commit()

        }
    }




