package com.somethingfun.jay.peanut;

import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import com.somethingfun.jay.peanut.drawing.Circle;
import com.somethingfun.jay.peanut.drawing.Line;
import com.somethingfun.jay.peanut.drawing.Square;

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

        mPeanutView = findViewById(R.id.peanutView);
        mPeanutView.init();

        Line line = new Line(0, 0, 500, 500);
        line.setInterpolator(new AccelerateInterpolator());
        line.setAnimateDuration(1000);

        Line line2 = new Line(500, 0, 0, 500);
        line2.setAnimateDuration(600);
        line2.repeatAnim(true);

        Square square = new Square(
                new RectF(500, 500, 500, 500),
                new RectF(100, 500, 200, 1200)
        );
        square.setAnimateDuration(2000);

        Square square2 = new Square(
                new RectF(500, 0, 550, 50),
                new RectF(500, 1200, 550, 1250)
        );
        square2.setAnimateDuration(300);
        square2.setInterpolator(new AccelerateInterpolator());
        square2.setAlphaAnim(0, 1);
        square2.repeatAnim(true);
        square2.setRetainAfterAnimation(false);


        Circle circle1 = new Circle(500, 500, 100);
        circle1.setAnimation(500, 500, 300);
        circle1.setInterpolator(new OvershootInterpolator());
        circle1.repeatAnim(true);
        circle1.setAlphaAnim(0, 1);
        circle1.setRetainAfterAnimation(true);
        circle1.setAnimateDuration(3000);

        mPeanutView.addShape(line);
        mPeanutView.addShape(line2);
        mPeanutView.addShape(square);
        mPeanutView.addShape(square2);
        mPeanutView.addShape(circle1);
    }
}
