package com.example.marvel_characters.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.marvel_characters.R
import com.example.marvel_characters.app.localization.Language
import com.example.marvel_characters.ui.base.BaseActivity
import com.example.marvel_characters.ui.character.list.viewModel.CharacterListViewModel
import com.example.marvel_characters.ui.character.list.viewModel.CharacterListViewModelFactory
import com.example.marvel_characters.ui.navigation.NavigationDrawerFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_layout.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    private lateinit var mDrawerToggle: ActionBarDrawerToggle
    private lateinit var navController: NavController

    @Inject
    lateinit var viewModelFactory: CharacterListViewModelFactory

    private val onNavigationItemClicked = View.OnClickListener { view ->
        drawer_layout.closeDrawers()
    }
    override val layoutId: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mViewModel:CharacterListViewModel by viewModels {viewModelFactory}

        if (savedInstanceState == null)
            mViewModel.refreshCharacterList()
    }

    override fun initializeViews() {
        setToolbar(toolbar, R.color.colorPrimary, getString(R.string.app_name), showUpButton = true, withElevation = false)
        navController = Navigation.findNavController(this, R.id.main_nav_container)

    }

    private fun setUpNavDrawerListeners() {
        mDrawerToggle = object : ActionBarDrawerToggle(this, drawer_layout, myToolbar, R.string.app_name, R.string.app_name) {

            override fun onDrawerClosed(drawerView: View) {
            }

            override fun onDrawerOpened(drawerView: View) {
            }
        }

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.nav_fragment_container, NavigationDrawerFragment(onNavigationItemClicked))
                .commit()

        mDrawerToggle.setToolbarNavigationClickListener { navController.popBackStack() }

        drawer_layout.addDrawerListener(mDrawerToggle)
    }

    private fun enableDisableNavDrawer(enable: Boolean) {
        mDrawerToggle.isDrawerIndicatorEnabled = enable
        drawer_layout.setDrawerLockMode(if (enable) DrawerLayout.LOCK_MODE_UNLOCKED else DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        mDrawerToggle.syncState()
    }

    override fun setListeners() {
        setUpNavDrawerListeners()
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.fragmentCharacterList -> enableDisableNavDrawer(true)
                R.id.fragmentCharacterDetails -> enableDisableNavDrawer(false)
            }
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        mDrawerToggle.syncState()
    }


}
