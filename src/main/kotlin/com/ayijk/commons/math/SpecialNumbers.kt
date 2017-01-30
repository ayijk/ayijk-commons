package com.ayijk.commons.math

import com.ayijk.commons.test.TestDone
import java.math.BigDecimal
import java.math.BigInteger


/**
 * Created by ayijk on 2017/01/28.
 */
object SpecialNumbers {

  @TestDone
  tailrec fun fact(n: Int, f: BigInteger = BigInteger.ONE): BigInteger {
    return when (n) {
      0 -> f
      else -> fact(n - 1, n * f)
    }
  }

  /**
   * nPk
   */
  @TestDone
  fun permutation(n: Int, k: Int): BigInteger {
    require(n >= 0 && k >= 0 && n >= k, {
      "n:$n, k:$k"
    })

    return fact(n) / fact(n - k)
  }

  /**
   * nCk
   */
  @TestDone
  fun combination(n: Int, k: Int): BigInteger {
    return permutation(n, k) / fact(k)
  }

  /**
   * https://fr.wikipedia.org/wiki/Nombre_de_Stirling#Formules_explicites
   */
  @TestDone
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
          Maths.pow(-1, j).toInt() * Maths.bigDecimalPow(j, n - m + k) / (fact(j) * fact(k - j))
        }.sum
        d / ((n + k) * fact(n - m - k) * fact(n - m + k))
      }.sum
      val bd = (a * c / b)
      val bdScale = bd.setScale(0, BigDecimal.ROUND_HALF_UP)
      val ret = bdScale.toBigInteger().abs()

      return ret
    }
  }

  /**
   * https://fr.wikipedia.org/wiki/Nombre_de_Stirling#Formule_explicite
   */
  @TestDone
  fun stirlingNumber2nd(n: Int, m: Int): BigInteger {
    val a = (0..m).map { j ->
      Maths.pow(-1, j).toInt() * combination(m, j) * Maths.bigIntegerPow(j, n)
    }.sum
    return a / fact(m)
  }
}