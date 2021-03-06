package com.jeongmin.peanutview.drawing.drawable;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.animation.Interpolator;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.jeongmin.peanutview.drawing.event.OnAnimationEnd;
import com.jeongmin.peanutview.transition.AlphaTransition;


/**
 * Created by jay on 17. 11. 10.
 */
public abstract class SelfDrawable {

    protected Paint paint;                          // paint for drawing
    protected Interpolator interpolator;            // interpolator
    protected AlphaTransition alphaAnim;            // represent for alpha anim
    protected long duration;                        // duration of animation
    protected long startTime;                       // animation starting time
    protected long delay;                           // delay before starting
    protected boolean repeatAnim;                   // repeat animation or not
    protected boolean isTheLastAnimationFrame;      // We need to show the last frame
    protected boolean retainAfterAnimation = true;
    protected float interpolation = 0F;

    private boolean onAnimating;

    protected OnAnimationEnd onAnimationEndEvent;

    public void setPaint(@NonNull Paint paint) {
        this.paint = paint;
    }

    public void startAnimation(long startTime) {
        this.startTime = startTime + delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public void setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
    }

    public void setRetainAfterAnimation(boolean retainAfterAnimation) {
        this.retainAfterAnimation = retainAfterAnimation;
    }

    public void repeatAnim(boolean repeatAnim) {
        this.repeatAnim = repeatAnim;
    }

    public void setAlphaAnim(float from, float to) {
        alphaAnim = new AlphaTransition();
        alphaAnim.from = from;
        alphaAnim.to   = to;
    }

    public long getDuration() {
        return duration;
    }

    public long getDelay() {
        return delay;
    }

    public long getTotalAnimationDuration() {
        return duration + delay;
    }

    protected boolean shouldInvalidate() {
        return onAnimating || repeatAnim;
    }

    /**
     *
     * @param canvas
     * @param currentTime
     * @return Need to invalidate or not
     */
    public boolean draw(@NonNull Canvas canvas, long currentTime) {
        if (currentTime < startTime) {
            return true;
        }

        boolean shouldAnimate = shouldAnimate(currentTime);
        boolean justStoppedAnimating = onAnimating && !shouldAnimate;
        isTheLastAnimationFrame = (justStoppedAnimating && !isTheLastAnimationFrame);
        onAnimating = isTheLastAnimationFrame || shouldAnimate;
        if (onAnimating) {
            long timeProceed = currentTime - startTime;
            interpolation = getInterpolation(timeProceed / (float) duration);
            if (isTheLastAnimationFrame) {
                interpolation = 1;
            }
            drawInAnimation(canvas, interpolation);
        } else {
            if (retainAfterAnimation) {
                drawLastState(canvas);
            } else {
                interpolation = 0F;
            }

            // repeat
            if (justStoppedAnimating) {
                if (repeatAnim) {
                    startAnimation(System.currentTimeMillis());
                } else {
                    if (onAnimationEndEvent != null) {
                        onAnimationEndEvent.onAnimationEnd();
                    }
                }
            }
        }

        return shouldInvalidate();
    }

    /**
     * Draw to canvas by the initial state
     * @param canvas
     */
    protected abstract void drawInitialState(Canvas canvas);

    /**
     * Draw to canvas by the last state
     * @param canvas
     */
    protected abstract void drawLastState(Canvas canvas);

    /**
     * Draw in animating
     * @param canvas for drawing
     * @param interpolation for calcuating transition values
     */
    protected abstract void drawInAnimation(Canvas canvas, float interpolation);

    /**
     * Set duration of animation
     * @param duration
     */
    public void setAnimDuration(long duration) {
        this.duration = duration;
    }

    public void setAnimDuration(int duration) {
        this.duration = duration;
    }

    /**
     *
     * @param input
     * @return
     */
    protected float getInterpolation(float input) {
        if (interpolator == null) {
            return input;
        }
        return interpolator.getInterpolation(input);
    }

    protected void setAlpha(AlphaTransition alphaAnim, Paint paint, float interpolation) {
        if (alphaAnim == null || paint == null) {
            return;
        }

        paint.setAlpha(alphaAnim.calcTransitionValue(interpolation));
    }

    public void setPaintColor(@ColorInt int color) {
        if (paint != null) {
            paint.setColor(color);
        }
    }

    public boolean toBeRemoved(long currentTime) {
        return currentTime >= (startTime + duration) && !onAnimating && !retainAfterAnimation;
    }

    private boolean shouldAnimate(long currentTime) {
        long animEndTime = startTime + duration;
        return startTime <= currentTime && animEndTime > currentTime;
    }

    public void setOnAnimationEndListener(OnAnimationEnd listener) {
        onAnimationEndEvent = listener;
    }

    public Paint getPaint() {
        return paint;
    }

    public float getInterpolation() {
        return interpolation;
    }
}
