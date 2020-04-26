package com.psycach_ktl

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.psycach_ktl.databinding.ActivityMainBinding
import com.psycach_ktl.enums.ActivityResultCodes
import com.psycach_ktl.managers.AuthorizationManager
import com.psycach_ktl.viewmodels.AuthViewModel
import com.psycach_ktl.viewmodels.LoaderViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private val loaderViewModel: LoaderViewModel by viewModels { LoaderViewModel.Factory(true) }
    private val authViewModel: AuthViewModel by viewModels { AuthViewModel.Factory(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        initLoader()
        initAuth()
        initNavigation()
    }

    private fun initLoader() {
        binding.loaderViewModel = loaderViewModel
    }

    private fun initAuth() {
        authViewModel.authenticationState.observe(this, Observer {
            updateDrawer()
            loaderViewModel.stop()
        })

        binding.authViewModel = authViewModel
    }

    private fun initNavigation() {
        val drawerLayout = binding.drawerLayout
        navController = this.findNavController(R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener { controller, destination, _ ->
            when(destination.id) {
                controller.graph.startDestination -> drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                else -> drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED )
            }
        }
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navigationView, navController)
        binding.navigationView.setNavigationItemSelectedListener { item ->
            binding.drawerLayout.closeDrawers()
            when (item.itemId) {
                R.id.sign_in_button -> {
                    loaderViewModel.start()
                    startActivityForResult(authViewModel.signInUser(), ActivityResultCodes.SIGN_IN.ordinal)
                }
                R.id.sign_out_button -> {
                    authViewModel.signOutUser()
                }
                else -> navController.navigate(item.itemId)
            }
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, binding.drawerLayout)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ActivityResultCodes.SIGN_IN.ordinal) {
            authViewModel.authenticateWithGoogle(data)
        }
    }

    private fun updateDrawer() {
        binding.apply {
            val menu = navigationView.menu
            menu.findItem(R.id.sign_out_button).isVisible = AuthorizationManager.isAuthenticated()
            menu.findItem(R.id.history).isVisible = AuthorizationManager.isAuthenticated()
            menu.findItem(R.id.upgrade_account).isVisible = AuthorizationManager.isAuthenticated() && !AuthorizationManager.isPsychologist()
            menu.findItem(R.id.sign_in_button).isVisible = !AuthorizationManager.isAuthenticated()
        }
    }
}
