package com.somethingfun.jay.peanut.drawing.animatable;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;

import com.somethingfun.jay.peanut.drawing.shape.Circle;

/**
 * Created by jay on 17. 11. 11.
 */

public class CircleAnimatable extends Animatable {

    private Circle circleStart;
    private Circle circleEnd;

    public CircleAnimatable(@NonNull Circle circleStart, @NonNull Circle circleEnd) {
        this.circleStart = circleStart;
        this.circleEnd = circleEnd;

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0xff101010);
        paint.setStrokeWidth(10);
    }

    @Override
    protected void drawInitialState(Canvas canvas) {
        canvas.drawCircle(circleStart.getCx(), circleStart.getCy(), circleStart.getRadius(), paint);
    }

    @Override
    protected void drawLastState(Canvas canvas) {
        canvas.drawCircle(circleEnd.getCx(), circleEnd.getCy(), circleEnd.getRadius(), paint);
    }

    @Override
    protected void drawInAnimation(Canvas canvas, float interpolation) {
        float deltaCx     = circleEnd.getCx() - circleStart.getCx();
        float deltaCy     = circleEnd.getCy() - circleStart.getCy();
        float deltaRadius = circleEnd.getRadius() - circleStart.getRadius();

        float toCx   = deltaCx * interpolation + circleStart.getCx();
        float toCy   = deltaCy * interpolation + circleStart.getCy();
        float toRadius = deltaRadius * interpolation + circleStart.getRadius();

        setAlpha(alphaAnim, paint, interpolation);
        canvas.drawCircle(toCx, toCy, toRadius, paint);
    }
}
