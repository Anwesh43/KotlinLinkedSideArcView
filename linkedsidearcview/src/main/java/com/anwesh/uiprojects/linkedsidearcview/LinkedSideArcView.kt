package com.anwesh.uiprojects.linkedsidearcview

/**
 * Created by anweshmishra on 15/06/18.
 */

import android.view.View
import android.content.Context
import android.view.MotionEvent
import android.graphics.*

class LinkedSideArcView (ctx : Context) : View(ctx) {

    private val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}