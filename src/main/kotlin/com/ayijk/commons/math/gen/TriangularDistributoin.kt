package com.ayijk.commons.math.gen

/**
 * Created by ayijk on 2017/02/07.
 */
class TriangularDistributoin(val a: Double, val c: Double, val b: Double) : UniVariateGenerative {
  init {
    require(a < b && a <= c && c <= b, {
    })
  }

  /**
   * https://en.wikipedia.org/wiki/Triangular_distribution#Generating_Triangular-distributed_random_variates
   */
  override fun generate(): Double {
    val uni = UniformDistribution().generate()
    val f = (c - a) / (b - a)

    return if (uni < f) {
      a + Math.sqrt(uni * (b - a) * (c - a))
    } else {
      b - Math.sqrt((1 - uni) * (b - a) * (b - c))
    }
  }
}