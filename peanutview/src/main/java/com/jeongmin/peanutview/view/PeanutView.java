package com.jeongmin.peanutview.view;

import android.content.Context;
import android.graphics.Canvas;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.jeongmin.peanutview.drawing.drawable.SelfDrawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jay on 17. 11. 10.
 */

public class PeanutView extends View {

    private ArrayList<SelfDrawable> selfDrawableList;
    private ArrayList<SelfDrawable> drawableListToRemove;
    private ArrayList<SelfDrawable> drawableListToAdd;

    public void setPeanutDrawEventListener(PeanutDrawEvent onDrawEventListener) {
        this.onDrawEventListener = onDrawEventListener;
    }

    private PeanutDrawEvent onDrawEventListener;

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
        drawableListToAdd = new ArrayList<>();
        drawableListToRemove = new ArrayList<>();
    }

    /**
     * Add selfDrawable to dra
     * @param selfDrawable
     */
    public void addAnimatable(SelfDrawable selfDrawable) {
        drawableListToAdd.add(selfDrawable);
        invalidate();
    }

    public void removeAnimatable(SelfDrawable selfDrawable) {
        drawableListToRemove.add(selfDrawable);
        invalidate();
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

        boolean needInvalidate = false;
        long currentTime = System.currentTimeMillis();
        for (SelfDrawable selfDrawable : selfDrawableList) {
            if (selfDrawable.draw(canvas, currentTime)) {
                needInvalidate = true;
            }

            if (selfDrawable.toBeRemoved(currentTime)) {
                drawableListToRemove.add(selfDrawable);
            }
        }

        if (drawableListToRemove.size() > 0) {
            needInvalidate = true;
            selfDrawableList.removeAll(drawableListToRemove);
            drawableListToRemove.clear();
        }

        if (drawableListToAdd.size() > 0) {
            needInvalidate = true;
            selfDrawableList.addAll(drawableListToAdd);
            drawableListToAdd.clear();
        }

        if (onDrawEventListener != null) {
            onDrawEventListener.onDraw();
        }

        if (needInvalidate) {
            invalidate();
        }
    }

    /**
     * Add selfDrawables and start them immediately.
     * @param selfDrawableList
     */
    public void startImmediate(List<SelfDrawable> selfDrawableList) {
        long currentTime = System.currentTimeMillis();
        for (SelfDrawable selfDrawable : selfDrawableList) {
            selfDrawable.startAnimation(currentTime);
            addAnimatable(selfDrawable);
        }
    }

    /**
     * Add selfDrawable and start it immediately.
     * @param selfDrawable
     */
    public void startImmediate(SelfDrawable selfDrawable) {
        long currentTime = System.currentTimeMillis();
        selfDrawable.startAnimation(currentTime);
        addAnimatable(selfDrawable);
    }

    public void clear() {
        selfDrawableList.clear();
        drawableListToAdd.clear();
        drawableListToRemove.clear();
        invalidate();
    }
}
