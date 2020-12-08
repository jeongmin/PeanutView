package com.somethingfun.jay.peanut

import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.jeongmin.peanutview.drawing.drawable.*
import com.jeongmin.peanutview.drawing.shape.Circle
import com.jeongmin.peanutview.drawing.shape.Line
import com.jeongmin.peanutview.view.PeanutDrawEvent
import com.somethingfun.jay.peanut.databinding.ActivityBezierBinding
import kotlin.math.pow
import kotlin.random.Random


class BezierActivity: AppCompatActivity() {

    private lateinit var binding: ActivityBezierBinding

    private val colorCyan = 0xff00ffff.toInt()
    private val colorPink = 0xffffccff.toInt()

    private val circleRadius = 30F
    private val heartSize: Int by lazy { dpToPx(this, 32F).toInt() }

    private val dotList = mutableListOf<Circle>()
    private val movingDots = mutableListOf<CircleDrawable>()
    private val lines = mutableListOf<LineDrawable>()

    private var dotBezier = CircleDrawable(makePaint(colorCyan), Circle(PointF(), circleRadius))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBezierBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initDrawable() {
        val minX = heartSize
        val maxX = binding.peanutView.width - heartSize
        val minY = heartSize
        val maxY = binding.peanutView.height - heartSize

        dotList.clear()
        movingDots.clear()
        if (isQuad()) {
            for (i in 0 until 3) {
                val dot = Circle(0f, 0f, circleRadius)
                when (i) {
                    0 -> {
                        dot.cx = Random.nextInt(minX, maxX).toFloat()
                        //dot.cy = maxY.toFloat()
                        dot.cy = Random.nextInt(minY, maxY).toFloat()
                    }
                    2 -> {
                        dot.cx = Random.nextInt(minX, maxX).toFloat()
                        //dot.cy = heartSize.toFloat()
                        dot.cy = Random.nextInt(minY, maxY).toFloat()
                    }
                    else -> {
                        dot.cx = Random.nextInt(minX, maxX).toFloat()
                        dot.cy = Random.nextInt(minY, maxY).toFloat()
                    }
                }
                dotList.add(dot)
            }
        } else {
            for (i in 0 until 4) {
                val dot = Circle(0f, 0f, circleRadius)
                when (i) {
                    0 -> {
                        dot.cx = Random.nextInt(minX, maxX).toFloat()
                        dot.cy = maxY.toFloat()
                        //dot.cy = Random.nextInt(minY, maxY).toFloat()
                    }
                    3 -> {
                        dot.cx = Random.nextInt(minX, maxX).toFloat()
                        dot.cy = heartSize.toFloat()
                        //dot.cy = Random.nextInt(minY, maxY).toFloat()
                    }
                    else -> {
                        dot.cx = Random.nextInt(minX, maxX).toFloat()
                        dot.cy = Random.nextInt(minY, maxY).toFloat()
                    }
                }
                dotList.add(dot)
            }
        }
        dotBezier = CircleDrawable(makePaint(colorCyan), Circle(PointF(), circleRadius))
        binding.peanutView.clear()
    }

    private fun initGuide() {
        for (i in 0 until dotList.size) {
            binding.peanutView.addAnimatable(CircleDrawable(makePaint(Color.WHITE), dotList[i]))
        }

        for (i in 0 until dotList.size - 1) {
            val start = dotList[i]
            val end   = dotList[i + 1]
            val lineDrawable = LineDrawable(makePaint(Color.WHITE, 5F), Line(start.getPoint(), end.getPoint()))
            lines.add(lineDrawable)
            binding.peanutView.addAnimatable(lineDrawable)

            val movingDot = CircleDrawable(makePaint(colorPink), start, end)
            movingDots.add(movingDot)
        }

        if (binding.showCurve.isChecked) {
            binding.peanutView.addAnimatable(BezierCurveDrawable(makePaint(colorCyan, 10F, Paint.Style.STROKE), dotList.map { it.getPoint() }))
        }
        binding.peanutView.invalidate()
    }

    private fun initView() {
        binding.switchGuide.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.peanutView.clear()
            removeAllHeartImageView()
        }

        binding.start.setOnClickListener {
            val animationDuration = 5000L
            initDrawable()
            if (binding.switchGuide.isChecked) {
                initGuide()
                startDrawingBezierCurve(animationDuration.toInt())
            } else {
                if (isQuad()) {
                    bezierCurveQuad(
                            dotList[0].getPoint(),
                            dotList[1].getPoint(),
                            dotList[2].getPoint(),
                            animationDuration.toInt())
                } else {
                    bezierCurveCubic(
                            dotList[0].getPoint(),
                            dotList[1].getPoint(),
                            dotList[2].getPoint(),
                            dotList[3].getPoint(),
                            animationDuration.toInt())
                }
            }
        }
    }

    private fun startDrawingBezierCurve(duration: Int) {
        val selfDrawables = mutableListOf<SelfDrawable>()

        val lines = mutableListOf<LineDrawable>()
        val dots = mutableListOf<CircleDrawable>()
        for (i in 0 until (dotList.size - 2)) {
            val start = dotList[i]
            val center = dotList[i+1]
            val end = dotList[i+2]
            LineDrawable(makePaint(colorPink, style = Paint.Style.STROKE),
                    Line(start.getPoint(), center.getPoint()),
                    Line(center.getPoint(), end.getPoint())
            ).apply {
                setAnimDuration(duration)
                selfDrawables.add(this)
                lines.add(this)
            }

            CircleDrawable(makePaint(colorPink), Circle(start.getPoint(), circleRadius)).also {
                selfDrawables.add(it)
                dots.add(it)
            }

            CircleDrawable(makePaint(colorPink), Circle(center.getPoint(), circleRadius)).also {
                selfDrawables.add(it)
                dots.add(it)
            }
        }

        movingDots.forEach {
            it.setAnimDuration(duration)
            selfDrawables.add(it)
        }

        if (dotList.size == 3) {
            BezierCircleDrawable(makePaint(colorCyan), circleRadius, dotList[0].getPoint(), dotList[1].getPoint(), dotList[2].getPoint()).apply {
                setAnimDuration(duration)
                selfDrawables.add(this)
            }
        } else {
            BezierCircleDrawable(makePaint(colorCyan), circleRadius, dotList[0].getPoint(), dotList[1].getPoint(), dotList[2].getPoint(), dotList[3].getPoint()).apply {
                setAnimDuration(duration)
                selfDrawables.add(this)
            }
        }

        val lastLine = LineDrawable(makePaint(colorCyan), Line(PointF(), PointF())).also {
            selfDrawables.add(it)
        }

        val lastDots = mutableListOf<CircleDrawable>()
        if (!isQuad()) {
            CircleDrawable(makePaint(colorPink), Circle(0f, 0f, circleRadius)).also {
                lastDots.add(it)
            }

            CircleDrawable(makePaint(colorPink), Circle(0f, 0f, circleRadius)).also {
                lastDots.add(it)
            }
        }

        binding.peanutView.startImmediate(selfDrawables)
        binding.peanutView.setPeanutDrawEventListener(object : PeanutDrawEvent {
            override fun onDraw() {
                if (lines.size == 2) {
                    val line1 = lines[0]
                    dots[0].circleStart.cx = getCurrentValue(line1.tX1, line1.tX2, movingDots[0].interpolation)
                    dots[0].circleStart.cy = getCurrentValue(line1.tY1, line1.tY2, movingDots[0].interpolation)

                    val line2 = lines[1]
                    dots[1].circleStart.cx = getCurrentValue(line2.tX1, line2.tX2, movingDots[0].interpolation)
                    dots[1].circleStart.cy = getCurrentValue(line2.tY1, line2.tY2, movingDots[0].interpolation)

                    lastLine.line.x1 = dots[0].circleStart.cx
                    lastLine.line.x2 = dots[1].circleStart.cx
                    lastLine.line.y1 = dots[0].circleStart.cy
                    lastLine.line.y2 = dots[1].circleStart.cy
                }

            }
        })
    }

    private fun startDrawingBezierCurve(animationDuration: Long) {
        val selfDrawables = mutableListOf<SelfDrawable>()
        val lines = createLines(movingDots)
        lines.forEach {
            it.setAnimDuration(animationDuration)
            selfDrawables.add(it)
        }

        movingDots.forEach {
            it.setAnimDuration(animationDuration)
            selfDrawables.add(it)
        }

        binding.peanutView.setPeanutDrawEventListener(object : PeanutDrawEvent {
            override fun onDraw() {
                for (i in 0 until movingDots.size - 1) {
                    val lineDrawable = lines[i]

                    val dotStart = movingDots[i]
                    val dotEnd = movingDots[i + 1]

                    if (dotStart.tX != null) {
                        lineDrawable.line.x1 = dotStart.tX!!
                        lineDrawable.line.y1 = dotStart.tY!!
                    }

                    if (dotEnd.tX != null) {
                        lineDrawable.line.x2 = dotEnd.tX!!
                        lineDrawable.line.y2 = dotEnd.tY!!
                    }

                    Log.d("jm.lee", "dotStart from(${dotStart.circleStart.cx}, ${dotStart.circleStart.cy}), t(${dotStart.tX}, ${dotStart.tY}), end(${dotStart.circleEnd?.cx}, ${dotStart.circleEnd?.cy})")
                }

                //dotBezier.circleStart.cx = getCurrentValue(lineDrawable.line.x1, lineDrawable.line.x2, movingDot0.interpolation)
                //dotBezier.circleStart.cy = getCurrentValue(lineDrawable.line.y1, lineDrawable.line.y2, movingDot0.interpolation)
            }
        })
        binding.peanutView.startImmediate(selfDrawables)
    }

    private fun createLines(dots: List<CircleDrawable>): List<LineDrawable> {
        val lines = mutableListOf<LineDrawable>()
        for (i in 0 until (dots.size - 1)) {
            val lineDrawable = LineDrawable(makePaint(colorPink, style = Paint.Style.STROKE), Line(PointF(), PointF()))
            lines.add(lineDrawable)
        }
        return lines
    }

    private fun getCurrentValue(start: Float, end: Float, interpolator: Float): Float {
        return (end - start) * interpolator + start
    }


    private fun bezierCurveQuad(p0: PointF, p1: PointF, p2: PointF, duration: Int) {
        val imageView = createImageView()
        binding.container.addView(imageView)

        val point = PointF()
        ValueAnimator().apply {
            setIntValues(0, duration)
            setDuration(duration.toLong())
            addUpdateListener {
                val t = it.animatedFraction
                calcQuadBezierCurve(p0, p1, p2, t, point)

                imageView.visibility = View.VISIBLE
                imageView.x = point.x - (imageView.width / 2)
                imageView.y = point.y - (imageView.height / 2)
                imageView.alpha = 1 - t
                imageView.requestLayout()
            }
        }.start()
    }

    private fun createImageView() = ImageView(this).apply {
        layoutParams = ViewGroup.LayoutParams(heartSize, heartSize)
        setImageResource(R.drawable.ic_baseline_favorite_24)
        visibility = View.GONE
        setColorFilter(Color.argb(0xff, Random.nextInt(0, 255), Random.nextInt(0, 255), Random.nextInt(0, 255)))
    }

    private fun bezierCurveCubic(p0: PointF, p1: PointF, p2: PointF, p3: PointF, duration: Int) {
        val imageView = createImageView()
        binding.container.addView(imageView)
        val point = PointF()

        ValueAnimator().apply {
            setIntValues(0, duration)
            setDuration(duration.toLong())
            addUpdateListener {
                val t = it.animatedFraction
                calcCubicBezierCurve(p0, p1, p2, p3, t, point)

                imageView.visibility = View.VISIBLE
                imageView.x = point.x - (imageView.width / 2)
                imageView.y = point.y - (imageView.height / 2)
                imageView.alpha = 1 - it.animatedFraction
                imageView.requestLayout()
            }
        }.start()
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

    private fun calcQuadBezierCurve(p0: PointF, p1: PointF, p2: PointF, t: Float, ret: PointF) {
        ret.x = ((1 - t).toDouble().pow(2) * p0.x + 2 * (1 - t) * t * p1.x + t.toDouble().pow(2) * p2.x).toFloat()
        ret.y = ((1 - t).toDouble().pow(2) * p0.y + 2 * (1 - t) * t * p1.y + t.toDouble().pow(2) * p2.y).toFloat()
    }


    private fun calcCubicBezierCurve(p0: PointF, p1: PointF, p2: PointF, p3: PointF, t: Float, ret: PointF) {
        val left = PointF()
        val right = PointF()
        calcQuadBezierCurve(p0, p1, p2, t, left)
        calcQuadBezierCurve(p1, p2, p3, t, right)

        ret.x = (1 - t) * left.x + t * right.x
        ret.y = (1 - t) * left.y + t * right.y
    }

    private fun isQuad(): Boolean = binding.groupBezier.checkedRadioButtonId == R.id.bezierQuad
}