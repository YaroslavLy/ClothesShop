package com.example.clothesshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.clothesshop.databinding.ActivityMainBinding
import com.example.clothesshop.model.Type
import com.example.clothesshop.ui.category.CategoryFragment
import com.example.clothesshop.ui.login.LoginFragment
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)



        binding.bottomNavigationView.setOnItemSelectedListener {
            var fragent =
                supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
            when (it.itemId) {
                R.id.main_menu -> {
                    fragent.view?.let { it1 ->
                        Navigation.findNavController(it1)
                            .navigate(R.id.categoryFragment)
                    }
                }
                R.id.catalog_menu -> fragent.view?.let { it1 ->
                    Navigation.findNavController(it1)
                        .navigate(R.id.typeFragment)
                }
                R.id.basket_menu -> fragent.view?.let { it1 ->
                    Navigation.findNavController(it1)
                        .navigate(R.id.basketFragment)
                }

//                else -> {
//
//
//                }

            }
            true

        }

    }


}