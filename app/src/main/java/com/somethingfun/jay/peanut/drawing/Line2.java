package com.somethingfun.jay.peanut.drawing;

import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.Log;

/**
 * Created by jay on 17. 11. 10.
 */

public class Line2 extends DrawableObject {

    private Path  path;
    private float startX;
    private float startY;
    private float stopX;
    private float stopY;


    public Line2(float startX, float startY, float stopX, float stopY) {
        this.startX = startX;
        this.startY = startY;
        this.stopX  = stopX;
        this.stopY  = stopY;

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(0xff101010);
        paint.setStrokeWidth(10);

        float centerX = (this.stopX - this.startX) / 2 + this.startX;
        float centerY = (this.stopY - this.startY) / 2 + this.startY;

        float centerLeftX = (centerX - this.startX) / 2 + this.startX;
        float centerLeftY = (centerY - this.startY) / 2 + this.startY;

        float centerRightX = (this.stopX - centerX) / 2 + centerX;
        float centerRightY = (this.stopY - centerY) / 2 + centerY;
        Log.d("jm.lee", String.format("startX %f, startY %f, centerLeftX %f, centerLeftY %f, centerRightX %f, centerRightY %f, stopX %f, stopY %f", this.startX, this.startY, centerLeftX, centerLeftY, centerRightX, centerRightY, this.stopX, this.stopY));
        path = new Path();
        path.moveTo(this.startX, this.startY);
        path.cubicTo(centerLeftX, centerRightY, centerRightX, centerLeftY, this.stopX, this.stopY);
        
    }

    @Override
    protected void drawInitialState(Canvas canvas) {
        canvas.drawPath(path, paint);
        Log.d("jm.lee", "drawInitialState.." + path.toString());
    }

    @Override
    protected void drawLastState(Canvas canvas) {
        canvas.drawPath(path, paint);
        Log.d("jm.lee", "drawLastState.." + path.toString());
    }

    @Override
    protected void drawInAnimation(Canvas canvas, float interpolation) {
        Log.d("jm.lee", "drawInAnimation.." + path.toString());
        canvas.drawPath(path, paint);
        /*
        float deltaX = stopX - startX;
        float deltaY = stopY - startY;
        float toX = deltaX * interpolation + startX;
        float toY = deltaY * interpolation + startY;
        //path.quadTo(startX, startY, toX, toY);
        path.lineTo(toX, toY);
        canvas.drawPath(path, paint);
        */
    }
}
