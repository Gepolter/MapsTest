package com.mapstest

import android.os.Bundle
import com.example.mapstest.databinding.ActivityDashboardBinding

class Dashboard : DrawerBaseActivity() {
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        allocateActivityTitle("Dashboard")
    }
}