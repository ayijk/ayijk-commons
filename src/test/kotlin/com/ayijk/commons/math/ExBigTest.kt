package com.ayijk.commons.math

import com.ayijk.commons.math.AssertUtils.assertEquals
import org.junit.Test
import java.math.BigDecimal

/**
 * Created by ayijk on 2017/01/29.
 */
class ExBigTest {
  @Test
  fun biPlusTest() {
    assertEquals(120, BigInteger(100) + BigInteger(20))
    assertEquals(120, BigInteger(100) + 20.toLong())
    assertEquals(120, BigInteger(100) + 20)
  }

  @Test
  fun biMinusTest() {
    assertEquals(80, BigInteger(100) - BigInteger(20))
    assertEquals(80, BigInteger(100) - 20.toLong())
    assertEquals(80, BigInteger(100) - 20)
  }

  @Test
  fun biTimesTest() {
    assertEquals(2000, BigInteger(100) * BigInteger(20))
    assertEquals(2000, BigInteger(100) * 20.toLong())
    assertEquals(2000, BigInteger(100) * 20)
  }

  @Test
  fun bdPlusTest() {
    assertEquals(120, BigDecimal(100) + BigDecimal(20))
    assertEquals(120, BigDecimal(100) + BigInteger(20))
    assertEquals(120, BigDecimal(100) + 20.toDouble())
    assertEquals(120, BigDecimal(100) + 20)
  }

  @Test
  fun bdTimesTest() {
    assertEquals(2000, BigDecimal(100) * BigDecimal(20))
    assertEquals(2000, BigDecimal(100) * BigInteger(20))
    assertEquals(2000, BigDecimal(100) * 20.toDouble())
    assertEquals(2000, BigDecimal(100) * 20)
  }

  @Test
  fun bdDivTest() {
    assertEquals(5, BigDecimal(100) / BigDecimal(20))
    assertEquals(5, BigDecimal(100) / BigInteger(20))
    assertEquals(5, BigDecimal(100) / 20.toDouble())
    assertEquals(5, BigDecimal(100) / 20)
  }

  @Test
  fun biToBdTest() {
    assertEquals(120, BigInteger(100) + BigDecimal(20))
    assertEquals(80, BigInteger(100) - BigDecimal(20))
    assertEquals(2000, BigInteger(100) * BigDecimal(20))
  }

  @Test
  fun doubleToBdTest() {
    assertEquals(120, 100 + BigDecimal(20))
    assertEquals(80, 100 - BigDecimal(20))
    assertEquals(2000, 100 * BigDecimal(20))
    assertEquals(5, 100 / BigDecimal(20))
  }

  @Test
  fun intToBdTest() {
    assertEquals(120, 100 + BigDecimal(20))
    assertEquals(80, 100 - BigDecimal(20))
    assertEquals(2000, 100 * BigDecimal(20))
    assertEquals(5, 100 / BigDecimal(20))
  }

  @Test
  fun intToBiTest() {
    assertEquals(120, 100 + BigInteger(20))
    assertEquals(80, 100 - BigInteger(20))
    assertEquals(2000, 100 * BigInteger(20))
  }

  @Test
  fun bdSumTest() {
    val array = arrayListOf(10, 20, 30, 40).map(::BigDecimal)
    assertEquals(100, array.sum)
  }
}