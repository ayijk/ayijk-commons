package com.ayijk.commons.stats

import com.ayijk.commons.math.AssertUtils.assertEquals
import org.junit.Test

/**
 * Created by ayijk on 2017/01/29.
 */
class meanAndVarianceTest {
  @Test
  fun meanAndVarianceTest() {
    val array = arrayOf(10, 20, 30, 40, 50)
    val mv = meanAndVariance(array)

    assertEquals(30, mv.mean)
    assertEquals(200, mv.variance)
  }
}