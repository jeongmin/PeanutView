package com.somethingfun.jay.peanut;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import com.somethingfun.jay.peanut.drawing.CircleDrawable;
import com.somethingfun.jay.peanut.drawing.Line;
import com.somethingfun.jay.peanut.drawing.LineDrawable;

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

                int duration = (int)(Math.random() * 1000);
                duration = (duration < 300) ? 300 : duration;
                makeRain(x, y, radius, duration, color);
                mPeanutView.startAnimation();
            }
        });

        mPeanutView = findViewById(R.id.peanutView);
        mPeanutView.init();
    }

    private void makeRain(float x, float y, float radius, int duration, @ColorInt int color) {
        LineDrawable lineDrawable = new LineDrawable(new Line(x, 0, x, 0),new Line(x, 0, x, y));
        lineDrawable.setPaintColor(color);
        lineDrawable.setAnimDuration(duration);
        lineDrawable.setAlphaAnim(0.5f, 1);
        lineDrawable.setRetainAfterAnimation(false);
        lineDrawable.setInterpolator(new AccelerateInterpolator());

        LineDrawable lineDrawable2 = new LineDrawable(new Line(x, 0, x, y),new Line(x, y, x, y));
        lineDrawable2.setPaintColor(color);
        lineDrawable2.setAnimDuration(duration / 2);
        lineDrawable2.setRetainAfterAnimation(false);
        lineDrawable2.setDelay(duration);
        lineDrawable2.setInterpolator(new AccelerateInterpolator());

        CircleDrawable circleDrawable = new CircleDrawable(x, y, 0);
        circleDrawable.setPaintColor(color);
        circleDrawable.setAnimation(x, y, radius);
        circleDrawable.setAlphaAnim(0.3f, 1);
        circleDrawable.setAnimDuration(duration);
        circleDrawable.setDelay(duration + duration / 2);
        circleDrawable.setRetainAfterAnimation(false);
        circleDrawable.setInterpolator(new OvershootInterpolator());



        mPeanutView.addDrawingObject(lineDrawable);
        mPeanutView.addDrawingObject(lineDrawable2);
        mPeanutView.addDrawingObject(circleDrawable);
    }

}
