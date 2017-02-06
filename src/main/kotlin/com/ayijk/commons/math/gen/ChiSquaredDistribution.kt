package com.ayijk.commons.math.gen

import com.ayijk.commons.math.Maths
import com.ayijk.commons.math.linalg.DenseMatrix


/**
 * Created by ayijk on 2017/02/06.
 */
class ChiSquaredDistribution(val dof: Double) : UniVariateGenerative {
  init {
    require(dof > 0, {
    })

  }

  /**
   * https://en.wikipedia.org/wiki/Chi-squared_distribution#Generalized_chi-squared_distribution
   */
  override fun generate(): Double {
    val g = GaussianDistribution(doubleArrayOf(0.0), DenseMatrix.Companion.eye(1))

    val n = dof.toInt()
    val delta = dof - n
    return (0 until n).map {
      Maths.pow(g.generate(), 2)
    }.sum() + Maths.pow(g.generate(), 2) * delta
  }
}