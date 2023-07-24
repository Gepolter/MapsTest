package com.mapstest


import android.content.Intent
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.mapstest.R
import com.google.android.material.navigation.NavigationView

open class DrawerBaseActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {
    var drawerLayout: DrawerLayout? = null
    override fun setContentView(view: View) {

        drawerLayout = layoutInflater.inflate(R.layout.activity_drawer_base, null) as DrawerLayout
        val container = drawerLayout!!.findViewById<FrameLayout>(R.id.activityContainer)
        container.addView(view)
        super.setContentView(drawerLayout)

        val toolbar = drawerLayout!!.findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val navigationView = drawerLayout!!.findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.menu_drawer_open,
            R.string.menu_drawer_close
        )
        toggle.syncState()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout!!.closeDrawer(GravityCompat.START)
        Log.d("Navigation Registered", item.itemId.toString())
        if (item.itemId == R.id.nav_dashboard) {
            val i = Intent(this,Dashboard::class.java )
            i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(i)
            overridePendingTransition(0, 0)
        } else if (item.itemId == R.id.nav_map) {
            val i = Intent(this, MapsActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(i)
            overridePendingTransition(0, 0)
        } else if (item.itemId == R.id.nav_subs) {
            val i = Intent(this, ArtistsubsActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(i)
            overridePendingTransition(0, 0)
        }
        return false
    }

    protected fun allocateActivityTitle(titleString: String?) {
        if (supportActionBar != null) {
            supportActionBar!!.title = titleString
        }
    }
}
