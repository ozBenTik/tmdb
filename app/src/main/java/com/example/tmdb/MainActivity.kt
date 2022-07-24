package com.example.tmdb

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.ui_login.LoginFragment
import com.example.ui_login.LoginResult
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.oz.tmdb.R
import com.oz.tmdb.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        navHostFragment =
            supportFragmentManager.findFragmentById(binding.navHostContainer.id) as NavHostFragment

        navHostFragment.navController.let { navController ->

            auth = FirebaseAuth.getInstance()
            setupWithNavController(binding.bottomNavigation, navController)


            lifecycleScope.launchWhenCreated {
                navController.currentBackStackEntryFlow.collectLatest {
                    it.savedStateHandle.get<Boolean>(LoginFragment.LOGIN_SUCCESSFUL)?.let { success ->
                        if (success) {
                            binding.bottomNavigation.visibility = View.VISIBLE
                        } else {
                            Snackbar.make(binding.root, "Sign in failed", Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
            }


            auth.addAuthStateListener {
                if (it.currentUser == null) {
                    binding.bottomNavigation.visibility = View.GONE
                    Timber.i("No User")
                    navController
                        .navigate(
                            "tmdb://auth.com/loginpage",
                        )
                }
            }
        }
    }
}