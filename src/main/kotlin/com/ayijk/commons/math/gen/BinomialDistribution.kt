package com.ayijk.commons.math.gen

/**
 * Created by ayijk on 2017/02/05.
 */
class BinomialDistribution(val n: Int, val p: Double) : UniVariateGenerative {
  init {
    require(n > 0, {
    })

    require(p >= 0 && p <= 1, {
    })
  }

  /**
   * https://en.wikipedia.org/wiki/Binomial_distribution#Generating_binomial_random_variates
   */
  override fun generate(): Double {
    val log_q = Math.log(1.0 - p)
    var x = 0
    var sum = 0.0
    val uni = UniformDistribution()
    while (true) {
      sum += Math.log(uni.generate()) / (n - x)
      if (sum < log_q) {
        return x.toDouble()
      }
      x++
    }
  }
}