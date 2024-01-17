package com.example.destinationapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.destinationapp.databinding.ActivitySummaryBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class SummaryActivity : AppCompatActivity(), OnMapReadyCallback {
    private var binding:ActivitySummaryBinding? = null
    private lateinit var dataList: ArrayList<ParcelModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySummaryBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val totalScore: Int = intent.getIntExtra("totalScore", 0)
        dataList = intent.getParcelableArrayListExtra("dataList")!!
        setAdapter(dataList)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.summary_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun setAdapter(dataList: ArrayList<ParcelModel>) {
        val recyclerView:RecyclerView = findViewById(R.id.recycler_summary)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = SummaryAdapter(dataList)
        recyclerView.adapter = adapter
    }

    override fun onMapReady(p0: GoogleMap) {

    }
}