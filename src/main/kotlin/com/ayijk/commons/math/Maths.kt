package com.ayijk.commons.math

import java.math.BigDecimal
import java.math.BigInteger

/**
 * Created by ayijk on 2017/01/29.
 */
object Maths {
  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
  // pow
  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
  fun pow(a: Int, b: Int): Double {
    return Math.pow(a.toDouble(), b.toDouble())
  }

  fun pow(a: Int, b: Double): Double {
    return Math.pow(a.toDouble(), b)
  }

  fun pow(a: Double, b: Int): Double {
    return Math.pow(a, b.toDouble())
  }

  fun pow(a: Double, b: Double): Double {
    return Math.pow(a, b)
  }

  fun pow(a: BigInteger, b: Int): BigInteger {
    return a.pow(b)
  }

  fun bigIntegerPow(a: Int, b: Int): BigInteger {
    return BigInteger.valueOf(a.toLong()).pow(b)
  }

  fun bigDecimalPow(a: Double, b: Int): BigDecimal {
    return BigDecimal(a).pow(b)
  }

  fun bigDecimalPow(a: Int, b: Int): BigDecimal {
    return bigDecimalPow(a.toDouble(), b)
  }

  //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
  // pow
  //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

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