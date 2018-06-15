package com.anwesh.uiprojects.kotlinlinkedsidearcview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.anwesh.uiprojects.linkedsidearcview.LinkedSideArcView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LinkedSideArcView.create(this)
    }
}
