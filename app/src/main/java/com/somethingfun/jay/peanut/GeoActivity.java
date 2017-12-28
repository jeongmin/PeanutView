package com.somethingfun.jay.peanut;

import android.annotation.SuppressLint;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.jeongmin.peanutview.drawing.animatable.LineDrawable;
import com.jeongmin.peanutview.drawing.shape.Line;
import com.jeongmin.peanutview.util.MathUtilKt;
import com.jeongmin.peanutview.view.PeanutView;

public class GeoActivity extends AppCompatActivity {

    private PeanutView mPeanutView;
    private PointF point1;
    private PointF point2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo);

        mPeanutView = findViewById(R.id.peanutView);
        mPeanutView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (point1 == null) {
                            point1 = new PointF(event.getX(), event.getY());
                        } else {
                            point2 = new PointF(event.getX(), event.getY());
                            drawLine(point1, point2);
                            point1 = null;
                            point2 = null;
                        }
                        break;
                }

                return false;
            }
        });
    }

    private void drawLine(PointF point1, PointF point2) {
        LineDrawable lineDrawable = new LineDrawable(new Line(point1.x, point1.y, point1.x, point1.y), new Line(point1.x, point1.y, point2.x, point2.y));
        lineDrawable.setAnimDuration(1000);
        lineDrawable.setRetainAfterAnimation(true);
        lineDrawable.setInterpolator(new LinearInterpolator());
        mPeanutView.addAnimatable(lineDrawable);
        mPeanutView.addToStartLine(lineDrawable);
        Log.d("jm.lee", "haha : " + MathUtilKt.getLinearEquation(point1, point2));
    }
}
