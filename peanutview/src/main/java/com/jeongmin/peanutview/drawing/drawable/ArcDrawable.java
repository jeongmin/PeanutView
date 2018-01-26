package com.jeongmin.peanutview.drawing.drawable;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import com.jeongmin.peanutview.drawing.shape.Arc;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by jay on 17. 11. 11.
 */

public class ArcDrawable extends SelfDrawable {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            DIRECTION_RIGHT,
            DIRECTION_LEFT
    })
    public @interface Direction {}

    public static final int DIRECTION_RIGHT = 0;
    public static final int DIRECTION_LEFT  = 1;



    private Arc arcStart;
    private Arc arcEnd;
    private Arc intermediateArc;
    public boolean useCenter;
    public @Direction int direction = DIRECTION_RIGHT;

    public ArcDrawable(@NonNull Paint paint, @NonNull Arc arcStart, @NonNull Arc arcEnd) {
        this.arcStart = arcStart;
        this.arcEnd = arcEnd;
        this.paint = paint;
        init();
    }

    public ArcDrawable(@NonNull Paint paint, @NonNull Arc arcStart) {
        this.arcStart = arcStart;
        this.paint = paint;
        init();
    }

    private void init() {
        intermediateArc = new Arc();
    }

    @Override
    protected void drawInitialState(Canvas canvas) {
        canvas.drawArc(arcStart.oval, arcStart.startAngle, arcStart.sweepAngle, arcStart.useCenter, paint);
    }

    @Override
    protected void drawLastState(Canvas canvas) {
        if (arcEnd == null) {
            drawInitialState(canvas);
            return;
        }

        canvas.drawArc(arcEnd.oval, arcEnd.startAngle, arcEnd.sweepAngle, arcEnd.useCenter, paint);
    }

    @Override
    protected void drawInAnimation(Canvas canvas, float interpolation) {
        if (arcEnd == null) {
            drawInitialState(canvas);
            return;
        }

        setAlpha(alphaAnim, paint, interpolation);
        Arc.getIntermediateArc(arcStart, arcEnd, intermediateArc, interpolation);

        if (direction == DIRECTION_RIGHT) {
            canvas.drawArc(intermediateArc.oval, intermediateArc.startAngle, intermediateArc.sweepAngle, useCenter, paint);
        } else {
            canvas.drawArc(intermediateArc.oval, intermediateArc.startAngle, -intermediateArc.sweepAngle, useCenter, paint);
        }
    }
}
