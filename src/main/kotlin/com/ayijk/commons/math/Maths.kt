package com.ayijk.commons.math

import com.ayijk.commons.test.TestYet
import java.math.BigDecimal
import java.math.BigInteger

/**
 * Created by ayijk on 2017/01/29.
 */
object Maths {
  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
  // pow
  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
  @TestYet
  fun pow(a: Int, b: Int): Double {
    return Math.pow(a.toDouble(), b.toDouble())
  }

  @TestYet
  fun pow(a: Int, b: Double): Double {
    return Math.pow(a.toDouble(), b)
  }

  @TestYet
  fun pow(a: Double, b: Int): Double {
    return Math.pow(a, b.toDouble())
  }

  @TestYet
  fun pow(a: Double, b: Double): Double {
    return Math.pow(a, b)
  }

  @TestYet
  fun pow(a: BigInteger, b: Int): BigInteger {
    return a.pow(b)
  }

  @TestYet
  fun bigIntegerPow(a: Int, b: Int): BigInteger {
    return BigInteger.valueOf(a.toLong()).pow(b)
  }

  @TestYet
  fun bigDecimalPow(a: Double, b: Int): BigDecimal {
    return BigDecimal(a).pow(b)
  }

  @TestYet
  fun bigDecimalPow(a: Int, b: Int): BigDecimal {
    return bigDecimalPow(a.toDouble(), b)
  }
  //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
  // pow
  //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

  @TestYet
  fun logSumExp(array: DoubleArray): Double {
    var maxValue = -Double.MAX_VALUE
    array.indices.forEach { row ->
      if (maxValue < array[row]) {
        maxValue = array[row]
      }
    }

    var logsumexp = 0.0
    array.indices.forEach { row ->
      logsumexp += Math.exp(array[row] - maxValue)
    }

    return maxValue + Math.log(logsumexp)
  }

}