package com.ayijk.commons.math

import com.ayijk.commons.math.AssertUtils.assertEquals
import org.junit.Test

/**
 * Created by ayijk on 2017/01/29.
 */
class ExStatsTest {
  @Test
  fun meanAndVarianceTest() {
    val array = arrayOf(10, 20, 30, 40, 50)
    val mv = array.meanAndVariance

    assertEquals(30, mv.mean)
    assertEquals(200, mv.variance)
  }
}