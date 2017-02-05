package com.ayijk.commons.math.gen

import net.goui.util.MTRandom

/**
 * Created by ayijk on 2017/02/05.
 */
class UniformDistribution : UniVariateGenerative {

  private val random by lazy {
    MTRandom()
  }

  fun generate(from: Number, to: Number): Double {
    val f = from.toDouble()
    val t = to.toDouble()
    require(t > f, {
    })

    return random.nextDouble() * (t - f) + f
  }

  fun generate(to: Number): Double {
    return generate(0, to)
  }

  override fun generate(): Double {
    return generate(1)
  }
}