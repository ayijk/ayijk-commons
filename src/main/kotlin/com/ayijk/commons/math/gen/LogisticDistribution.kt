package com.ayijk.commons.math.gen

import com.ayijk.commons.math.Maths
import com.ayijk.commons.test.TestDone

/**
 * Created by ayijk on 2017/02/05.
 */
class LogisticDistribution(val location: Double, val scale: Double) : UniVariateGenerative {
  init {
    require(scale > 0, {
    })
  }

  /**
   * http://www.ntrand.com/logistic-distribution/
   */
  @TestDone
  override fun generate(): Double {
    val uni = UniformDistribution()
    return 2 * scale * Maths.artanh(2 * uni.generate() - 1) + location
  }
}