package com.jeongmin.peanutview.drawing.drawable;

import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.NonNull;

import com.jeongmin.peanutview.drawing.shape.Circle;

/**
 * Created by jay on 17. 11. 11.
 */

public class CircleDrawable extends SelfDrawable {

    private Circle circleStart;
    private Circle circleEnd;

    public CircleDrawable(@NonNull Paint paint, @NonNull Circle circleStart, @NonNull Circle circleEnd) {
        this.circleStart = circleStart;
        this.circleEnd = circleEnd;
        this.paint = paint;
    }

    public CircleDrawable(@NonNull Paint paint, @NonNull Circle circleStart) {
        this.circleStart = circleStart;
        this.paint = paint;
    }

    @Override
    protected void drawInitialState(Canvas canvas) {
        canvas.drawCircle(circleStart.getCx(), circleStart.getCy(), circleStart.getRadius(), paint);
    }

    @Override
    protected void drawLastState(Canvas canvas) {
        if (circleEnd == null) {
            drawInitialState(canvas);
            return;
        }

        canvas.drawCircle(circleEnd.getCx(), circleEnd.getCy(), circleEnd.getRadius(), paint);
    }

    @Override
    protected void drawInAnimation(Canvas canvas, float interpolation) {
        if (circleEnd == null) {
            drawInitialState(canvas);
            return;
        }

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
