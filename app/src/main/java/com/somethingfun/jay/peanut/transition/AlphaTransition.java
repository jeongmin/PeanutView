package com.somethingfun.jay.peanut.transition;

/**
 * Created by jay on 17. 11. 14.
 */

public class AlphaTransition implements TransitionObject<Integer> {
    public float from;
    public float to;

    @Override
    public Integer calcTransitionValue(float interpolation) {
        return (int)((to - from) * interpolation * 255);
    }
}
