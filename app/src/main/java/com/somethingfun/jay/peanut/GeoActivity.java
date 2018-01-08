package com.somethingfun.jay.peanut;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.RadioGroup;

import com.jeongmin.peanutview.drawing.drawable.CircleDrawable;
import com.jeongmin.peanutview.drawing.drawable.LineDrawable;
import com.jeongmin.peanutview.drawing.drawable.SelfDrawable;
import com.jeongmin.peanutview.drawing.shape.Circle;
import com.jeongmin.peanutview.drawing.shape.Line;
import com.jeongmin.peanutview.view.PeanutView;

import java.util.ArrayList;
import java.util.Random;

public class GeoActivity extends AppCompatActivity {

    private PeanutView mPeanutView;
    private PointF point1;
    private PointF point2;

    private int checkedId = R.id.radio_rain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo);

        findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });

        ((RadioGroup)findViewById(R.id.radiogroup)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                GeoActivity.this.checkedId = checkedId;
            }
        });

        mPeanutView = findViewById(R.id.peanutView);
        mPeanutView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        switch (checkedId) {
                            case R.id.radio_circle:
                                handleTouchOnCircleMode(event, 100,300);
                                break;
                            case R.id.radio_line:
                                handleTouchOnLineMode(event);
                                break;
                            case R.id.radio_rain:
                                int radius = (int)(Math.random() * 200);
                                radius = (radius < 100) ? 150 : radius;

                                int duration = (int)(Math.random() * 1000);
                                duration = (duration < 300) ? 300 : duration;
                                ArrayList<SelfDrawable> rain = handleTouchOnRainMode(0, event.getX(), event.getY(), radius, duration, getRandomColor());
                                mPeanutView.startImmediate(rain);
                                break;
                        }
                        break;
                }

                return false;
            }
        });
    }

    private void drawLine(PointF point1, PointF point2) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(0xff101010);
        paint.setStrokeWidth(10);

        LineDrawable lineDrawable = new LineDrawable(paint, new Line(point1.x, point1.y, point1.x, point1.y), new Line(point1.x, point1.y, point2.x, point2.y));
        lineDrawable.setAnimDuration(1000);
        lineDrawable.setRetainAfterAnimation(true);
        lineDrawable.setInterpolator(new LinearInterpolator());
        mPeanutView.addAnimatable(lineDrawable);
        mPeanutView.startImmediate(lineDrawable);
    }

    private void drawDot(float x, float y) {
        CircleDrawable circle = new CircleDrawable(makePaint(0xff000000), new Circle(x, y, 15));
        circle.setRetainAfterAnimation(true);
        mPeanutView.startImmediate(circle);
    }

    private void handleTouchOnLineMode(MotionEvent event) {
        if (point1 == null) {
            point1 = new PointF(event.getX(), event.getY());
        } else {
            point2 = new PointF(event.getX(), event.getY());
            drawLine(point1, point2);
            point1 = null;
            point2 = null;
        }
        drawDot(event.getX(), event.getY());
    }

    private void handleTouchOnCircleMode(MotionEvent event, int radius, int duration) {
        CircleDrawable circle1 = new CircleDrawable(makePaint(getRandomColor()), new Circle(event.getX(), event.getY(), 0), new Circle(event.getX(), event.getY(), radius - 60));
        circle1.setRetainAfterAnimation(false);
        circle1.setAnimDuration(duration);
        circle1.setAlphaAnim(1, 0);
        circle1.setInterpolator(new LinearInterpolator());
        circle1.repeatAnim(true);

        CircleDrawable circle2 = new CircleDrawable(makePaint(getRandomColor()), new Circle(event.getX(), event.getY(), 0), new Circle(event.getX(), event.getY(), radius - 30));
        circle2.setRetainAfterAnimation(false);
        circle2.setAnimDuration(duration);
        circle2.setDelay(100);
        circle2.setAlphaAnim(1, 0);
        circle2.setInterpolator(new LinearInterpolator());
        circle2.repeatAnim(true);

        CircleDrawable circle3 = new CircleDrawable(makePaint(getRandomColor()), new Circle(event.getX(), event.getY(), 0), new Circle(event.getX(), event.getY(), radius));
        circle3.setRetainAfterAnimation(false);
        circle3.setAnimDuration(duration);
        circle3.setDelay(200);
        circle3.setAlphaAnim(1, 0);
        circle3.setInterpolator(new LinearInterpolator());
        circle3.repeatAnim(true);


        mPeanutView.startImmediate(circle1);
        mPeanutView.startImmediate(circle2);
        mPeanutView.startImmediate(circle3);
    }

    private @ColorInt
    int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    private ArrayList<SelfDrawable> handleTouchOnRainMode(int startDelay, float x, float y, float radius, int duration, @ColorInt int color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);
        paint.setStrokeWidth(10);

        LineDrawable lineDrawable = new LineDrawable(paint, new Line(x, 0, x, 0),new Line(x, 0, x, y));
        lineDrawable.setAnimDuration(duration);
        lineDrawable.setAlphaAnim(0.5f, 1);
        lineDrawable.setDelay(startDelay);
        lineDrawable.setInterpolator(new AccelerateInterpolator());

        LineDrawable lineDrawable2 = new LineDrawable(paint, new Line(x, 0, x, y),new Line(x, y, x, y));
        lineDrawable2.setPaintColor(color);
        lineDrawable2.setAnimDuration(duration / 2);
        lineDrawable2.setDelay(duration + startDelay);
        lineDrawable2.setInterpolator(new AccelerateInterpolator());

        CircleDrawable circleDrawable = new CircleDrawable(makePaint(getRandomColor()), new Circle(x, y, 0), new Circle(x, y, radius));
        circleDrawable.setPaintColor(color);
        circleDrawable.setAlphaAnim(0.3f, 1);
        circleDrawable.setAnimDuration(duration);
        circleDrawable.setDelay(duration + duration / 2 + startDelay);
        circleDrawable.setInterpolator(new OvershootInterpolator());

        ArrayList<SelfDrawable> rain = new ArrayList<>();
        rain.add(lineDrawable);
        rain.add(lineDrawable2);
        rain.add(circleDrawable);
        return rain;
    }

    private void clear() {
        mPeanutView.clear();
        point1 = null;
        point2 = null;
    }

    private float getY(float a, float x1, float y1, float x2) {
        return ((x2 - x1) * a) + y1;
    }

    @NonNull
    private Paint makePaint(@ColorInt int colorInt) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(colorInt);
        paint.setStrokeWidth(10);
        return paint;
    }
}
