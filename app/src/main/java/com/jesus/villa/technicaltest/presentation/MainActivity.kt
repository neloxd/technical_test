package com.jesus.villa.technicaltest.presentation

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.jesus.villa.technicaltest.MainNavGraphDirections
import com.jesus.villa.technicaltest.R
import com.jesus.villa.technicaltest.databinding.ActivityMainBinding
import com.jesus.villa.technicaltest.presentation.BaseFragment.Companion.SHOW_HOME_BUTTON
import com.jesus.villa.technicaltest.presentation.ui.CustomProgressBar
import com.jesus.villa.technicaltest.util.safeNavigate

class MainActivity : AppCompatActivity(), BaseActivity, FragmentChangedListener {

    private lateinit var navController: NavController
    private var _binding: ActivityMainBinding? = null

    private lateinit var progressBar: CustomProgressBar

    // This property is only valid between onCreateView and
    // onDestroyView.
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // This app draws behind the system bars, so we want to handle fitting system windows
        WindowCompat.setDecorFitsSystemWindows(window, false)


        progressBar = findViewById(R.id.customProgressBar)
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return
        navController = host.navController

        intent?.let {
            it.data?.let { uri ->
                if (uri.toString().contains("fpayapp.com://")) {
                    val data = uri.toString()
                    val newData = Uri.parse(data.replace("fpayapp.com://", "https://fpayapp.com/"))
                    intent?.data = newData

                    navController.navigate(newData)
                }
            } ?: it.getStringExtra("notification")?.let { url ->
                intent.data = Uri.parse(url)
                navController.navigate(
                    Uri.parse(url)
                )
            }
        }

        setUpBottomNavigation(navController)

    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun showNavigation(show: Boolean) {
        binding.bottomNav.visibility =  if (show) View.VISIBLE else View.GONE
    }

    override fun onFragmentChanged(type: Int) {

    }

    private fun setUpBottomNavigation(navController: NavController) {
        binding.bottomNav.setupWithNavController(navController = navController)
        //binding.bottomNav.menu.getItem(0).isCheckable = false
        navController.addOnDestinationChangedListener {_, destination, _ ->
            /*if(destination.id == R.id.action_splashFragment_to_EmailFragment) {
                showNavigation(false)
            } else
                showNavigation(true)*/
        }
        binding.bottomNav.setOnNavigationItemSelectedListener { item ->

            if(binding.bottomNav.selectedItemId == item.itemId)
                return@setOnNavigationItemSelectedListener false

            item.isChecked = true

            var direction: NavDirections? = null
            if(R.id.bottom_nav_home == item.itemId) {
                direction = MainNavGraphDirections.actionToDashboard()
            } else if(R.id.bottom_nav_transfer == item.itemId) {
                direction = MainNavGraphDirections.actionToTransference()
            }
            else if(R.id.bottom_nav_bank_transfer == item.itemId) {
                direction = MainNavGraphDirections.actionToBankTransference()
            }
            else if(R.id.bottom_nav_profile == item.itemId) {
                direction = MainNavGraphDirections.actionToProfile()
            }

            direction?.let {
                navController.safeNavigate(it)
            }
            return@setOnNavigationItemSelectedListener true
        }
    }
}