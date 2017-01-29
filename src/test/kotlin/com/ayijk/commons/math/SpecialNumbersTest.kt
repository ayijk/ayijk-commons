package com.ayijk.commons.math

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by ayijk on 2017/01/28.
 */
class SpecialNumbersTest {
  @Test
  fun factTest() {
    assertEquals(1, SpecialNumbers.fact(0).toLong())
    assertEquals(1, SpecialNumbers.fact(1).toLong())
    assertEquals(2, SpecialNumbers.fact(2).toLong())
    assertEquals(3628800, SpecialNumbers.fact(10).toLong())
  }

  @Test
  fun stirlingNumber1stTest() {
    assertEquals(1, SpecialNumbers.stirlingNumber1st(0, 0).toLong())
    assertEquals(1764, SpecialNumbers.stirlingNumber1st(7, 2).toLong())
    assertEquals(6634460278534540725, SpecialNumbers.stirlingNumber1st(30, 20).toLong())
  }
}