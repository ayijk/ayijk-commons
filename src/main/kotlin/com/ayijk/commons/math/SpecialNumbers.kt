package com.ayijk.commons.math

import java.math.BigDecimal
import java.math.BigInteger


/**
 * Created by ayijk on 2017/01/28.
 */
object SpecialNumbers {

  tailrec fun fact(n: Int, f: BigInteger = BigInteger.ONE): BigInteger {
    return when (n) {
      0 -> f
      else -> fact(n - 1, f * n)
    }
  }

  /**
   * http://mathoverflow.net/questions/72854/stirling-number-of-first-kind-implementation
   */
  fun stirlingNumber1st(n: Int, m: Int): BigInteger {
    require(n >= 0 && m >= 0, {
      "the arguments 'n' and 'm' should be positive, but actually are n:$n, m:$m"
    })

    if (n == m) {
      return BigInteger.ONE
    } else if (n > 0 && m == 0) {
      return BigInteger.ZERO
    } else if (m > n) {
      return BigInteger.ZERO
    } else {
      val a = fact(2 * n - m)
      val b = fact(m - 1)
      val c = (0..(n - m)).map { k ->
        val d = (0..k).map { j ->
          Math.pow(-1, j) * BigDecimal(j).pow(n - m + k) / (fact(j) * fact(k - j))
        }.sum
        d / ((n + k) * fact(n - m - k) * fact(n - m + k))
      }.sum
      val bd = (a * c / b)
      val bdScale = bd.setScale(0, BigDecimal.ROUND_HALF_UP)
      val ret = bdScale.toBigInteger().abs()

      return ret
    }
  }
}