package com.jeongmin.peanutview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.jeongmin.peanutview.drawing.animatable.SelfDrawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jay on 17. 11. 10.
 */

public class PeanutView extends View {


    private ArrayList<SelfDrawable> selfDrawableList;

    public PeanutView(Context context) {
        super(context);
        init();
    }

    public PeanutView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PeanutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        selfDrawableList = new ArrayList<>();
    }

    /**
     * Add selfDrawable to dra
     * @param selfDrawable
     */
    public void addAnimatable(SelfDrawable selfDrawable) {
        if (selfDrawableList != null) {
            selfDrawableList.add(selfDrawable);
        }
    }

    public void startAnimation() {
        long animStartTime = System.currentTimeMillis();
        for (SelfDrawable selfDrawable : selfDrawableList) {
            selfDrawable.startAnimation(animStartTime);
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (canvas == null || selfDrawableList == null) {
            return;
        }

        ArrayList<SelfDrawable> toRemove = new ArrayList<>();
        boolean needInvalidate = false;
        long currentTime = System.currentTimeMillis();
        for (SelfDrawable selfDrawable : selfDrawableList) {
            if (selfDrawable.draw(canvas, currentTime)) {
                needInvalidate = true;
            }

            if (selfDrawable.toBeRemoved(currentTime)) {
                toRemove.add(selfDrawable);
            }
        }

        selfDrawableList.removeAll(toRemove);

        if (needInvalidate) {
            invalidate();
        }
    }

    public void addToStartLine(List<SelfDrawable> selfDrawableList) {
        long currentTime = System.currentTimeMillis();
        for (SelfDrawable selfDrawable : selfDrawableList) {
            selfDrawable.startAnimation(currentTime);
            addAnimatable(selfDrawable);
        }
        invalidate();
    }

    public void addToStartLine(SelfDrawable selfDrawable) {
        long currentTime = System.currentTimeMillis();
        selfDrawable.startAnimation(currentTime);
        addAnimatable(selfDrawable);
        invalidate();
    }
}
