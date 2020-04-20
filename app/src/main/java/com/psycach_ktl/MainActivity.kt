package com.psycach_ktl

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.psycach_ktl.databinding.ActivityMainBinding
import com.psycach_ktl.enums.ActivityResultCodes
import com.psycach_ktl.enums.AuthenticationState
import com.psycach_ktl.viewmodels.AuthViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: ActivityMainBinding
    private lateinit var authViewModel: AuthViewModel
    private lateinit var authViewModelFactory: AuthViewModel.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initAuth()
        initNavigation()
    }

    private fun initAuth() {
        authViewModelFactory = AuthViewModel.Factory(application)
        authViewModel = ViewModelProvider(this, authViewModelFactory).get(AuthViewModel::class.java)

        authViewModel.authenticationState.observe(this, Observer {
            updateUI(it)
            updateLoading(false)
        })
    }

    private fun initNavigation() {
        drawerLayout = binding.drawerLayout
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
            when (item.itemId) {
                R.id.sign_in_button -> {
                    updateLoading(true)
                    startActivityForResult(authViewModel.signInUser(), ActivityResultCodes.SIGN_IN.ordinal)
                }
                R.id.sign_out_button -> {
                    updateLoading(true)
                    authViewModel.signOutUser()
                }
            }
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ActivityResultCodes.SIGN_IN.ordinal) {
            authViewModel.authenticateWithGoogle(data)
        }
    }

    private fun updateUI(authenticationState: AuthenticationState) {
        when(authenticationState) {
            AuthenticationState.AUTHENTICATED -> updateDrawer(true)
            AuthenticationState.UNAUTHENTICATED -> updateDrawer(false)
        }
    }

    private fun updateDrawer(authenticated: Boolean) {
        binding.apply {
            navigationView.menu.findItem(R.id.sign_out_button).isVisible = authenticated
            navigationView.menu.findItem(R.id.sign_in_button).isVisible = !authenticated
        }
    }

    private fun updateLoading(loading: Boolean) {
        drawerLayout.closeDrawers()
        binding.apply {
            if (loading) {
                progressCircular.visibility = View.VISIBLE
                fragmentContent.visibility = View.GONE
            } else {
                progressCircular.visibility = View.GONE
                fragmentContent.visibility = View.VISIBLE
            }
        }
    }
}
