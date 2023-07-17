package com.mapstest

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.mapstest.R
import com.mapstest.data.RetrofitStuff
import com.example.mapstest.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class MapsActivity : DrawerBaseActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //navigation
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        allocateActivityTitle("Map")
        //val viewModel : MapViewModel by viewModels()
        val viewModel = ViewModelProvider(this).get(MapViewModel::class.java)

        Log.d("Maps View", "newMapActivity launched")
        Log.d("Maps View", intent.extras.toString())
        //functionality
        if(intent.hasExtra("userData")){//intent.extras?.isEmpty != true
            val userData = intent.extras?.get("userData") as RetrofitStuff.RetrofitHelper.Result
            Log.d("Maps View", "INTENT EXTRA REGISTERED")
            //save user data in view model
            //val viewModel: MapViewModel by viewModels()
            GlobalScope.launch {//lifecyclescope
                //repeatOnLifecycle(Lifecycle.State.STARTED){
                    viewModel.updateUserData(userData)
                    Log.d("viewmodeldata", viewModel.uiState.value.toString())
                //}
            }
            Log.d("mapsView", userData.toString())

        }


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    //map settings
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val viewModel = ViewModelProvider(this).get(MapViewModel::class.java)
        Log.d("Maps View Map Ready", viewModel.uiState.value.toString())
        //i want js back..
        while (
            viewModel.uiState.value.user.userName == "defaultName"
        ){
            Thread.sleep(1_000)
            Log.d("Maps View Map Ready", "waiting for View Model coroutine")

        }
        val userData =  viewModel.uiState.value//intent.extras?.get("userData") as RetrofitStuff.RetrofitHelper.Result
        val center = LatLng(userData.user.locationLatLng[0], userData.user.locationLatLng[1])

        mMap.addMarker(
            MarkerOptions()
                .position(center)
                .title("this is you!")
        )

        mMap.moveCamera(CameraUpdateFactory.newLatLng(center))
        for (artist in userData.artists){
            for (gig in artist.gigData){
                mMap.addMarker(
                    MarkerOptions()
                        .position(LatLng(gig.venueLatLng[0], gig.venueLatLng[1]))
                        .title(artist.artistName)
                        .snippet(gig.venueName + "\n" + gig.venueAddress)

                )

            }
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(center))
    }
}