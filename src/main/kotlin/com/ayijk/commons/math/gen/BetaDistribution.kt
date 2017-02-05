package com.ayijk.commons.math.gen

import com.ayijk.commons.test.TestDone

/**
 * Created by ayijk on 2017/02/05.
 */
class BetaDistribution(val alpha: Double, val beta: Double) : UniVariateGenerative {
  init {
    require(alpha > 0 && beta > 0, {
    })
  }

  /**
   * https://en.wikipedia.org/wiki/Beta_distribution#Generating_beta-distributed_random_variates
   */
  @TestDone
  override fun generate(): Double {
    val g1 = GammaDistribution(alpha, 1.0)
    val g2 = GammaDistribution(beta, 1.0)

    val x = g1.generate()
    val y = g2.generate()

    return x / (x + y)
  }
}