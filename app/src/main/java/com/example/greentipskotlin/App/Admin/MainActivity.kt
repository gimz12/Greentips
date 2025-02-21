package com.example.greentipskotlin.App.Admin

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.greentipskotlin.App.Admin.Activity.CoconutInsert
import com.example.greentipskotlin.App.Admin.Activity.EmployeeInsert
import com.example.greentipskotlin.App.Admin.Activity.EmployeePositionInsert
import com.example.greentipskotlin.App.Admin.Activity.EstateInsert
import com.example.greentipskotlin.App.Admin.Activity.HarvestInfomationInsert
import com.example.greentipskotlin.App.Admin.Activity.UserProfileManagement
import com.example.greentipskotlin.App.Model.HarvestInfo
import com.example.greentipskotlin.App.User_Login
import com.example.greentipskotlin.R
import com.google.android.material.navigation.NavigationView

private lateinit var drawerLayout: DrawerLayout
private lateinit var toggle: ActionBarDrawerToggle

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Drawer
        drawerLayout = findViewById(R.id.drawer_layout)

        // Initialize navigationView and set listener
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        // Initialize ActionBar and Drawer Toggle
        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.open_nav,
            R.string.close_nav
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Set visible the toggleDrawer button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        // Load default fragment (do not add to back stack)
        if (savedInstanceState == null) {
            replaceFragment(dashboardFragment(), addToBackStack = false)
            navigationView.setCheckedItem(R.id.homeFragment)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        when (id) {
            R.id.profile -> {
                val profileIntent = Intent(this, UserProfileManagement::class.java)
                startActivity(profileIntent)
            }
            R.id.notifications -> {
                val notificationIntent = Intent(this, HarvestInfomationInsert::class.java)
                startActivity(notificationIntent)
            }
        }

        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.homeFragment -> replaceFragment(dashboardFragment())
            R.id.empfragment -> replaceFragment(emp_mngFragment())
            R.id.est_mngFragmnet -> replaceFragment(est_mngFragment())
            R.id.buyer_mngFragment -> replaceFragment(buyer_mngFragment())
            R.id.sup_mngFragment -> replaceFragment(supplier_mngFragment())
            R.id.coconut_infoFragment -> replaceFragment(coconut_infoFragment())
            R.id.intercrops_infoFragment -> replaceFragment(intercrops_infoFragment())
            R.id.harvest_infoFragmnet -> replaceFragment(hvst_infoFragment())
            R.id.res_mngFragmenet -> replaceFragment(resource_mngFragment())
            R.id.fert_mngFragment -> replaceFragment(fert_mngFragment())
            R.id.log_out -> {
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, User_Login::class.java))
                finish()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = true) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
