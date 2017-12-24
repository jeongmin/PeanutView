package com.somethingfun.jay.peanut;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.somethingfun.jay.peanut.drawing.DrawableObject;

import java.util.ArrayList;

/**
 * Created by jay on 17. 11. 10.
 */

public class PeanutView extends View {


    private ArrayList<DrawableObject> drawableObjectList;
    private long animStartTime;


    public PeanutView(Context context) {
        super(context);
    }

    public PeanutView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PeanutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init() {
        drawableObjectList = new ArrayList<>();
    }

    /**
     * Add drawableObject to dra
     * @param drawableObject
     */
    public void addDrawingObject(DrawableObject drawableObject) {
        if (drawableObjectList != null) {
            drawableObjectList.add(drawableObject);
        }
    }

    public void removeDrawingObject(DrawableObject drawableObject) {
        if (drawableObjectList != null) {
            drawableObjectList.remove(drawableObject);
        }
    }

    public void startAnimation() {
        animStartTime = System.currentTimeMillis();
        for (DrawableObject drawableObject : drawableObjectList) {
            drawableObject.startAnimation(animStartTime);
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (canvas == null || drawableObjectList == null) {
            return;
        }

        boolean needInvalidate = false;
        long currentTime = System.currentTimeMillis();
        for (DrawableObject drawableObject : drawableObjectList) {
            if (drawableObject.draw(this, canvas, currentTime)) {
                needInvalidate = true;
            }
        }

        if (needInvalidate) {
            invalidate();
        }
    }

    public boolean shouldAnimate(DrawableObject drawableObject, long currentTime) {
        long animEndTime = animStartTime + drawableObject.getDuration() + drawableObject.getDelay();
        return animStartTime <= currentTime && animEndTime > currentTime;
    }

    public long getAnimStartTime() {
        return animStartTime;
    }
}
