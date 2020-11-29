package com.jeongmin.peanutview.drawing.drawable;

import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;

import com.jeongmin.peanutview.drawing.shape.Line;


/**
 * Created by jay on 17. 11. 10.
 */

public class LineDrawable extends SelfDrawable {

    private Line line1;
    private Line line2;

    public LineDrawable(@NonNull Paint paint, Line line1, Line line2) {
        this.line1 = line1;
        this.line2 = line2;
        setPaint(paint);
    }

    @Override
    protected void drawInitialState(Canvas canvas) {
        canvas.drawLine(line1.getX1(), line1.getY1(), line1.getX2(), line1.getY2(), paint);
        Log.d("jm.lee", "drawInitialState");
    }

    @Override
    protected void drawLastState(Canvas canvas) {
        if (alphaAnim != null) {
            paint.setAlpha((int)(alphaAnim.to * 255));
        }
        canvas.drawLine(line2.getX1(), line2.getY1(), line2.getX2(), line2.getY2(), paint);
        Log.d("jm.lee", "drawLastState");
    }

    @Override
    protected void drawInAnimation(Canvas canvas, float interpolation) {
        float deltaX1 = line2.getX1() - line1.getX1();
        float deltaX2 = line2.getX2() - line1.getX2();
        float deltaY1 = line2.getY1() - line1.getY1();
        float deltaY2 = line2.getY2() - line1.getY2();

        float toX1 = deltaX1 * interpolation + line1.getX1();
        float toX2 = deltaX2 * interpolation + line1.getX2();
        float toY1 = deltaY1 * interpolation + line1.getY1();
        float toY2 = deltaY2 * interpolation + line1.getY2();

        setAlpha(alphaAnim, paint, interpolation);
        canvas.drawLine(toX1, toY1, toX2, toY2, paint);
        Log.d("jm.lee", "drawInAnimation");
    }
}
