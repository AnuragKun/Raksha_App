package com.arlabs.raksha.features.home

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.heatmaps.WeightedLatLng

object HeatmapData {
    fun getDummySafetyPoints(): List<WeightedLatLng> {
        val list = ArrayList<WeightedLatLng>()
        
        // Base location: New Delhi (28.6139, 77.2090)
        // Generate some points around it
        val baseLat = 28.6139
        val baseLng = 77.2090
        
        // Simulate some "unsafe" hotspots (high intensity)
        list.add(WeightedLatLng(LatLng(baseLat + 0.001, baseLng + 0.001), 1.0))
        list.add(WeightedLatLng(LatLng(baseLat + 0.002, baseLng - 0.001), 2.0))
        list.add(WeightedLatLng(LatLng(baseLat - 0.001, baseLng + 0.002), 0.5))
        list.add(WeightedLatLng(LatLng(baseLat - 0.002, baseLng - 0.002), 1.5))
        
        // Generate a cluster
        for (i in 0..10) {
            val offsetLat = (Math.random() - 0.5) * 0.01
            val offsetLng = (Math.random() - 0.5) * 0.01
            list.add(WeightedLatLng(LatLng(baseLat + offsetLat, baseLng + offsetLng), Math.random()))
        }
        
        return list
    }
}
