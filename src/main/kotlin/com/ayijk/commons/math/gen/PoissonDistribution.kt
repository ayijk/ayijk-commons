package com.ayijk.commons.math.gen

import com.ayijk.commons.test.TestDone

/**
 * Created by ayijk on 2017/02/05.
 */
class PoissonDistribution(val lambda: Double) : UniVariateGenerative {
  init {
    require(lambda > 0, {
    })
  }

  /**
   * https://en.wikipedia.org/wiki/Poisson_distribution#Generating_Poisson-distributed_random_variables
   */
  @TestDone
  override fun generate(): Double {
    val uni = UniformDistribution()

    val L = Math.exp(-lambda)
    var k = 0
    var p = 1.0
    do {
      k++
      p *= uni.generate()
    } while (p > L)

    return k - 1.0
  }
}