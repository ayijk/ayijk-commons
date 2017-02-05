package com.ayijk.commons.math

import com.ayijk.commons.math.AssertUtils.assertEquals
import org.junit.Test

/**
 * Created by ayijk on 2017/02/05.
 */
class MathsTest {
  @Test
  fun logGammaTest() {
    assertEquals(4.599479878042021, Maths.logGamma(0.01), Maths.pow(10, -10))
    assertEquals(-0.058063332505353, Maths.logGamma(1.12), Maths.pow(10, -10))
    assertEquals(71.257038967168015, Maths.logGamma(30.0), Maths.pow(10, -10))
    assertEquals(8.579336698258574e2, Maths.logGamma(200.0), Maths.pow(10, -10))
  }

  @Test
  fun logFactorial() {
    val n = 100
    assertEquals(Math.log(SpecialNumbers.fact(n).toDouble()), Maths.logFactorial(n), Maths.pow(10, -10))
  }

  @Test
  fun betaTest() {
    assertEquals(4.992508740634659e-09, Maths.beta(10, 20), Maths.pow(10, -5))
    assertEquals(0.009523809523810, Maths.beta(5, 3), Maths.pow(10, -5))
  }
}