package com.ayijk.commons.math.gen

import com.ayijk.commons.math.Maths
import com.ayijk.commons.test.TestDone


/**
 * Created by ayijk on 2017/02/05.
 */
class GammaDistribution(val alpha: Double, val beta: Double) : UniVariateGenerative {

  /**
   * https://en.wikipedia.org/wiki/Gamma_distribution#Generating_gamma-distributed_random_variables
   */
  @TestDone
  override fun generate(): Double {
    val n = alpha.toInt()
    val delta = alpha - n

    val expD = ExponentialDistribution(1.0)
    val gamma_n_1 = (0 until n).map {
      expD.generate()
    }.sum()

    val uni = UniformDistribution()

    var xi: Double
    var eta: Double
    do {
      val u = uni.generate()
      val v = uni.generate()
      val w = uni.generate()
      if (u <= Math.E / (Math.E + delta)) {
        xi = Maths.pow(v, 1 / delta)
        eta = w * Maths.pow(xi, delta - 1)
      } else {
        xi = 1 - Math.log(v)
        eta = w * Math.exp(-xi)
      }
    } while (eta > Maths.pow(xi, delta - 1) * Math.exp(-xi))

    return (xi + gamma_n_1) * beta
  }
}