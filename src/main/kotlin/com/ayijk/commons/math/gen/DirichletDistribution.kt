package com.ayijk.commons.math.gen

/**
 * Created by ayijk on 2017/02/05.
 */
class DirichletDistribution(alphas: DoubleArray) : MultiVariateGenerative {
  val alphas: DoubleArray
  val dim = alphas.size

  init {
    require(alphas.none { it < 0 }, {
    })

    val sum = alphas.sum()
    this.alphas = alphas.map { it / sum }.toDoubleArray()
  }

  /**
   * https://en.wikipedia.org/wiki/Dirichlet_distribution#Gamma_distribution
   */
  override fun generateArray(): DoubleArray {
    val ys = alphas.map {
      val gd = GammaDistribution(it, 1.0)
      gd.generate()
    }

    val sum = ys.sum()

    return ys.map { it / sum }.toDoubleArray()
  }
}