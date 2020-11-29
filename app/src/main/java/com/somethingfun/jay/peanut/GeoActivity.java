package com.somethingfun.jay.peanut;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.RadioGroup;

import com.jeongmin.peanutview.drawing.drawable.ArcDrawable;
import com.jeongmin.peanutview.drawing.drawable.CircleDrawable;
import com.jeongmin.peanutview.drawing.drawable.LineDrawable;
import com.jeongmin.peanutview.drawing.drawable.SelfDrawable;
import com.jeongmin.peanutview.drawing.event.OnAnimationEnd;
import com.jeongmin.peanutview.drawing.shape.Arc;
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

    @SuppressLint("ClickableViewAccessibility")
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
                                ArrayList<SelfDrawable> rain = handleTouchOnRainMode(0, event.getX(), event.getY(), getRandomRadius(), getRandomDuration(), getRandomColor());
                                mPeanutView.startImmediate(rain);
                                break;
                            case R.id.radio_robbi:
                                ArrayList<SelfDrawable> robbi = handleTouchOnRobbiMode(0, event.getX(), event.getY(), getRandomRadius(), getRandomDuration(), getRandomColor());
                                mPeanutView.startImmediate(robbi);
                                break;
                            case R.id.radio_wav:
                                ArrayList<SelfDrawable> wav = handleTouchOnWAV(event.getX(), event.getY(), getRandomDuration(), getRandomColor());
                                mPeanutView.startImmediate(wav);
                                break;
                            case R.id.radio_oval:
                                handleTouchOnOvalMode(event, 100, 3000);
                                break;
                            case R.id.radio_fractal_tree:
                                handleTouchOnFactalMode(event, 90, 100);
                                break;
                            default:
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

    private void handleTouchOnOvalMode(MotionEvent event, int radius, int duration) {
        Arc start = new Arc();
        Arc end   = new Arc();

        start.oval = new RectF(event.getX() - radius, event.getY() - radius, event.getX() + radius, event.getY() + radius);
        start.startAngle = 0;
        start.sweepAngle = 0;
        start.useCenter = true;

        end.oval = new RectF(event.getX() - radius, event.getY() - radius, event.getX() + radius, event.getY() + radius);
        end.startAngle = 0;
        end.sweepAngle = 360;
        end.useCenter = true;


        ArcDrawable oval = new ArcDrawable(makeOvalPaint(getRandomColor()), start, end);
        if (oval.getPaint().getStyle() == Paint.Style.STROKE) {
            oval.useCenter = false;
            oval.direction = ArcDrawable.DIRECTION_RIGHT;
        } else {
            oval.useCenter = true;
            oval.direction = ArcDrawable.DIRECTION_LEFT;
        }
        oval.setAnimDuration(duration);


        oval.setInterpolator(new LinearInterpolator());

        mPeanutView.startImmediate(oval);
    }


    private ArrayList<SelfDrawable> handleTouchOnRobbiMode(int startDelay, float x, float y, final float radius, final int duration, @ColorInt int color) {
        final Paint paintCircle = makePaint(color);

        final Circle circleFirst = new Circle(x, y, radius);
        final CircleDrawable circleDrawable = new CircleDrawable(paintCircle, new Circle(x, y, 0), circleFirst);
        circleDrawable.setAlphaAnim(0.3f, 1);
        circleDrawable.setAnimDuration(duration);
        circleDrawable.setDelay(startDelay);
        circleDrawable.setRetainAfterAnimation(true);
        circleDrawable.setInterpolator(new OvershootInterpolator(3.0f));


        Paint paintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setColor(color);
        paintLine.setStrokeWidth(10);

        float lineY = y - radius;
        LineDrawable lineDrawable = new LineDrawable(paintLine, new Line(x, 0, x, 0),new Line(x, 0, x, lineY));
        lineDrawable.setAnimDuration(duration);
        lineDrawable.setAlphaAnim(0.5f, 1);
        lineDrawable.setDelay(circleDrawable.getDelay() + circleDrawable.getDuration());
        lineDrawable.setInterpolator(new AccelerateInterpolator());

        LineDrawable lineDrawable2 = new LineDrawable(paintLine, new Line(x, 0, x, lineY),new Line(x, lineY, x, lineY));
        lineDrawable2.setPaintColor(color);
        lineDrawable2.setAnimDuration(duration / 2);
        lineDrawable2.setDelay(lineDrawable.getDelay() + lineDrawable.getDuration() );
        lineDrawable2.setInterpolator(new AccelerateInterpolator());
        lineDrawable2.setOnAnimationEndListener(new OnAnimationEnd() {
            @Override
            public void onAnimationEnd() {
                mPeanutView.removeAnimatable(circleDrawable);

                CircleDrawable newCircleDrawable = new CircleDrawable(paintCircle,  circleFirst, new Circle(circleFirst.getCx(), mPeanutView.getHeight() - radius, radius));
                newCircleDrawable.setAnimDuration(duration);
                newCircleDrawable.setInterpolator(new BounceInterpolator());
                newCircleDrawable.setRetainAfterAnimation(true);
                mPeanutView.startImmediate(newCircleDrawable);

                mPeanutView.invalidate();
            }
        });


        ArrayList<SelfDrawable> rain = new ArrayList<>();
        rain.add(circleDrawable);
        rain.add(lineDrawable);
        rain.add(lineDrawable2);

        return rain;
    }

    private ArrayList<SelfDrawable> handleTouchOnWAV(float x, float y, final int duration, @ColorInt int color) {


        float left = x - 140;
        float right = x + 100;
        float top = y - 50;
        float bottom = y + 50;



        float distanceW = 34;
        int wColor = getRandomColor();
        final Paint wPaint = makePaint(getRandomColor());
        LineDrawable line1 = new LineDrawable(wPaint, new Line(left, top, left, top), new Line(left, top, left + distanceW, bottom));
        line1.setInterpolator(new LinearInterpolator());
        line1.setPaint(wPaint);
        line1.setAnimDuration(duration);
        line1.setRetainAfterAnimation(true);

        LineDrawable line2 = new LineDrawable(wPaint, new Line(left + distanceW, bottom, left + distanceW, bottom), new Line(left + distanceW, bottom, left + distanceW * 2, top));
        line2.setInterpolator(new LinearInterpolator());
        line2.setPaint(wPaint);
        line2.setAnimDuration(duration);
        line2.setRetainAfterAnimation(true);


        LineDrawable line3 = new LineDrawable(wPaint, new Line(left + distanceW * 2, top, left + distanceW * 2, top), new Line(left + distanceW * 2, top, left + distanceW * 3, bottom));
        line3.setInterpolator(new LinearInterpolator());
        line3.setPaint(wPaint);
        line3.setPaintColor(wColor);
        line3.setAnimDuration(duration);
        line3.setRetainAfterAnimation(true);

        LineDrawable line4 = new LineDrawable(wPaint, new Line(left + distanceW * 3, bottom, left + distanceW * 3, bottom), new Line(left + distanceW * 3, bottom, left + distanceW * 4, top));
        line4.setInterpolator(new LinearInterpolator());
        line4.setPaint(wPaint);
        line4.setPaintColor(wColor);
        line4.setAnimDuration(duration);
        line4.setRetainAfterAnimation(true);

        float topLineY = top - 20;
        float aStartX = left + distanceW * 4 + 30;
        Paint topLinePaint = makePaint(getRandomColor());
        LineDrawable line5 = new LineDrawable(topLinePaint, new Line(aStartX, topLineY, aStartX, topLineY), new Line(aStartX, topLineY, aStartX + distanceW * 2, topLineY));
        line5.setInterpolator(new LinearInterpolator());
        line5.setPaint(topLinePaint);
        line5.setAnimDuration(duration);
        line5.setRetainAfterAnimation(true);

        Paint aPaint = makePaint(getRandomColor());
        LineDrawable line6 = new LineDrawable(aPaint, new Line(aStartX + distanceW, top, aStartX + distanceW, top), new Line(aStartX + distanceW, top, aStartX, bottom));
        line6.setInterpolator(new LinearInterpolator());
        line6.setPaint(aPaint);
        line6.setAnimDuration(duration);
        line6.setRetainAfterAnimation(true);

        LineDrawable line7 = new LineDrawable(aPaint, new Line(aStartX + distanceW, top, aStartX + distanceW, top), new Line(aStartX + distanceW, top, aStartX + distanceW * 2, bottom));
        line7.setInterpolator(new LinearInterpolator());
        line7.setPaint(aPaint);
        line7.setAnimDuration(duration);
        line7.setRetainAfterAnimation(true);

        float vStartX = aStartX + distanceW * 2 + 30;
        Paint vPaint = makePaint(getRandomColor());
        LineDrawable line8 = new LineDrawable(vPaint, new Line(vStartX, top, vStartX, top), new Line(vStartX, top, vStartX + distanceW, bottom));
        line8.setInterpolator(new LinearInterpolator());
        line8.setPaint(vPaint);
        line8.setAnimDuration(duration);
        line8.setRetainAfterAnimation(true);

        LineDrawable line9 = new LineDrawable(vPaint, new Line(vStartX + distanceW, bottom, vStartX + distanceW, bottom), new Line(vStartX + distanceW, bottom, vStartX + distanceW * 2, top));
        line9.setInterpolator(new LinearInterpolator());
        line9.setPaint(vPaint);
        line9.setAnimDuration(duration);
        line9.setRetainAfterAnimation(true);



        ArrayList<SelfDrawable> wav = new ArrayList<>();
        wav.add(line1);
        wav.add(line2);
        wav.add(line3);
        wav.add(line4);
        wav.add(line5);
        wav.add(line6);
        wav.add(line7);
        wav.add(line8);
        wav.add(line9);

        return wav;
    }

    private void handleTouchOnFactalMode(MotionEvent event, int angle, int duration) {
        fractalTree(event.getX(), event.getY(), 200, 90, 8);
    }

    private void fractalTree(float x, float y, float length, int angle, int order) {
        if (order <= 0) {
            return;
        }

        float x2 = (float)(x + (Math.cos(Math.toRadians(angle)) * length));
        float y2 = (float)(y - (Math.sin(Math.toRadians(angle)) * length));

        int duration = getRandomDuration();
        final Paint paint = makePaint(getRandomColor());
        LineDrawable lineDrawable = new LineDrawable(paint, new Line(x, y, x, y), new Line(x, y, x2, y2));
        lineDrawable.setInterpolator(new LinearInterpolator());
        lineDrawable.setPaint(paint);
        lineDrawable.setAnimDuration(duration);
        lineDrawable.setRetainAfterAnimation(true);

        mPeanutView.startImmediate(lineDrawable);

        fractalTree(x2, y2, length * 0.8f, angle - 30, order - 1);
        fractalTree(x2, y2, length * 0.8f, angle + 30, order - 1);
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
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);
        return paint;
    }

    private Paint makeOvalPaint(@ColorInt int colorInt) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Random r = new Random();
        paint.setStrokeCap(Paint.Cap.ROUND);
        int random = Math.abs(r.nextInt());
        int remain = random % 3;

        Log.d("jm.lee", "random : " + random + ", remain : " + remain);
        if (remain == 0) {
            paint.setStyle(Paint.Style.FILL);
        } else if (remain == 1) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(10);
        } else {
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setStrokeWidth(10);
        }

        paint.setColor(colorInt);
        paint.setAntiAlias(true);
        return paint;
    }

    private int getRandomRadius() {
        int radius = (int)(Math.random() * 200);
        return (radius < 100) ? 150 : radius;
    }

    private int getRandomDuration() {
        int duration = (int)(Math.random() * 1200);
        return Math.max(duration, 500);
    }
}
