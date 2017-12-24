package com.somethingfun.jay.peanut.drawing;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;


/**
 * Created by jay on 17. 11. 10.
 */

public class JPath extends DrawableObject {

    private Path  path;
    private PointF points[];

    public JPath(PointF ... points) {
        this.points = points;
        this.path = new Path();

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(0xff101010);
        paint.setStrokeWidth(10);

        if (points[0] != null) {
            this.path.moveTo(points[0].x, points[0].y);
        }

        for (int i = 1; i < points.length; i++) {
            this.path.lineTo(points[i].x, points[i].y);
        }

    }

    @Override
    protected void drawInitialState(Canvas canvas) {
        canvas.drawPath(path, paint);
    }

    @Override
    protected void drawLastState(Canvas canvas) {
        canvas.drawPath(path, paint);
    }

    @Override
    protected void drawInAnimation(Canvas canvas, float interpolation) {
        canvas.drawPath(path, paint);
        /*
        float deltaX = stopX - startX;
        float deltaY = stopY - startY;
        float toX = deltaX * interpolation + startX;
        float toY = deltaY * interpolation + startY;
        canvas.drawLine(startX, startY, toX, toY, paint);
        */
    }
}
