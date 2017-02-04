package com.ayijk.commons.stats

/**
 * Created by ayijk on 2017/02/03.
 */
object meanAndVariance {
  data class MeanAndVariance(val mean: Double, val variance: Double, val count: Int) {
    val std by lazy { Math.sqrt(variance) }
  }

  operator fun invoke(v: Collection<Number>): MeanAndVariance {
    return meanAndVariance(v.map(Number::toDouble).toDoubleArray())
  }

  operator fun invoke(v: Array<out Number>): MeanAndVariance {
    return meanAndVariance(v.map(Number::toDouble).toDoubleArray())
  }

  operator fun invoke(v: IntArray): MeanAndVariance {
    return meanAndVariance(v.map(Int::toDouble).toDoubleArray())
  }

  operator fun invoke(v: DoubleArray): MeanAndVariance {
    if (v.isEmpty()) {
      return MeanAndVariance(0.0, 0.0, 0)
    } else {
      val es = v.map { it ->
        val d = it
        Pair(d, d * d)
      }.reduce { p1, p2 ->
        Pair(p1.first + p2.first, p1.second + p2.second)
      }

      val mean = es.first / v.size
      val mean2 = es.second / v.size

      val variance = mean2 - mean * mean

      return MeanAndVariance(mean, variance, v.size)
    }
  }
}