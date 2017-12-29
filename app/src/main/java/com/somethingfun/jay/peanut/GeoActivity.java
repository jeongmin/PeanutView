package com.somethingfun.jay.peanut;

import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.jeongmin.peanutview.util.MathUtilKt;
import com.jeongmin.peanutview.view.PeanutView;

import java.util.ArrayList;
import java.util.Random;

public class GeoActivity extends AppCompatActivity {

    private PeanutView mPeanutView;
    private PointF point1;
    private PointF point2;

    private int checkedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo);

        ((RadioGroup)findViewById(R.id.radiogroup)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                GeoActivity.this.checkedId = checkedId;
                clear();
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
                                mPeanutView.addToStartLine(rain);
                                break;
                            case R.id.radio_fire:
                                handleFire(event);
                                break;
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
    }

    private void drawCircle(float x, float y) {
        CircleDrawable circle = new CircleDrawable(new Circle(x, y, 15));
        circle.setRetainAfterAnimation(true);
        mPeanutView.addToStartLine(circle);
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
        drawCircle(event.getX(), event.getY());
    }

    private void handleTouchOnCircleMode(MotionEvent event, int radius, int duration) {
        CircleDrawable circle1 = new CircleDrawable(new Circle(event.getX(), event.getY(), 0), new Circle(event.getX(), event.getY(), radius - 60));
        circle1.setRetainAfterAnimation(false);
        circle1.setAnimDuration(duration);
        circle1.setAlphaAnim(1, 0);
        circle1.setInterpolator(new LinearInterpolator());
        circle1.setPaintColor(getRandomColor());
        circle1.repeatAnim(true);

        CircleDrawable circle2 = new CircleDrawable(new Circle(event.getX(), event.getY(), 0), new Circle(event.getX(), event.getY(), radius - 30));
        circle2.setRetainAfterAnimation(false);
        circle2.setAnimDuration(duration);
        circle2.setDelay(100);
        circle2.setAlphaAnim(1, 0);
        circle2.setInterpolator(new LinearInterpolator());
        circle2.setPaintColor(getRandomColor());
        circle2.repeatAnim(true);

        CircleDrawable circle3 = new CircleDrawable(new Circle(event.getX(), event.getY(), 0), new Circle(event.getX(), event.getY(), radius));
        circle3.setRetainAfterAnimation(false);
        circle3.setAnimDuration(duration);
        circle3.setDelay(200);
        circle3.setAlphaAnim(1, 0);
        circle3.setInterpolator(new LinearInterpolator());
        circle3.setPaintColor(getRandomColor());
        circle3.repeatAnim(true);


        mPeanutView.addToStartLine(circle1);
        mPeanutView.addToStartLine(circle2);
        mPeanutView.addToStartLine(circle3);
    }

    private @ColorInt
    int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    private ArrayList<SelfDrawable> handleTouchOnRainMode(int startDelay, float x, float y, float radius, int duration, @ColorInt int color) {
        LineDrawable lineDrawable = new LineDrawable(new Line(x, 0, x, 0),new Line(x, 0, x, y));
        lineDrawable.setPaintColor(color);
        lineDrawable.setAnimDuration(duration);
        lineDrawable.setAlphaAnim(0.5f, 1);
        lineDrawable.setDelay(startDelay);
        lineDrawable.setInterpolator(new AccelerateInterpolator());

        LineDrawable lineDrawable2 = new LineDrawable(new Line(x, 0, x, y),new Line(x, y, x, y));
        lineDrawable2.setPaintColor(color);
        lineDrawable2.setAnimDuration(duration / 2);
        lineDrawable2.setDelay(duration + startDelay);
        lineDrawable2.setInterpolator(new AccelerateInterpolator());

        CircleDrawable circleDrawable = new CircleDrawable(new Circle(x, y, 0), new Circle(x, y, radius));
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

    private ArrayList<SelfDrawable> handleTouchOnFireMode(int startDelay, float x, float y, float radius, int duration, @ColorInt int color) {
        long animTime;
        LineDrawable lineDrawable = new LineDrawable(new Line(x, 0, x, 0),new Line(x, 0, x, y));
        lineDrawable.setPaintColor(color);
        lineDrawable.setAnimDuration(duration);
        lineDrawable.setAlphaAnim(0.5f, 1);
        lineDrawable.setDelay(startDelay);
        lineDrawable.setInterpolator(new AccelerateInterpolator());

        LineDrawable lineDrawable2 = new LineDrawable(new Line(x, 0, x, y),new Line(x, y, x, y));
        lineDrawable2.setPaintColor(color);
        lineDrawable2.setAnimDuration(duration / 2);
        lineDrawable2.setDelay(duration + startDelay);
        lineDrawable2.setInterpolator(new AccelerateInterpolator());
        animTime = lineDrawable2.getDelay() + lineDrawable2.getDuration();

        int halfLen = (int)(radius / 2);
        LineDrawable line1 = new LineDrawable(new Line(x, y, x, y),new Line(x - halfLen, y, x + halfLen, y));
        line1.setPaintColor(getRandomColor());
        line1.setAnimDuration(duration);
        line1.setDelay(animTime);
        line1.setInterpolator(new AccelerateInterpolator());

        LineDrawable line2 = new LineDrawable(new Line(x, y, x, y),new Line(x, y - halfLen, x, y + halfLen));
        line2.setPaintColor(getRandomColor());
        line2.setAnimDuration(duration);
        line2.setDelay(animTime);
        line2.setInterpolator(new AccelerateInterpolator());

        float leftX = x - halfLen;
        float leftY = getY(1, x, y, leftX);
        float rightX = x + halfLen;
        float rightY = getY(1, x, y, rightX);
        LineDrawable line3 = new LineDrawable(new Line(x, y, x, y),new Line(leftX, leftY, rightX, rightY));
        line3.setPaintColor(getRandomColor());
        line3.setAnimDuration(duration);
        line3.setDelay(animTime);
        line3.setInterpolator(new AccelerateInterpolator());


        leftY = getY(-1, x, y, leftX);
        rightY = getY(-1, x, y, rightX);
        LineDrawable line4 = new LineDrawable(new Line(x, y, x, y),new Line(leftX, leftY, rightX, rightY));
        line4.setPaintColor(getRandomColor());
        line4.setAnimDuration(duration);
        line4.setDelay(animTime);
        line4.setInterpolator(new AccelerateInterpolator());

        leftY = getY(0.5f, x, y, leftX);
        rightY = getY(0.5f, x, y, rightX);
        LineDrawable line5 = new LineDrawable(new Line(x, y, x, y),new Line(leftX, leftY, rightX, rightY));
        line5.setPaintColor(getRandomColor());
        line5.setAnimDuration(duration);
        line5.setDelay(animTime);
        line5.setInterpolator(new AccelerateInterpolator());

        leftY = getY(-0.5f, x, y, leftX);
        rightY = getY(-0.5f, x, y, rightX);
        LineDrawable line6 = new LineDrawable(new Line(x, y, x, y),new Line(leftX, leftY, rightX, rightY));
        line6.setPaintColor(getRandomColor());
        line6.setAnimDuration(duration);
        line6.setDelay(animTime);
        line6.setInterpolator(new AccelerateInterpolator());

        leftY = getY(2f, x, y, leftX);
        rightY = getY(2f, x, y, rightX);
        LineDrawable line7 = new LineDrawable(new Line(x, y, x, y),new Line(leftX, leftY, rightX, rightY));
        line7.setPaintColor(getRandomColor());
        line7.setAnimDuration(duration);
        line7.setDelay(animTime);
        line7.setInterpolator(new AccelerateInterpolator());

        leftY = getY(-2f, x, y, leftX);
        rightY = getY(-2f, x, y, rightX);
        LineDrawable line8 = new LineDrawable(new Line(x, y, x, y),new Line(leftX, leftY, rightX, rightY));
        line8.setPaintColor(getRandomColor());
        line8.setAnimDuration(duration);
        line8.setDelay(animTime);
        line8.setInterpolator(new AccelerateInterpolator());


        ArrayList<SelfDrawable> rain = new ArrayList<>();
        rain.add(lineDrawable);
        rain.add(lineDrawable2);
        rain.add(line1);
        rain.add(line2);
        rain.add(line3);
        rain.add(line4);

//        rain.add(line5);
//        rain.add(line6);
//        rain.add(line7);
//        rain.add(line8);

        return rain;
    }

    private float getY(float a, float x1, float y1, float x2) {
        return ((x2 - x1) * a) + y1;
    }

    private void handleFire(MotionEvent event) {
        int radius = (int)(Math.random() * 200);
        radius = (radius < 100) ? 150 : radius;

        int duration = (int)(Math.random() * 1000);
        duration = (duration < 300) ? 300 : duration;
        ArrayList<SelfDrawable> rain = handleTouchOnFireMode(0, event.getX(), event.getY(), radius, duration, getRandomColor());
        mPeanutView.addToStartLine(rain);
    }
}
