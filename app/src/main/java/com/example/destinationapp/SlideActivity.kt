package com.example.destinationapp

import android.view.View

object SlideActivity {

    fun slideUp(view: View) {
        view.animate().translationY(-view.height.toFloat())
            .setDuration(500).setListener(null)
    }

    fun slideDown(view: View) {
        view.animate().translationY(view.height.toFloat())
            .setDuration(500).setListener(null)
    }
}