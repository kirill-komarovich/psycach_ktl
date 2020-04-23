package com.psycach_ktl

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.psycach_ktl.builders.DialogBuilder
import com.psycach_ktl.databinding.ActivityMainBinding
import com.psycach_ktl.enums.ActivityResultCodes
import com.psycach_ktl.enums.AuthenticationState
import com.psycach_ktl.viewmodels.AuthViewModel
import com.psycach_ktl.viewmodels.LoaderViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private val loaderViewModel: LoaderViewModel by viewModels { LoaderViewModel.Factory(true) }
    private val authViewModel: AuthViewModel by viewModels { AuthViewModel.Factory(application) }
    private lateinit var dialogBuilder: DialogBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dialogBuilder = DialogBuilder(this)

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
            updateUI(it)
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
            when (item.itemId) {
                R.id.sign_in_button -> {
                    binding.drawerLayout.closeDrawers()
                    loaderViewModel.start()
                    startActivityForResult(authViewModel.signInUser(), ActivityResultCodes.SIGN_IN.ordinal)
                }
                R.id.sign_out_button -> {
                    binding.drawerLayout.closeDrawers()
                    authViewModel.signOutUser()
                }
                R.id.upgrade_account_button -> buildDialog()
                else -> navController.navigate(item.itemId)
            }
            true
        }
    }

    private fun buildDialog() {
        dialogBuilder.buildUpgradeAccountDialog { dialog, which ->
            Toast.makeText(this, "accepted", Toast.LENGTH_SHORT).show()

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

    private fun updateUI(authenticationState: AuthenticationState) {
        when(authenticationState) {
            AuthenticationState.AUTHENTICATED -> updateDrawer(true)
            AuthenticationState.UNAUTHENTICATED -> updateDrawer(false)
        }
    }

    private fun updateDrawer(authenticated: Boolean) {
        binding.apply {
            val menu = navigationView.menu
            menu.findItem(R.id.sign_out_button).isVisible = authenticated
            menu.findItem(R.id.history).isVisible = authenticated
            menu.findItem(R.id.upgrade_account_button).isVisible = authenticated
            menu.findItem(R.id.sign_in_button).isVisible = !authenticated
        }
    }
}
