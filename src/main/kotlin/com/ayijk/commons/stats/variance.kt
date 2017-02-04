package com.ayijk.commons.stats

/**
 * Created by ayijk on 2017/02/03.
 */
object variance {
  operator fun invoke(v: Collection<Number>): Double {
    return variance(v.map(Number::toDouble).toDoubleArray())
  }

  operator fun invoke(v: Array<out Number>): Double {
    return variance(v.map(Number::toDouble).toDoubleArray())
  }

  operator fun invoke(v: IntArray): Double {
    return variance(v.map(Int::toDouble).toDoubleArray())
  }

  operator fun invoke(v: DoubleArray): Double {
    val mv = meanAndVariance(v)
    return mv.variance
  }
}