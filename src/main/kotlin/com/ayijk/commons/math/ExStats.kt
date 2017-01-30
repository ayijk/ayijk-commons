package com.ayijk.commons.math

import com.ayijk.commons.test.TestDone

/**
 * Created by ayijk on 2017/01/29.
 */
data class MeanAndVariance(val mean: Double, val variance: Double)

@TestDone
fun Array<out Number>.meanAndVariance(): MeanAndVariance {
  return this.toList().meanAndVariance()
}

@TestDone
fun Collection<Number>.meanAndVariance(): MeanAndVariance {
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