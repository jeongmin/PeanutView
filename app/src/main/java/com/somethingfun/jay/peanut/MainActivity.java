package com.somethingfun.jay.peanut;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import com.jeongmin.peanutview.drawing.animatable.CircleAnimatable;
import com.jeongmin.peanutview.drawing.animatable.LineAnimatable;
import com.jeongmin.peanutview.drawing.shape.Circle;
import com.jeongmin.peanutview.drawing.shape.Line;
import com.jeongmin.peanutview.view.PeanutView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private PeanutView mPeanutView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.start_anim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rnd = new Random();
                @ColorInt int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

                int x = (int)(Math.random() * mPeanutView.getWidth());
                int y = (int)(Math.random() * mPeanutView.getHeight());
                int radius = (int)(Math.random() * 200);

                int duration = (int)(Math.random() * 2000);
                duration = (duration < 300) ? 300 : duration;

                int startDelay = (int)(Math.random() * 1000);
                makeRain(startDelay, x, y, radius, duration, color);
                mPeanutView.startAnimation();
            }
        });

        mPeanutView = findViewById(R.id.peanutView);
        mPeanutView.init();

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
                        makeRain(0, event.getX(), event.getY(), radius, duration, getRandomColor());
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private void makeRain(int startDelay, float x, float y, float radius, int duration, @ColorInt int color) {
        LineAnimatable lineAnimatable = new LineAnimatable(new Line(x, 0, x, 0),new Line(x, 0, x, y));
        lineAnimatable.setPaintColor(color);
        lineAnimatable.setAnimDuration(duration);
        lineAnimatable.setAlphaAnim(0.5f, 1);
        lineAnimatable.setDelay(startDelay);
        lineAnimatable.setInterpolator(new AccelerateInterpolator());

        LineAnimatable lineAnimatable2 = new LineAnimatable(new Line(x, 0, x, y),new Line(x, y, x, y));
        lineAnimatable2.setPaintColor(color);
        lineAnimatable2.setAnimDuration(duration / 2);
        lineAnimatable2.setDelay(duration + startDelay);
        lineAnimatable2.setInterpolator(new AccelerateInterpolator());

        CircleAnimatable circleAnimatable = new CircleAnimatable(new Circle(x, y, 0), new Circle(x, y, radius));
        circleAnimatable.setPaintColor(color);
        circleAnimatable.setAlphaAnim(0.3f, 1);
        circleAnimatable.setAnimDuration(duration);
        circleAnimatable.setDelay(duration + duration / 2 + startDelay);
        circleAnimatable.setInterpolator(new OvershootInterpolator());

        mPeanutView.addDrawingObject(lineAnimatable);
        mPeanutView.addDrawingObject(lineAnimatable2);
        mPeanutView.addDrawingObject(circleAnimatable);

        long start = System.currentTimeMillis();
        lineAnimatable.startAnimation(start);
        lineAnimatable2.startAnimation(start);
        circleAnimatable.startAnimation(start);
        mPeanutView.invalidate();
    }

    private @ColorInt int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

}
