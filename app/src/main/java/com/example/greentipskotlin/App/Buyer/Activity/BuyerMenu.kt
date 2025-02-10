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
import com.example.greentipskotlin.App.Buyer.BuyerCartFragment
import com.example.greentipskotlin.App.Buyer.BuyerCatalogueFragment
import com.example.greentipskotlin.App.Buyer.BuyerDashboardFragment
import com.example.greentipskotlin.App.Buyer.BuyerOrderHistoryFragment
import com.example.greentipskotlin.App.Buyer.PaymentMethodFragment
import com.example.greentipskotlin.App.CEO.sup_Order_HistoryFragment
import com.example.greentipskotlin.App.User_Login
import com.example.greentipskotlin.R
import com.google.android.material.navigation.NavigationView

private lateinit var drawerLayout: DrawerLayout
private lateinit var toggle: ActionBarDrawerToggle

class BuyerMenu : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

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

        // Enable the toggle (hamburger) button in the action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        // Display BuyerDashboardFragment when first loaded
        if (savedInstanceState == null) {
            replaceFragment(BuyerDashboardFragment())
            navigationView.setCheckedItem(R.id.homeFragment)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        when (item.itemId) {
            R.id.profile -> {
                startActivity(Intent(this, UserProfileManagement::class.java))
            }
            R.id.notifications -> {
                startActivity(Intent(this, HarvestInfomationInsert::class.java))
            }
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
            R.id.yourProfileFragment -> replaceFragment(sup_Order_HistoryFragment())
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
