package com.somethingfun.jay.peanut.drawing;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.animation.Interpolator;

import com.somethingfun.jay.peanut.PeanutView;
import com.somethingfun.jay.peanut.transition.AlphaTransition;


/**
 * Created by jay on 17. 11. 10.
 */

public abstract class DrawableObject {

    protected Paint paint;                  // paint for drawing
    protected Interpolator interpolator;    // interpolator
    protected AlphaTransition alphaAnim;          // represent for alpha anim
    protected long duration;                // duration of animation
    protected long startTime;               // animation starting time
    protected boolean repeatAnim;
    protected boolean onAnimating;
    protected boolean retainAfterAnimation = true;


    public void startAnimation(long startTime) {
        this.startTime = startTime;
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

    protected boolean shouldAnimate(long currentTime) {
        long animEndTime = startTime + duration;
        return animEndTime > currentTime;
    }

    protected boolean shouldInvalidate() {
        return onAnimating || repeatAnim;
    }

    /**
     *
     * @param view
     * @param canvas
     * @param currentTime
     * @return Need to invalidate or not
     */
    public boolean draw(PeanutView view, Canvas canvas, long currentTime) {
        if (view == null || canvas == null) {
            return false;
        }

        boolean shouldAnimate = shouldAnimate(currentTime);
        boolean justStoppedAnimating = onAnimating && shouldAnimate == false;
        onAnimating = shouldAnimate;
        if (onAnimating) {
            long timeProceed = currentTime - startTime;
            float interpolation = getInterpolation(timeProceed / (float) duration);
            drawInAnimation(canvas, interpolation);
            return shouldInvalidate();
        } else {
            if (justStoppedAnimating) {
                if (repeatAnim) {
                    startAnimation(System.currentTimeMillis());
                }
            } else {
                if (retainAfterAnimation) {
                    drawLastState(canvas);
                } else {
                    drawInitialState(canvas);
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
    public void setAnimateDuration(long duration) {
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

}
