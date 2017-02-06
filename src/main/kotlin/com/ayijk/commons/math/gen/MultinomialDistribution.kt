package com.ayijk.commons.math.gen

import com.ayijk.commons.test.TestDone

/**
 * Created by ayijk on 2017/02/06.
 */
class MultinomialDistribution(val n: Int, ps: DoubleArray) : MultiVariateGenerative {
  val ps: DoubleArray
  val dim = ps.size

  init {
    require(n > 0, {
    })

    require(ps.none { it < 0 }, {
    })

    val sum = ps.sum()
    this.ps = ps.map { it / sum }.toDoubleArray()
  }

  /**
   * https://en.wikipedia.org/wiki/Multinomial_distribution#Sampling_from_a_multinomial_distribution
   */
  @TestDone
  override fun generateArray(): DoubleArray {
    val ret = DoubleArray(dim)

    val descPs = ps.withIndex().sortedByDescending { it.value }
    (0 until n).forEach {
      val uni = UniformDistribution()
      val x = uni.generate()

      var sum = 0.0
      for (k in 0 until dim) {
        sum += descPs[k].value
        if (sum >= x) {
          val index = descPs[k].index
          ret[index]++
          break
        }
      }
    }

    return ret
  }
}