package com.ayijk.commons.math

import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal
import java.math.BigInteger

/**
 * Created by ayijk on 2017/01/29.
 */
class ExBigTest {
  @Test
  fun biPlusTest() {
    assertEquals((BigInteger.valueOf(100) + BigInteger.valueOf(20)).toLong(), 120)
    assertEquals((BigInteger.valueOf(100) + 20.toLong()).toLong(), 120)
    assertEquals((BigInteger.valueOf(100) + 20).toLong(), 120)
  }

  @Test
  fun biMinusTest() {
    assertEquals((BigInteger.valueOf(100) - BigInteger.valueOf(20)).toLong(), 80)
    assertEquals((BigInteger.valueOf(100) - 20.toLong()).toLong(), 80)
    assertEquals((BigInteger.valueOf(100) - 20).toLong(), 80)
  }

  @Test
  fun biTimesTest() {
    assertEquals((BigInteger.valueOf(100) * BigInteger.valueOf(20)).toLong(), 2000)
    assertEquals((BigInteger.valueOf(100) * 20.toLong()).toLong(), 2000)
    assertEquals((BigInteger.valueOf(100) * 20).toLong(), 2000)
  }

  @Test
  fun bdPlusTest() {
    assertEquals((BigDecimal.valueOf(100) + BigDecimal.valueOf(20)).toLong(), 120)
    assertEquals((BigDecimal.valueOf(100) + BigInteger.valueOf(20)).toLong(), 120)
    assertEquals((BigDecimal.valueOf(100) + 20.0).toLong(), 120)
    assertEquals((BigDecimal.valueOf(100) + 20).toLong(), 120)
  }

  @Test
  fun bdTimesTest() {
    assertEquals((BigDecimal.valueOf(100) * BigDecimal.valueOf(20)).toLong(), 2000)
    assertEquals((BigDecimal.valueOf(100) * BigInteger.valueOf(20)).toLong(), 2000)
    assertEquals((BigDecimal.valueOf(100) * 20.0).toLong(), 2000)
    assertEquals((BigDecimal.valueOf(100) * 20).toLong(), 2000)
  }

  @Test
  fun bdDivTest() {
    assertEquals((BigDecimal.valueOf(100) / BigDecimal.valueOf(20)).toLong(), 5)
    assertEquals((BigDecimal.valueOf(100) / BigInteger.valueOf(20)).toLong(), 5)
    assertEquals((BigDecimal.valueOf(100) / 20.0).toLong(), 5)
    assertEquals((BigDecimal.valueOf(100) / 20).toLong(), 5)
  }

  @Test
  fun biToBdTest() {
    assertEquals((BigInteger.valueOf(100) + BigDecimal.valueOf(20)).toLong(), 120)
    assertEquals((BigInteger.valueOf(100) - BigDecimal.valueOf(20)).toLong(), 80)
    assertEquals((BigInteger.valueOf(100) * BigDecimal.valueOf(20)).toLong(), 2000)
  }

  @Test
  fun doubleToBdTest() {
    assertEquals((100 + BigDecimal.valueOf(20)).toDouble(), 120.0, Double.EPS)
    assertEquals((100 - BigDecimal.valueOf(20)).toDouble(), 80.0, Double.EPS)
    assertEquals((100 * BigDecimal.valueOf(20)).toDouble(), 2000.0, Double.EPS)
    assertEquals((100 / BigDecimal.valueOf(20)).toDouble(), 5.0, Double.EPS)
  }

  @Test
  fun intToBdTest() {
    assertEquals((100 + BigDecimal.valueOf(20)).toInt(), 120)
    assertEquals((100 - BigDecimal.valueOf(20)).toInt(), 80)
    assertEquals((100 * BigDecimal.valueOf(20)).toInt(), 2000)
    assertEquals((100 / BigDecimal.valueOf(20)).toInt(), 5)
  }

  @Test
  fun intToBiTest() {
    assertEquals((100 + BigInteger.valueOf(20)).toInt(), 120)
    assertEquals((100 - BigInteger.valueOf(20)).toInt(), 80)
    assertEquals((100 * BigInteger.valueOf(20)).toInt(), 2000)
  }

  @Test
  fun bdSumTest() {
    val array = arrayListOf(10, 20, 30, 40).map(::BigDecimal)
    assertEquals(100.0, array.sum.toDouble(), Double.EPS)
  }
}