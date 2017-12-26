package com.jeongmin.peanutview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.jeongmin.peanutview.drawing.animatable.Animatable;

import java.util.ArrayList;

/**
 * Created by jay on 17. 11. 10.
 */

public class PeanutView extends View {


    private ArrayList<Animatable> animatableList;

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
        animatableList = new ArrayList<>();
    }

    /**
     * Add animatable to dra
     * @param animatable
     */
    public void addDrawingObject(Animatable animatable) {
        if (animatableList != null) {
            animatableList.add(animatable);
        }
    }

    public void startAnimation() {
        long animStartTime = System.currentTimeMillis();
        for (Animatable animatable : animatableList) {
            animatable.startAnimation(animStartTime);
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (canvas == null || animatableList == null) {
            return;
        }

        ArrayList<Animatable> toRemove = new ArrayList<>();
        boolean needInvalidate = false;
        long currentTime = System.currentTimeMillis();
        for (Animatable animatable : animatableList) {
            if (animatable.draw(canvas, currentTime)) {
                needInvalidate = true;
            }

            if (animatable.toBeRemoved(currentTime)) {
                toRemove.add(animatable);
            }
        }

        animatableList.removeAll(toRemove);

        if (needInvalidate) {
            invalidate();
        }
    }
}
