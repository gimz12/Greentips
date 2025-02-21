package com.example.greentipskotlin.App.FieldManager.Activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.greentipskotlin.App.Admin.Activity.HarvestInfomationInsert
import com.example.greentipskotlin.App.Admin.Activity.UserProfileManagement
import com.example.greentipskotlin.App.Admin.coconut_infoFragment
import com.example.greentipskotlin.App.Admin.dashboardFragment
import com.example.greentipskotlin.App.Admin.fert_mngFragment
import com.example.greentipskotlin.App.Admin.hvst_infoFragment
import com.example.greentipskotlin.App.Admin.intercrops_infoFragment
import com.example.greentipskotlin.App.Admin.resource_mngFragment
import com.example.greentipskotlin.App.Buyer.BuyerCatalogueFragment
import com.example.greentipskotlin.App.CEO.assignTaskFragment
import com.example.greentipskotlin.App.CEO.buyer_Order_HisFragment
import com.example.greentipskotlin.App.CEO.buyer_RegFragment
import com.example.greentipskotlin.App.CEO.ceoDashboardFragment
import com.example.greentipskotlin.App.CEO.sup_Order_HistoryFragment
import com.example.greentipskotlin.App.CEO.supplier_Reg_ReqFragment
import com.example.greentipskotlin.App.CEO.supplier_order_reqFragment
import com.example.greentipskotlin.App.CEO.task_HistoryFragment
import com.example.greentipskotlin.App.FieldManager.CatalogueItemManageFragment
import com.example.greentipskotlin.App.FieldManager.FieldManagerDashboard
import com.example.greentipskotlin.App.FieldManager.FieldManagerManageBuyerOrderFragment
import com.example.greentipskotlin.App.FieldManager.ManageTaskFragment
import com.example.greentipskotlin.App.FieldManager.SupplierOfferManagementFragment
import com.example.greentipskotlin.App.User_Login
import com.example.greentipskotlin.R
import com.google.android.material.navigation.NavigationView

private lateinit var drawerLayout: DrawerLayout
private lateinit var toggle: ActionBarDrawerToggle

class FieldManagerMenu : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_field_manager_menu)

        // Initialize Drawer
        drawerLayout = findViewById(R.id.drawer_layout_FieldManager)

        // Initialize navigationView and set listener
        val navigationView = findViewById<NavigationView>(R.id.nav_view_FieldManager)
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
            replaceFragment(FieldManagerDashboard(), addToBackStack = false)
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
            R.id.homeFragment -> replaceFragment(FieldManagerDashboard())
            R.id.coconutTreeManagement -> replaceFragment(coconut_infoFragment())
            R.id.intercropsManagement -> replaceFragment(intercrops_infoFragment())
            R.id.harvestingManagement -> replaceFragment(hvst_infoFragment())
            R.id.fertilizerManagement -> replaceFragment(fert_mngFragment())
            R.id.resourceManagement -> replaceFragment(resource_mngFragment())
            R.id.catalogueManagement -> replaceFragment(CatalogueItemManageFragment())
            R.id.buyerOrderManagement -> replaceFragment(FieldManagerManageBuyerOrderFragment())
            R.id.taskManagement -> replaceFragment(ManageTaskFragment())
            R.id.supplierOrderManagement -> replaceFragment(SupplierOfferManagementFragment())
            R.id.log_out -> {
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, User_Login::class.java)
                startActivity(intent)
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
}