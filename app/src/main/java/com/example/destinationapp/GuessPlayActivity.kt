package com.example.destinationapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.destinationapp.databinding.ActivityGuessPlayBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.StreetViewPanorama
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment
import com.google.android.gms.maps.model.LatLng
import kotlin.math.max

class GuessPlayActivity : AppCompatActivity(), OnMapReadyCallback {

    private var binding: ActivityGuessPlayBinding? = null
    private var mGoogleMap: GoogleMap? = null
    private lateinit var mapView: View
    private lateinit var linearScoreResult: View
    private lateinit var streetView: StreetViewPanorama

    private lateinit var googleMapClass: GoogleMaps
    private var round = 1

    private lateinit var correctPlace: LatLng
    private var selectedPlace: LatLng? = null
    private lateinit var correctPlaceList:Set<LatLng>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuessPlayBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        mapView = findViewById(R.id.map_View)
        linearScoreResult = findViewById(R.id.linear_score_result)
        correctPlaceList = FamousPlaces.getFamousPlaceList()
        correctPlace = correctPlaceList.first()

        val streetViewPanoramaFragment =
            supportFragmentManager.findFragmentById(R.id.guess_Street_FragmentView)
                    as SupportStreetViewPanoramaFragment?

        streetViewPanoramaFragment?.getStreetViewPanoramaAsync {
            it.setPosition(correctPlace)
            streetView = it
        }

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map_Fragment) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        binding?.openMaps?.setOnClickListener() {
            SlideActivity.slideUp(mapView)
        }

        binding?.closeMap?.setOnClickListener {
            SlideActivity.slideDown(mapView)
        }

        binding?.markPlaceButton?.setOnClickListener {
            if (selectedPlace != null) {
                googleMapClass.addBlueMarker(correctPlace)
                googleMapClass.addPolyLine(correctPlace, selectedPlace!!)
                googleMapClass.zoomCameraOnMap()

                mGoogleMap?.setOnMapClickListener(null)
                setTotalScore()
                showScore()
                selectedPlace = null
            }

            if( round == 5) {
                binding?.nextRound?.text = "Check summary"
            }
        }

        binding?.nextRound?.setOnClickListener {
            if( round <5) {
                round++
                correctPlace = correctPlaceList.elementAt(round - 1)
                googleMapClass.setCorrectPlace(correctPlace)
                streetView.setPosition(correctPlace)

                SlideActivity.slideUp(linearScoreResult)
                SlideActivity.slideDown(mapView)
                mGoogleMap?.clear()

                binding?.markPlaceButton?.setImageResource(R.drawable.mark_place_button)
                onMapClick()
                googleMapClass.zoomCameraOnMap(LatLng(0.0, 0.0), 1f)
            } else {
                gameOver()
            }
        }
        binding?.hintButton?.setOnClickListener {
            googleMapClass.addCircle()
            binding?.hintButton?.visibility = View.GONE
        }
    }

    private fun gameOver() {
        Toast.makeText(this, "Game's over!", Toast.LENGTH_SHORT).show()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap

        googleMapClass = GoogleMaps(googleMap, this@GuessPlayActivity)

        onMapClick()
    }

    private fun onMapClick() {
        mGoogleMap?.setOnMapClickListener {
            selectedPlace = it
            googleMapClass.setSelectedPlace(it)
            googleMapClass?.setCorrectPlace(correctPlace)
            googleMapClass.addSelectedPlaceMarker(it)
            binding?.markPlaceButton?.setImageResource(R.drawable.mark_button)
        }
    }

    private fun showScore() {
        binding?.textviewScoreRound?.text = "Round $round"
        binding?.textviewScore?.text = "Congratulations, you got ${getScore()} score!"
        binding?.textviewDistance?.text =
            "You are ${googleMapClass.getRealDistance()} kilometres far away!"
        binding?.progressbarScore?.progress = getScore()
        SlideActivity.slideDown(linearScoreResult)
    }

    private fun getScore(): Int {
        val distance = googleMapClass.getRealDistance()
        return max(0, 5000 - 2 * distance)
    }

    private var totalScore = 0
    private fun setTotalScore() {
        totalScore += getScore()
    }
}