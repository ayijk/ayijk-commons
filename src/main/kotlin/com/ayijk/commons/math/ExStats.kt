package com.ayijk.commons.math

/**
 * Created by ayijk on 2017/01/29.
 */
data class MeanAndVariance(val mean: Double, val variance: Double)

val Array<out Number>.meanAndVariance: MeanAndVariance
  get() {
    return this.toList().meanAndVariance
  }

val Collection<Number>.meanAndVariance: MeanAndVariance
  get() {
    val es = this.map { it ->
      val d = it.toDouble()
      Pair(d, d * d)
    }.reduce { p1, p2 ->
      Pair(p1.first + p2.first, p1.second + p2.second)
    }

    val mean = es.first / this.size
    val mean2 = es.second / this.size

    val variance = mean2 - mean * mean

    return MeanAndVariance(mean, variance)
  }