package com.mapstest

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.media.Image
import android.os.AsyncTask
import android.os.Bundle
import android.provider.Settings.Global
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.mapstest.R
import com.example.mapstest.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mapstest.data.RetrofitStuff
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception
import java.net.URL


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
        mMap.setInfoWindowAdapter(MapsInfoAdapter(this))
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
            //var bmp : Bitmap? = null
            var image: BitmapDescriptor? = null
            val iconUrl = URL(artist.artistMarker)
            try {
                /*
                Log.d("MapIcon", iconUrl.toString())
                val image = GlobalScope.async {
                    bmp = BitmapFactory.decodeStream(iconUrl.openConnection().getInputStream())
                    return@async bmp
                }
                while (
                    bmp == null
                ){
                    Thread.sleep(1_000)
                    Log.d("fetching icon", "waiting for user Icon")
                }
                */

            }catch (err:Exception){
                Log.d("MapsError", err.toString())
            }



            var bmp = GlobalScope.async {
                val loader = ImageLoader(this@MapsActivity)
                val req = ImageRequest.Builder(this@MapsActivity)
                    .data(iconUrl) // demo link
                    .allowHardware(false)
                        /*
                    .target { result ->
                        val bitmap = (result as BitmapDrawable).bitmap
                        bmp = bitmap
                        Log.d("coil test", bmp.toString())
                    }*/
                    .build()
                val result = (loader.execute(req) as SuccessResult).drawable
                return@async (result as BitmapDrawable).bitmap
            }

            var i = 0
            while (
                i < 5
            ){
                Thread.sleep(1_000)
                Log.d("coul test", "waiting for userImageBmp")
                i++
            }
            Log.d("coil test", bmp.toString())
            for (gig in artist.gigData){
                Log.d("Maps View", "skipped loader")
                var datesString : String = ""
                for (gigDateInVenue in gig.gigs){
                    datesString = datesString + gigDateInVenue.date + "\n" + gigDateInVenue.link + "\n"
                }
                mMap.addMarker(
                    MarkerOptions()
                        .position(LatLng(gig.venueLatLng[0], gig.venueLatLng[1]))
                        .title(artist.artistName)
                        .snippet(
                            gig.venueName + "\n" +
                            gig.venueAddress + "\n" +
                            datesString
                        )
                        .icon(BitmapDescriptorFactory.fromBitmap(bmp.getCompleted()))

                )

            }
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(center))
    }

}