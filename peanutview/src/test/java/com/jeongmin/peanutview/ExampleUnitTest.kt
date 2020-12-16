package com.jeongmin.peanutview

import com.jeongmin.peanutview.util.intersectionXwithCircle
import org.junit.Assert
import org.junit.Test
import kotlin.math.sqrt

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        Assert.assertEquals(4, 2 + 2.toLong())

        val sqrt32 = sqrt(32f)
        val t = sqrt(32f) / 4

        val result = intersectionXwithCircle(2f, 1f, 0f, 0f, 0f)
        println("result: $result, t: $t, sqrt32: $sqrt32")
    }
}