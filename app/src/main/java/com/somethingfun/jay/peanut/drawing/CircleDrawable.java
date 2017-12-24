package com.somethingfun.jay.peanut.drawing;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by jay on 17. 11. 11.
 */

public class CircleDrawable extends DrawableObject {

    private float cx;
    private float cy;
    private float radius;

    private float animCx;
    private float animCy;
    private float animRadius;

    public CircleDrawable(float cx, float cy, float radius) {
        this.cx = cx;
        this.cy = cy;
        this.radius = radius;

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0xff101010);
        paint.setStrokeWidth(10);
    }

    public void setAnimation(float animCx, float animCy, float animRadius) {
        this.animCx = animCx;
        this.animCy = animCy;
        this.animRadius = animRadius;
    }

    @Override
    protected void drawInitialState(Canvas canvas) {
        canvas.drawCircle(cx, cy, radius, paint);
    }

    @Override
    protected void drawLastState(Canvas canvas) {
        canvas.drawCircle(animCx, animCy, animRadius, paint);
    }

    @Override
    protected void drawInAnimation(Canvas canvas, float interpolation) {
        float deltaCx     = animCx - cx;
        float deltaCy     = animCy - cy;
        float deltaRadius = animRadius - radius;

        float toCx   = deltaCx * interpolation + cx;
        float toCy   = deltaCy * interpolation + cy;
        float toRadius = deltaRadius * interpolation + radius;

        setAlpha(alphaAnim, paint, interpolation);
        canvas.drawCircle(toCx, toCy, toRadius, paint);
    }
}
