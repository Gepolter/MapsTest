package com.mapstest

import android.os.Bundle
import com.example.mapstest.databinding.ActivityArtistSubsBinding

class ArtistsubsActivity : DrawerBaseActivity(){
    private lateinit var binding: ActivityArtistSubsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtistSubsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        allocateActivityTitle("Subscriptions")
    }


}