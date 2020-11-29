package com.somethingfun.jay.peanut

import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.jeongmin.peanutview.drawing.drawable.CircleDrawable
import com.jeongmin.peanutview.drawing.drawable.LineDrawable
import com.jeongmin.peanutview.drawing.drawable.SelfDrawable
import com.jeongmin.peanutview.drawing.shape.Circle
import com.jeongmin.peanutview.drawing.shape.Line
import com.jeongmin.peanutview.view.PeanutDrawEvent
import com.somethingfun.jay.peanut.databinding.ActivityBezierBinding
import kotlin.random.Random


class BezierActivity: AppCompatActivity() {

    private lateinit var binding: ActivityBezierBinding

    private val circleRadius = 30F
    private val lineDrawable = LineDrawable(makePaint(0xffffccff.toInt(), style = Paint.Style.STROKE), Line(PointF(), PointF()))

    private var p0 = PointF(500F, 2000F)
    private var p1 = PointF(100F, 100F)
    private var p2 = PointF(1200F, 100F)


    private var dot0 = Circle(p0, circleRadius)
    private var dot1 = Circle(p1, circleRadius)
    private var dot2 = Circle(p2, circleRadius)

    private var movingDot0 = CircleDrawable(makePaint(0xffffccff.toInt()), dot0, dot1)
    private var movingDot1 = CircleDrawable(makePaint(0xffffccff.toInt()), dot1, dot2)
    private var movingDot2 = CircleDrawable(makePaint(0xff00ffff.toInt()), Circle(PointF(), circleRadius))

    private val flyingDrawables = mutableListOf<ImageView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBezierBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initDrawable()
        initView()
    }

    private fun initDrawable() {
//        p0 = PointF(500F, 2000F)
//        p1 = PointF(100F, 100F)
//        p2 = PointF(1200F, 100F)

        //val deviceWidth = getDeviceWidth(this)
        //p0 = PointF(500F, 2000F)
        //p1 = PointF(100F, 100F)
        //p2 = PointF(1200F, 100F)
        val maxWidth = binding.peanutView.width
        val maxHeight = binding.peanutView.height

        p0 = PointF(Random.nextInt(0, maxWidth).toFloat(), maxHeight.toFloat() - 100)
        p1 = PointF(Random.nextInt(0, maxWidth).toFloat(), Random.nextInt(0, maxHeight).toFloat())
        p2 = PointF(Random.nextInt(0, maxWidth).toFloat(), 100F)

        dot0 = Circle(p0, circleRadius)
        dot1 = Circle(p1, circleRadius)
        dot2 = Circle(p2, circleRadius)

        movingDot0 = CircleDrawable(makePaint(0xffffccff.toInt()), dot0, dot1)
        movingDot1 = CircleDrawable(makePaint(0xffffccff.toInt()), dot1, dot2)
        movingDot2 = CircleDrawable(makePaint(0xff00ffff.toInt()), Circle(PointF(), circleRadius))

        binding.peanutView.clear()
    }

    private fun initGuide() {

        val circleP0 = CircleDrawable(makePaint(Color.WHITE), dot0)
        val circleP1 = CircleDrawable(makePaint(Color.WHITE), dot1)
        val circleP2 = CircleDrawable(makePaint(Color.WHITE), dot2)

        val lineP0P1 = LineDrawable(makePaint(Color.WHITE, 5F), Line(p0, p1), Line(p0, p1))
        val lineP1P2 = LineDrawable(makePaint(Color.WHITE, 5F), Line(p1, p2), Line(p1, p2))

        binding.peanutView.addAnimatable(circleP0)
        binding.peanutView.addAnimatable(circleP1)
        binding.peanutView.addAnimatable(circleP2)
        binding.peanutView.addAnimatable(lineP0P1)
        binding.peanutView.addAnimatable(lineP1P2)
        binding.peanutView.invalidate()
    }

    private fun initView() {
        binding.switchGuide.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.peanutView.clear()
        }

        binding.start.setOnClickListener {
            val animationDuration = 3000L
            initDrawable()
            if (binding.switchGuide.isChecked) {
                removeAllHeartImageView()
                initGuide()
                startDrawingBezierCurv(animationDuration)
            } else {
                bezierCurv(p0, p1, p2, animationDuration.toInt())
            }
        }
    }

    private fun startDrawingBezierCurv(animationDuration: Long) {
        movingDot0.setAnimDuration(animationDuration)
        movingDot1.setAnimDuration(animationDuration)
        binding.peanutView.addAnimatable(lineDrawable)
        binding.peanutView.startImmediate(listOf(movingDot0, movingDot1, movingDot2))
        binding.peanutView.setPeanutDrawEventListener(object : PeanutDrawEvent {
            override fun onDraw() {
                if (movingDot0.tX != null) {
                    lineDrawable.line1.x1 = movingDot0.tX!!
                    lineDrawable.line1.y1 = movingDot0.tY!!
                }

                if (movingDot1.tX != null) {
                    lineDrawable.line1.x2 = movingDot1.tX!!
                    lineDrawable.line1.y2 = movingDot1.tY!!
                }

                movingDot2.circleStart.cx = getCurrentValue(lineDrawable.line1.x1, lineDrawable.line1.x2, movingDot0.interpolation)
                movingDot2.circleStart.cy = getCurrentValue(lineDrawable.line1.y1, lineDrawable.line1.y2, movingDot0.interpolation)
            }
        })
    }

    private fun getCurrentValue(start: Float, end: Float, interpolator: Float): Float {
        return (end - start) * interpolator + start
    }

    private fun bezierCurv(p0: PointF, p1: PointF, p2: PointF, duration: Int) {
        val size = dpToPx(this, 32F).toInt()
        val imageView = ImageView(this).apply {
            layoutParams = ViewGroup.LayoutParams(size, size)
            setImageResource(R.drawable.ic_baseline_favorite_24)
            visibility = View.GONE
            setColorFilter(Color.argb(0xff, Random.nextInt(0, 255), Random.nextInt(0, 255), Random.nextInt(0, 255)))
        }
        binding.container.addView(imageView)

        ValueAnimator().apply {
            setIntValues(0, duration)
            setDuration(duration.toLong())
            addUpdateListener {
                val cx1 = getCurrentValue(p0.x, p1.x, it.animatedFraction)
                val cy1 = getCurrentValue(p0.y, p1.y, it.animatedFraction)

                val cx2 = getCurrentValue(p1.x, p2.x, it.animatedFraction)
                val cy2 = getCurrentValue(p1.y, p2.y, it.animatedFraction)

                val cx = getCurrentValue(cx1, cx2, it.animatedFraction)
                val cy = getCurrentValue(cy1, cy2, it.animatedFraction)


                imageView.visibility = View.VISIBLE
                imageView.x = cx - (imageView.width / 2)
                imageView.y = cy - (imageView.height / 2)
                imageView.alpha = 1 - it.animatedFraction
                imageView.requestLayout()

                /*
                binding.heart.visibility = View.VISIBLE
                binding.heart.x = cx - (binding.heart.width / 2)
                binding.heart.y = cy - (binding.heart.height / 2)
                binding.heart.requestLayout()
                 */
            }
        }.start()
        flyingDrawables.clear()
    }

    private fun removeAllHeartImageView() {
        val listToRemove = mutableListOf<ImageView>()
        for (i: Int in 0 until binding.container.childCount) {
            (binding.container.getChildAt(i) as? ImageView)?.let { imageView ->
                listToRemove.add(imageView)
            }
        }

        listToRemove.forEach {
            binding.container.removeView(it)
        }
    }
}