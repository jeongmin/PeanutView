package com.jeongmin.peanutview.drawing;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.jeongmin.peanutview.drawing.drawable.SelfDrawable;

/**
 * Created by jay on 17. 11. 11.
 */

public class Square extends SelfDrawable {

    private RectF start;
    private RectF end;

    public Square(RectF start, RectF end) {
        this.start = start;
        this.end = end;

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0xff101010);
        paint.setStrokeWidth(10);
    }

    @Override
    protected void drawInitialState(Canvas canvas) {
        canvas.drawRect(start, paint);
    }

    @Override
    protected void drawLastState(Canvas canvas) {
        canvas.drawRect(end, paint);
    }

    @Override
    protected void drawInAnimation(Canvas canvas, float interpolation) {
        float deltaLeft = end.left - start.left;
        float deltaTop  = end.top - start.top;
        float deltaRight = end.right - start.right;
        float deltaBottom = end.bottom - start.bottom;

        RectF toRect  = new RectF();
        toRect.left   = deltaLeft * interpolation + start.left;
        toRect.top    = deltaTop * interpolation + start.top;
        toRect.right  = deltaRight * interpolation + start.right;
        toRect.bottom = deltaBottom * interpolation + start.bottom;

        setAlpha(alphaAnim, paint, interpolation);
        canvas.drawRect(toRect, paint);
    }
}
