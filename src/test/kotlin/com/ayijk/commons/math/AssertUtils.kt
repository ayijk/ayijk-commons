package com.ayijk.commons.math

import org.junit.Assert
import java.math.BigDecimal
import java.math.BigInteger

/**
 * Created by ayijk on 2017/01/29.
 */
object AssertUtils {
  fun assertEquals(expected: String, actual: Number): Unit {
    assertEquals(BigDecimal(expected), actual)
  }

  fun assertEquals(expected: Number, actual: Number): Unit {
    fun castToBigDecimal(number: Number): BigDecimal {
      return when (number) {
        is BigInteger -> BigDecimal(number)
        is BigDecimal -> number
        else -> BigDecimal(number.toDouble())
      }
    }

    Assert.assertEquals(castToBigDecimal(expected), castToBigDecimal(actual))
  }
}