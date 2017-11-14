package com.somethingfun.jay.peanut.drawing;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.somethingfun.jay.peanut.drawing.DrawableObject;

/**
 * Created by jay on 17. 11. 10.
 */

public class Line extends DrawableObject {

    private float startX;
    private float startY;
    private float stopX;
    private float stopY;


    public Line(float startX, float startY, float stopX, float stopY) {
        this.startX = startX;
        this.startY = startY;
        this.stopX  = stopX;
        this.stopY  = stopY;

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0xff101010);
        paint.setStrokeWidth(10);
    }

    @Override
    protected void drawInitialState(Canvas canvas) {
        canvas.drawLine(startX, startY, stopX, stopY, paint);
    }

    @Override
    protected void drawLastState(Canvas canvas) {
        canvas.drawLine(startX, startY, stopX, stopY, paint);
    }

    @Override
    protected void drawInAnimation(Canvas canvas, float interpolation) {
        float deltaX = stopX - startX;
        float deltaY = stopY - startY;
        float toX = deltaX * interpolation + startX;
        float toY = deltaY * interpolation + startY;
        canvas.drawLine(startX, startY, toX, toY, paint);
    }
}
