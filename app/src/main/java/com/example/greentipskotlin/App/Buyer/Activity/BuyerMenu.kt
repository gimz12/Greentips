package com.example.greentipskotlin.App.Buyer.Activity

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
import com.example.greentipskotlin.App.Buyer.BuyerCartFragment
import com.example.greentipskotlin.App.Buyer.BuyerCatalogueFragment
import com.example.greentipskotlin.App.Buyer.BuyerDashboardFragment
import com.example.greentipskotlin.App.Buyer.BuyerOrderHistoryFragment
import com.example.greentipskotlin.App.Buyer.BuyerReviewFragment
import com.example.greentipskotlin.App.Buyer.PaymentMethodFragment
import com.example.greentipskotlin.App.CEO.sup_Order_HistoryFragment
import com.example.greentipskotlin.R
import com.google.android.material.navigation.NavigationView

private lateinit var drawerLayout: DrawerLayout
private lateinit var toggle: ActionBarDrawerToggle

class BuyerMenu : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buyer_menu)

        drawerLayout = findViewById(R.id.drawer_layout_Buyer)

        // Initialize navigationView and set listener
        val navigationView = findViewById<NavigationView>(R.id.nav_view_Buyer)
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
            replaceFragment(dashboardFragment())
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
            R.id.homeFragment -> replaceFragment(BuyerDashboardFragment())
            R.id.viewCatalogueFragment -> replaceFragment(BuyerCatalogueFragment())
            R.id.viewCartFragment -> replaceFragment(BuyerCartFragment())
            R.id.orderHistoryFragment -> replaceFragment(BuyerOrderHistoryFragment())
            R.id.paymentMethodFragment -> replaceFragment(PaymentMethodFragment())
            R.id.reviewHistoryFragment -> replaceFragment(BuyerReviewFragment())
            R.id.yourProfileFragment -> replaceFragment(sup_Order_HistoryFragment())


            // Other fragments...
            R.id.log_out -> Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
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