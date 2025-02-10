package com.example.greentipskotlin.App.CEO

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
import com.example.greentipskotlin.App.Admin.dashboardFragment
import com.example.greentipskotlin.App.Buyer.BuyerCatalogueFragment
import com.example.greentipskotlin.App.FieldManager.CatalogueItemManageFragment
import com.example.greentipskotlin.App.Model.Catalogue
import com.example.greentipskotlin.App.User_Login
import com.example.greentipskotlin.R
import com.google.android.material.navigation.NavigationView

private lateinit var drawerLayout: DrawerLayout
private lateinit var toggle: ActionBarDrawerToggle

class CEOMenu : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ceomenu)

        drawerLayout = findViewById(R.id.drawer_layout_CEO)

        // Initialize navigationView and set listener
        val navigationView = findViewById<NavigationView>(R.id.nav_view_CEO)
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

        //set visible the toggleDrawer button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        if (savedInstanceState == null) {
            replaceFragment(ceoDashboardFragment())
            navigationView.setCheckedItem(R.id.homeFragment)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        if (id == R.id.profile) {
            val groupMessageIntent = Intent(
                this,
                UserProfileManagement::class.java
            )
            startActivity(groupMessageIntent)
        }else if (id == R.id.notifications){
            val notificationIntent = Intent(
                this,
                HarvestInfomationInsert::class.java
            )
            startActivity(notificationIntent)
        }

        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.homeFragment -> replaceFragment(ceoDashboardFragment())
            R.id.assignTaskFragment -> replaceFragment(assignTaskFragment())
            R.id.task_HistoryFragment -> replaceFragment(task_HistoryFragment())
            R.id.supplier_order_reqFragment -> replaceFragment(supplier_order_reqFragment())
            R.id.sup_Order_HistoryFragment -> replaceFragment(sup_Order_HistoryFragment())
            R.id.buyer_Order_HisFragment -> replaceFragment(buyer_Order_HisFragment())
            R.id.financial_ReportFragment -> replaceFragment(financial_ReportFragment())
            R.id.custom_ReportsFragment -> replaceFragment(custom_ReportsFragment())


            // Other fragments...
            R.id.log_out -> {
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, User_Login::class.java))
                finish()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


    private fun replaceFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}