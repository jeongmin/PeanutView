package com.somethingfun.jay.peanut;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import com.jeongmin.peanutview.drawing.drawable.SelfDrawable;
import com.jeongmin.peanutview.drawing.drawable.CircleDrawable;
import com.jeongmin.peanutview.drawing.drawable.LineDrawable;
import com.jeongmin.peanutview.drawing.shape.Circle;
import com.jeongmin.peanutview.drawing.shape.Line;
import com.jeongmin.peanutview.view.PeanutView;

import java.util.Random;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private PeanutView mPeanutView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.start_anim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPeanutView.startAnimation();
            }
        });

        findViewById(R.id.start_geo_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GeoActivity.class));
            }
        });



        mPeanutView = findViewById(R.id.peanutView);
        mPeanutView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        return true;
                    case MotionEvent.ACTION_UP:
                        int radius = (int)(Math.random() * 200);
                        radius = (radius < 100) ? 150 : radius;

                        int duration = (int)(Math.random() * 1000);
                        duration = (duration < 300) ? 300 : duration;
                        //ArrayList<SelfDrawable> rain = makeRain(0, event.getX(), event.getY(), radius, duration, getRandomColor());
                        ArrayList<SelfDrawable> rain = makeFireFlower(0, event.getX(), event.getY(), radius*3, duration, getRandomColor());
                        mPeanutView.addToStartLine(rain);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private ArrayList<SelfDrawable> makeRain(int startDelay, float x, float y, float radius, int duration, @ColorInt int color) {
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

    private ArrayList<SelfDrawable> makeFireFlower(int startDelay, float x, float y, float radius, int duration, @ColorInt int color) {
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


    private @ColorInt int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    private float getY(float a, float x1, float y1, float x2) {
        return ((x2 - x1) * a) + y1;
    }

}
