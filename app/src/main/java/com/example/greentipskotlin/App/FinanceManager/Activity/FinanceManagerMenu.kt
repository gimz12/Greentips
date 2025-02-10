package com.example.greentipskotlin.App.FinanceManager.Activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.greentipskotlin.App.Admin.Activity.HarvestInfomationInsert
import com.example.greentipskotlin.App.Admin.Activity.UserProfileManagement
import com.example.greentipskotlin.App.Admin.dashboardFragment
import com.example.greentipskotlin.App.Buyer.BuyerCatalogueFragment
import com.example.greentipskotlin.App.CEO.assignTaskFragment
import com.example.greentipskotlin.App.CEO.buyer_Order_HisFragment
import com.example.greentipskotlin.App.CEO.buyer_RegFragment
import com.example.greentipskotlin.App.CEO.supplier_Reg_ReqFragment
import com.example.greentipskotlin.App.CEO.supplier_order_reqFragment
import com.example.greentipskotlin.App.CEO.task_HistoryFragment
import com.example.greentipskotlin.App.FieldManager.CatalogueItemManageFragment
import com.example.greentipskotlin.App.FieldManager.FieldManagerDashboard
import com.example.greentipskotlin.App.FinanceManager.ConfirmedSupplierOrderFragment
import com.example.greentipskotlin.App.FinanceManager.FinanceManagerDashboardFragment
import com.example.greentipskotlin.App.FinanceManager.FinancialReportsFragment
import com.example.greentipskotlin.App.FinanceManager.PendingSupplierOrdersFragment
import com.example.greentipskotlin.App.User_Login
import com.example.greentipskotlin.R
import com.google.android.material.navigation.NavigationView

private lateinit var drawerLayout: DrawerLayout
private lateinit var toggle: ActionBarDrawerToggle

class FinanceManagerMenu : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finance_manager_menu)

        drawerLayout = findViewById(R.id.drawer_layout_FinanceManager)

        // Initialize navigationView and set listener
        val navigationView = findViewById<NavigationView>(R.id.nav_view_FinanceManager)
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
            replaceFragment(FinanceManagerDashboardFragment())
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
            R.id.homeFragment -> replaceFragment(FieldManagerDashboard())
            R.id.confirmedSupplierOrderFragment -> replaceFragment(ConfirmedSupplierOrderFragment())
            R.id.pendingSupplierOrderFragment -> replaceFragment(PendingSupplierOrdersFragment())
            R.id.PaymentRecordsFragment -> replaceFragment(PaymentRecordsFragment())
            R.id.financialReportsFragment -> replaceFragment(FinancialReportsFragment())


            // Other fragments...
            R.id.log_out -> {
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()

                // Redirect to UserLogin activity
                val intent = Intent(this, User_Login::class.java)
                startActivity(intent)

                // Finish the current activity to prevent returning to it
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