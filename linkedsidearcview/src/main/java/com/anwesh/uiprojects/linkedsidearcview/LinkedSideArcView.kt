package com.anwesh.uiprojects.linkedsidearcview

/**
 * Created by anweshmishra on 15/06/18.
 */

import android.view.View
import android.content.Context
import android.view.MotionEvent
import android.graphics.*

val SIDE_ARC_NODES = 5

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

    data class State(var scale : Float = 0f, var prevScale : Float = 0f, var dir : Float = 0f) {

        fun update(stopcb : (Float) -> Unit) {
            scale += 0.1f * dir
            if (Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                stopcb(prevScale)
            }
        }

        fun startUpdating(startcb : () -> Unit) {
            if (dir == 0f) {
                dir = 1 - 2 * prevScale
                startcb()
            }
        }
    }

    data class Animator(var view : View, var animated : Boolean = false) {

        fun start() {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }

        fun stop() {
            if (animated) {
                animated = false
            }
        }

        fun animate(cb : () -> Unit) {
            if (animated) {
                cb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                } catch(ex : Exception) {

                }
            }
        }
    }

    data class SideArcNode(var i : Int) {

        private val state : State = State()

        private var prev : SideArcNode? = null

        private var next : SideArcNode? = null

        init {
            addNeighbor()
        }

        fun addNeighbor() {
            if (i < SIDE_ARC_NODES - 1) {
                next = SideArcNode(i + 1)
                next?.prev = this
            }
        }

        fun draw(canvas : Canvas, paint : Paint) {
            val w : Float = canvas.width.toFloat()
            val h : Float = canvas.height.toFloat()
            val gap : Float = w / SIDE_ARC_NODES
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = Math.min(w, h)/60
            paint.strokeCap = Paint.Cap.ROUND
            paint.color = Color.parseColor("#9b59b6")
            canvas.save()
            canvas.translate(i * gap + gap * state.scale, h/2)
            canvas.drawArc(RectF(-gap/2, -gap/2, gap/2, gap/2), -60f, 120f, false, paint)
            canvas.restore()
        }

        fun update(stopcb : (Float) -> Unit) {
            state.update(stopcb)
        }

        fun startUpdating(startcb : () -> Unit) {
            state.startUpdating(startcb)
        }

        fun getNext(dir : Int, cb : () -> Unit) : SideArcNode {
            var curr : SideArcNode? = prev
            if (dir == 1) {
                curr = next
            }
            if (curr != null) {
                return curr
            }
            cb()
            return this
        }
    }

    data class LinkedSideArc(var i : Int) {

        private var curr : SideArcNode = SideArcNode(0)

        private var dir : Int = 1

        fun draw(canvas : Canvas, paint : Paint) {
            curr.draw(canvas, paint)
        }

        fun update(stopcb : (Float) -> Unit) {
            curr.update {
                curr = curr.getNext(dir) {
                    dir *= -1
                }
                stopcb(it)
            }
        }

        fun startUpdating(startcb : () -> Unit) {
            curr.startUpdating(startcb)
        }
    }
}