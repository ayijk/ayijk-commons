package com.ayijk.commons.math.gen

import com.ayijk.commons.test.TestDone

/**
 * Created by ayijk on 2017/02/05.
 */
class ExponentialDistribution(val lambda: Double) : UniVariateGenerative {
  init {
    require(lambda > 0, {
    })
  }

  @TestDone
  override fun generate(): Double {
    val uni = UniformDistribution()
    return -Math.log(uni.generate()) / lambda
  }

}