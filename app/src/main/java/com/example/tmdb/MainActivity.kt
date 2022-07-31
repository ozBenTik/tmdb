package com.example.tmdb

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.core.data.user.UserRemoteDataSource
import com.google.firebase.auth.FirebaseAuth
import com.oz.tmdb.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var firebaseAuthStateUserDataSource: UserRemoteDataSource

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

            lifecycleScope.launchWhenStarted {
                firebaseAuthStateUserDataSource.getBasicUserInfo().collectLatest { result ->
                    val isSignedIn = result?.isSignedIn() ?: false
                    if (!isSignedIn) {
                        binding.bottomNavigation.visibility = View.GONE
                        navController.navigate(com.example.ui_login.R.id.login_nav_graph)
                    } else {
                        navController.popBackStack(
                            com.example.ui_login.R.id.login_nav_graph,
                            false
                        )
                        binding.bottomNavigation.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}