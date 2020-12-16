package com.somethingfun.jay.peanut

import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.jeongmin.peanutview.drawing.drawable.ArcDrawable
import com.jeongmin.peanutview.drawing.drawable.CircleDrawable
import com.jeongmin.peanutview.drawing.drawable.LineDrawable
import com.jeongmin.peanutview.drawing.drawable.SelfDrawable
import com.jeongmin.peanutview.drawing.math.LinearEquation
import com.jeongmin.peanutview.drawing.shape.Arc
import com.jeongmin.peanutview.drawing.shape.Circle
import com.jeongmin.peanutview.drawing.shape.Line
import com.jeongmin.peanutview.util.*
import com.somethingfun.jay.peanut.databinding.ActivityTaegeukgiBinding


class TaegeukgiActivity: AppCompatActivity() {

    private val colorRed   = 0xFFCD313A.toInt()
    private val colorBlue  = 0xFF0047A0.toInt()
    private val colorBlack = 0xFF0E0E0E.toInt()
    private val animationDuration = 800L

    private lateinit var binding: ActivityTaegeukgiBinding

    private lateinit var pLeftTop: PointF
    private lateinit var pRightBottom: PointF
    private lateinit var pRightTop: PointF
    private lateinit var pLeftBottom: PointF
    private lateinit var pointCenter: PointF

    private var slopeLeft: Float = 0f
    private var slopeRight: Float = 0f
    private var widthDivideWidth: Float = 0f
    private var centerCircleRadius: Float = 0f
    private var gwaWidth: Float = 0f
    private var gwaGuideCircleRadius: Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaegeukgiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.peanutView.post {
            initValues()
            initView()
        }
    }

    private fun initView() {
        binding.start.setOnClickListener {
            binding.peanutView.clear()
            draw()
        }
    }

    private fun initValues() {
        pLeftTop = PointF(0f, 0f)
        pRightBottom = PointF(binding.peanutView.width.toFloat(), binding.peanutView.height.toFloat())
        pRightTop = PointF(binding.peanutView.width.toFloat(), 0f)
        pLeftBottom = PointF(0f, binding.peanutView.height.toFloat())
        slopeLeft = slopeBetweenPoints(pLeftTop, pRightBottom)
        slopeRight = slopeBetweenPoints(pRightTop, pLeftBottom)
        pointCenter = PointF(binding.peanutView.width / 2f, binding.peanutView.height / 2f)
        widthDivideWidth = binding.peanutView.width / 3f
        centerCircleRadius = widthDivideWidth / 2f
        gwaWidth = widthDivideWidth / 2f
        gwaGuideCircleRadius = gwaWidth / 2f
    }

    private fun draw() {
        val guideLinesDrawables = drawGuideLines()
        binding.peanutView.startImmediate(guideLinesDrawables)
        var delay = guideLinesDrawables.last().totalAnimationDuration

        val taegeukDrawables = drawTaegeuk(delay)
        binding.peanutView.startImmediate(taegeukDrawables)
        delay = guideLinesDrawables.last().totalAnimationDuration

        val gwaDrawables = drawGwa(delay)
        binding.peanutView.startImmediate(gwaDrawables)

        gwaDrawables.last().setOnAnimationEndListener {
            guideLinesDrawables.forEach {
                binding.peanutView.removeAnimatable(it)
            }
        }
    }

    private fun drawGwa(startDelay: Long): List<SelfDrawable> {
        val drawables = arrayListOf<SelfDrawable>()

        // 건
        val l = LinearEquation(slopeRight, pointCenter)
        val gwaHeight = widthDivideWidth / 12f
        var radius = centerCircleRadius + widthDivideWidth / 4f + gwaHeight / 2
        var interSectionLeft = intersectionXwithCircle(radius, slopeLeft, 0f, pointCenter.x, pointCenter.y)
        var interSectionRight = intersectionXwithCircle(radius, slopeRight, l.interceptY(), pointCenter.x, pointCenter.y)
        drawables.addAll(drawLineWithCircle(slopeLeft, PointF(interSectionLeft[0], getY(slopeLeft, 0f, interSectionLeft[0])), startDelay))
        drawables.addAll(drawLineWithCircle(slopeLeft, PointF(interSectionLeft[1], getY(slopeLeft, 0f, interSectionLeft[1])), startDelay))
        drawables.addAll(drawLineWithCircle(slopeRight, PointF(interSectionRight[0], l.getY(interSectionRight[0])), startDelay))
        drawables.addAll(drawLineWithCircle(slopeRight, PointF(interSectionRight[1], l.getY(interSectionRight[1])), startDelay))
        var delay = drawables.last().totalAnimationDuration

        drawables.addAll(drawSpace(slopeLeft, PointF(interSectionLeft[1], getY(slopeLeft, 0f, interSectionLeft[1])), delay))
        drawables.addAll(drawSpace(slopeRight, PointF(interSectionRight[1], l.getY(interSectionRight[1])), delay))
        delay = drawables.last().totalAnimationDuration

        // 괘 사이간격 = 괘 높이 / 24
        // 괘 높이의 중간
        radius += (widthDivideWidth / 24) + (gwaHeight)
        interSectionLeft = intersectionXwithCircle(radius, slopeLeft, 0f, pointCenter.x, pointCenter.y)
        interSectionRight = intersectionXwithCircle(radius, slopeRight, l.interceptY(), pointCenter.x, pointCenter.y)
        drawables.addAll(drawLineWithCircle(slopeLeft, PointF(interSectionLeft[0], getY(slopeLeft, 0f, interSectionLeft[0])), delay))
        drawables.addAll(drawLineWithCircle(slopeLeft, PointF(interSectionLeft[1], getY(slopeLeft, 0f, interSectionLeft[1])), delay))
        drawables.addAll(drawLineWithCircle(slopeRight, PointF(interSectionRight[0], l.getY(interSectionRight[0])), delay))
        drawables.addAll(drawLineWithCircle(slopeRight, PointF(interSectionRight[1], l.getY(interSectionRight[1])), delay))
        delay = drawables.last().totalAnimationDuration

        drawables.addAll(drawSpace(slopeLeft, PointF(interSectionLeft[1], getY(slopeLeft, 0f, interSectionLeft[1])), delay))
        drawables.addAll(drawSpace(slopeRight, PointF(interSectionRight[0], l.getY(interSectionRight[0])), delay))
        delay = drawables.last().totalAnimationDuration

        radius += (widthDivideWidth / 24) + (gwaHeight)
        interSectionLeft = intersectionXwithCircle(radius, slopeLeft, 0f, pointCenter.x, pointCenter.y)
        interSectionRight = intersectionXwithCircle(radius, slopeRight, l.interceptY(), pointCenter.x, pointCenter.y)
        drawables.addAll(drawLineWithCircle(slopeLeft, PointF(interSectionLeft[0], getY(slopeLeft, 0f, interSectionLeft[0])), delay))
        drawables.addAll(drawLineWithCircle(slopeLeft, PointF(interSectionLeft[1], getY(slopeLeft, 0f, interSectionLeft[1])), delay))
        drawables.addAll(drawLineWithCircle(slopeRight, PointF(interSectionRight[0], l.getY(interSectionRight[0])), delay))
        drawables.addAll(drawLineWithCircle(slopeRight, PointF(interSectionRight[1], l.getY(interSectionRight[1])), delay))
        delay = drawables.last().totalAnimationDuration
        drawables.addAll(drawSpace(slopeLeft, PointF(interSectionLeft[1], getY(slopeLeft, 0f, interSectionLeft[1])), delay))
        drawables.addAll(drawSpace(slopeRight, PointF(interSectionRight[1], l.getY(interSectionRight[1])), delay))

        return drawables
    }

    private fun drawTaegeuk(delay: Long): List<SelfDrawable> {
        // 가운데 원
        val angle = getAngle(pLeftTop, pRightBottom)
        val rectCenter = RectF(widthDivideWidth, pointCenter.y - centerCircleRadius, widthDivideWidth * 2, pointCenter.y + centerCircleRadius)
        val arcTopStart = Arc().apply {
            oval = rectCenter
            startAngle = angle.toFloat()
            sweepAngle = 0f
        }

        val arcTopEnd = Arc().apply {
            oval = rectCenter
            startAngle = angle.toFloat()
            sweepAngle = 180f
        }
        val arcTopDrawable = ArcDrawable(makePaint(colorBlue), arcTopStart, arcTopEnd).apply {
            this.delay = delay
            setAnimDuration(animationDuration)
            direction = ArcDrawable.DIRECTION_RIGHT
        }

        val arcBottomStart = Arc().apply {
            oval = rectCenter
            startAngle = angle.toFloat() + 180f
            sweepAngle = 0f
        }
        val arcBottomEnd = Arc().apply {
            oval = rectCenter
            startAngle = angle.toFloat() + 180f
            sweepAngle = 180f
        }
        val arcBottomDrawable = ArcDrawable(makePaint(colorRed), arcBottomStart, arcBottomEnd).apply {
            this.delay = delay
            setAnimDuration(animationDuration)
            direction = ArcDrawable.DIRECTION_RIGHT
        }

        val radiusSmall = centerCircleRadius / 2f
        val intersectionX = intersectionXwithCircle(centerCircleRadius, slopeLeft, 0f, pointCenter.x, pointCenter.y)

        val yTop = (getY(slopeLeft, 0f, intersectionX[0]) + pointCenter.y) / 2
        val yBottom = (getY(slopeLeft, 0f, intersectionX[1]) + pointCenter.y) / 2

        val circleLeft = CircleDrawable(makePaint(colorRed),
                Circle((intersectionX[0] + pointCenter.x) / 2, yTop, 0f),
                Circle((intersectionX[0] + pointCenter.x) / 2, yTop, radiusSmall)
        ).apply {
            this.delay = arcTopDrawable.totalAnimationDuration
            setAnimDuration(animationDuration)
        }

        val circleRight = CircleDrawable(makePaint(colorBlue),
                Circle((intersectionX[1] + pointCenter.x) / 2, yBottom, 0f),
                Circle((intersectionX[1] + pointCenter.x) / 2, yBottom, radiusSmall)
        ).apply {
            this.delay = arcTopDrawable.totalAnimationDuration
            setAnimDuration(animationDuration)
        }

        return listOf(arcTopDrawable, arcBottomDrawable, circleLeft, circleRight)
    }

    private fun drawGuideLines(): List<SelfDrawable> {
        val guidePaint = makePaint(Color.GRAY, 2f, Paint.Style.STROKE, true)

        // 세로 구분선
        val widthDivideWidth = binding.peanutView.width / 3f
        val guideVertical1 = LineDrawable(guidePaint,
                Line(widthDivideWidth, 0f, widthDivideWidth, 0f),
                Line(widthDivideWidth, 0f, widthDivideWidth, binding.peanutView.height.toFloat())
        ).apply {
            setAnimDuration(animationDuration)
        }
        val guideVertical2 = LineDrawable(guidePaint,
                Line(widthDivideWidth * 2, binding.peanutView.height.toFloat(), widthDivideWidth * 2, binding.peanutView.height.toFloat()),
                Line(widthDivideWidth * 2, binding.peanutView.height.toFloat(), widthDivideWidth * 2, 0f)
        ).apply {
            setAnimDuration(animationDuration)
        }

        // 대각선
        val guide = LineDrawable(guidePaint,
                Line(pLeftTop, pLeftTop),
                Line(pLeftTop, pRightBottom)
        ).apply {
            this.delay = animationDuration
            setAnimDuration(animationDuration)
        }

        val guide2 = LineDrawable(guidePaint,
                Line(pRightTop, pRightTop),
                Line(pRightTop, pLeftBottom)
        ).apply {
            this.delay = animationDuration
            setAnimDuration(animationDuration)
        }

        val pointCenter = PointF(binding.peanutView.width / 2f, binding.peanutView.height / 2f)
        val pointCenterRadius = 15f
        val pointCenterDuration = 200
        val centerCircle = CircleDrawable(makePaint(Color.GRAY),
                Circle(pointCenter, 0f),
                Circle(pointCenter, pointCenterRadius)
        ).apply {
            this.delay = guide.totalAnimationDuration / 2
            setAnimDuration(pointCenterDuration)
        }

        // 가이드 원
        val guideCircleInner = CircleDrawable(guidePaint,
                Circle(pointCenter, pointCenterRadius),
                Circle(pointCenter, widthDivideWidth / 2f)
        ).apply {
            this.delay = centerCircle.delay + pointCenterDuration
            setAnimDuration(animationDuration)
        }

        // 괘 가이드 원
        val centerCircleRadius = widthDivideWidth / 2f
        val gwaGuideCircle = CircleDrawable(guidePaint,
                Circle(pointCenter, centerCircleRadius),
                Circle(pointCenter, centerCircleRadius + widthDivideWidth / 4f)
        ).apply {
            this.delay = guideCircleInner.totalAnimationDuration
            setAnimDuration(animationDuration)
        }

        // 음양 원
        val radiusSmall = centerCircleRadius / 2f
        val crossX = intersectionXwithCircle(centerCircleRadius, slopeLeft, 0f, pointCenter.x, pointCenter.y)

        val yTop = (getY(slopeLeft, 0f, crossX[0]) + pointCenter.y) / 2
        val yBottom = (getY(slopeLeft, 0f, crossX[1]) + pointCenter.y) / 2
        val circleLeft = CircleDrawable(guidePaint,
                Circle((crossX[0] + pointCenter.x) / 2, yTop, 0f),
                Circle((crossX[0] + pointCenter.x) / 2, yTop, radiusSmall)
        ).apply {
            this.delay = gwaGuideCircle.totalAnimationDuration
            setAnimDuration(animationDuration)
        }

        val circleRight = CircleDrawable(guidePaint,
                Circle((crossX[1] + pointCenter.x) / 2, yBottom, 0f),
                Circle((crossX[1] + pointCenter.x) / 2, yBottom, radiusSmall)
        ).apply {
            this.delay = gwaGuideCircle.totalAnimationDuration
            setAnimDuration(animationDuration)
        }

        return listOf(
                guide,
                guide2,
                guideVertical1,
                guideVertical2,
                centerCircle,
                gwaGuideCircle,
                guideCircleInner,
                circleLeft,
                circleRight,
        )
    }

    private fun drawLineWithCircle(slope: Float, p: PointF, delay: Long = 0): List<SelfDrawable> {
        // 1. p와 slope에 직교하는 직선 그리기
        // 직교하는 기울기를 구한다.
        val perpSlope = perpSlop(slope)
        // 점 p를 지나고 slope에 직교하는 직선의 방정식을 구한다.
        val le = LinearEquation(perpSlope, p)

        // 가이드 라인 그리기
        val guidePaint = makePaint(Color.GRAY, 2f, Paint.Style.STROKE, true)
        val guideIntersection = if (perpSlope > 0) {
            LineDrawable(
                    guidePaint,
                    Line(0f, le.interceptY(), 0f, le.interceptY()),
                    Line(0f, le.interceptY(), le.interceptX(), 0f),
            ).apply {
                this.delay = delay
            }
        }else {
            Log.d("jm.lee", "le.interceptX(): ${le.interceptX()}, le.getX(binding.peanutView.height.toFloat()): ${le.getX(binding.peanutView.height.toFloat())}")
            LineDrawable(
                    guidePaint,
                    Line(le.interceptX(), 0f, le.interceptX(), 0f),
                    Line(le.interceptX(), 0f, le.getX(binding.peanutView.height.toFloat()), binding.peanutView.height.toFloat()),
            ).apply {
                this.delay = delay
            }
        }

        // p가 중점이고 반지름이 괘 길이의 1/2인 원을 그린다
        val guideCircle = CircleDrawable(guidePaint,
                Circle(p, 0f),
                Circle(p, gwaGuideCircleRadius)
        ).apply {
            this.delay = delay
            setAnimDuration(animationDuration)
        }

        // 위에서 그린 원과 점 p를 지나고 slope에 직교하는 직선과의 교점을 구한다.
        val gwaHeight = widthDivideWidth / 12f
        val paintGwa = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            this.style = Paint.Style.STROKE
            this.color = colorBlack
            this.strokeWidth = gwaHeight
            this.isAntiAlias = true
        }
        val intersectionX = intersectionXwithCircle(gwaGuideCircleRadius, le.slope, le.b, p.x, p.y)
        val line = LineDrawable(paintGwa,
                Line(intersectionX[0], le.getY(intersectionX[0]), intersectionX[0], le.getY(intersectionX[0])),
                Line(intersectionX[0], le.getY(intersectionX[0]), intersectionX[1], le.getY(intersectionX[1])),
        ).apply {
            this.delay = guideCircle.totalAnimationDuration
            setAnimDuration(animationDuration)
        }


        val drawables = listOf(guideCircle, line)
        binding.peanutView.startImmediate(drawables)

        line.setOnAnimationEndListener {
            binding.peanutView.removeAnimatable(guideIntersection)
            binding.peanutView.removeAnimatable(guideCircle)
        }

        return drawables
    }

    private fun drawSpace(slope: Float, p: PointF, delay: Long = 0): List<SelfDrawable> {
        val animationDuration = 200
        // 1. p와 slope에 직교하는 직선 그리기
        // 직교하는 기울기를 구한다.
        val perpSlope = -1 / slope //perpSlop(slope)
        // 점 p를 지나고 slope에 직교하는 직선의 방정식을 구한다.
        val le = LinearEquation(perpSlope, p)

        // 가이드 라인 그리기
        val guidePaint = makePaint(Color.WHITE, 2f, Paint.Style.STROKE, true)

        // p가 중점이고 반지름이 괘 길이의 1/2인 원을 그린다
        val gwaWidth = widthDivideWidth / 2f
        val gwaGuideCircleRadius = gwaWidth / 24f
        val guideCircle = CircleDrawable(guidePaint,
                Circle(p, 0f),
                Circle(p, gwaGuideCircleRadius)
        ).apply {
            this.delay = delay
            setAnimDuration(animationDuration)
        }

        // 위에서 그린 원과 점 p를 지나고 slope에 직교하는 직선과의 교점을 구한다.
        val gwaHeight = widthDivideWidth / 12f
        val paintGwa = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            this.style = Paint.Style.STROKE
            this.color = Color.WHITE
            this.strokeWidth = gwaHeight
            this.isAntiAlias = true
        }

        val intersectionX = intersectionXwithCircle(gwaGuideCircleRadius, le.slope, le.b, p.x, p.y)
        val space = LineDrawable(paintGwa,
                Line(intersectionX[0], le.getY(intersectionX[0]), intersectionX[0], le.getY(intersectionX[0])),
                Line(intersectionX[0], le.getY(intersectionX[0]), intersectionX[1], le.getY(intersectionX[1])),
        ).apply {
            this.delay = guideCircle.delay + animationDuration
            setAnimDuration(animationDuration)
        }

        val drawables = listOf(guideCircle, space)
        binding.peanutView.startImmediate(drawables)

        space.setOnAnimationEndListener {
            binding.peanutView.removeAnimatable(guideCircle)
        }
        return drawables
    }
}