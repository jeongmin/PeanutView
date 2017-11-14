package com.somethingfun.jay.peanut.transition;

/**
 * Created by jay on 17. 11. 14.
 */

public interface TransitionObject<T> {
    T calcTransitionValue(float interpolation);
}
