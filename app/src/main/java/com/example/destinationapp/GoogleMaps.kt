package com.example.destinationapp

import android.content.Context
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.Dash
import com.google.android.gms.maps.model.Gap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PatternItem
import com.google.android.gms.maps.model.PolylineOptions
import kotlin.math.roundToInt
import kotlin.random.Random

//Pracuje s prvkami mapy, vyžaduje prístup k zdrojom a službám appky (GoogleMap a Context)
class GoogleMaps(private val mGoogleMap: GoogleMap?, private val context:Context) {

    private var correctPlace = LatLng(0.0, 0.0)

    private var selectedPlace:LatLng?= null

    fun setSelectedPlace(answer:LatLng?) {
        this.selectedPlace = answer
    }

    fun setCorrectPlace(answer: LatLng) {
        this.correctPlace = answer
    }

    //vypocita skutocnu vzdialenost medzi vybranym a spravnym miestom
    //Vyuziva Location.distanceBetween API, ktora zobrazuje pribliznu vzdialenost v metroch
    fun getRealDistance():Int {
        val result = FloatArray(1)
        Location.distanceBetween(
            selectedPlace?.latitude!!,
            selectedPlace?.longitude!!,
            correctPlace.latitude,
            correctPlace.longitude,
            result
        )
        return (result[0]/1609.34).roundToInt()
    }

    private var placeMarker: Marker? = null

    fun addSelectedPlaceMarker(position:LatLng) {
        placeMarker?.remove()
        placeMarker = mGoogleMap?.addMarker(MarkerOptions().position(position).icon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin)))
    }

    fun addBlueMarker(position: LatLng) {
        mGoogleMap?.addMarker(MarkerOptions().position(position).icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_pin)))
    }

    //Pomocka pre pouzivatela, ktora zobrazuje "napovedu" pre spravne miesto
    fun addCircle() {
        val latValue = correctPlace.latitude
        val lngValue = correctPlace.longitude

        val position = LatLng(latValue + Random.nextDouble(0.01, 0.1), lngValue + Random.nextDouble(0.01, 0.1))
        mGoogleMap?.addCircle(CircleOptions().center(position).fillColor(ContextCompat.getColor(context, R.color.trans_yellow))
            .strokeColor(ContextCompat.getColor(context, R.color.yellow))
            .radius(15000.0))

        zoomCameraOnMap(position, 10f)
    }

    //Nastavuje kameru tak, aby bolo vidiet vybrane miesto pouzivatelom a spravne miesto
    fun zoomCameraOnMap() {
        val builder = LatLngBounds.builder()
        builder.include(selectedPlace!!)
        builder.include(correctPlace)
        val limits = builder.build()
        val updateCamera = CameraUpdateFactory.newLatLngBounds(limits, 200)
        mGoogleMap?.animateCamera(updateCamera)
    }

    fun zoomCameraOnMap(position: LatLng, zoomLevel: Float) {
        val newLatLngZoom = CameraUpdateFactory.newLatLngZoom(position, zoomLevel)
        mGoogleMap?.animateCamera(newLatLngZoom)
    }

    fun addPolyLine(correctPlace: LatLng, selectedPlace: LatLng) {
        val dash:PatternItem = Dash(20f)
        val gap:PatternItem = Gap(20f)
        val linePattern = listOf(gap, dash)

        mGoogleMap?.addPolyline(PolylineOptions().add(correctPlace, selectedPlace).color(ContextCompat.getColor(context, R.color.black))
            .pattern(linePattern))
    }
}