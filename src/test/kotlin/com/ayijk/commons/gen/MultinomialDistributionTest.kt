package com.ayijk.commons.gen

import com.ayijk.commons.math.AssertUtils.assertEquals
import com.ayijk.commons.math.Maths
import com.ayijk.commons.math.gen.MultinomialDistribution
import com.ayijk.commons.math.gen.UniformDistribution
import com.ayijk.commons.stats.meanAndVariance
import org.junit.Test

/**
 * Created by ayijk on 2017/02/05.
 */
class MultinomialDistributionTest {
  @Test
  fun generateTest() {
    val uni = UniformDistribution()
    val dim = 10
    val n = uni.generate(20).toInt() + 1
    val ps = DoubleArray(dim).map { uni.generate(10) }.toDoubleArray()

    val dist = MultinomialDistribution(n, ps)
    val results = (0 until 1000000).map {
      dist.generateArray()
    }

    val normPs = dist.ps
    (0 until dim).forEach { dimIndex ->
      val mv1 = meanAndVariance.MeanAndVariance(n * normPs[dimIndex],
          n * normPs[dimIndex] * (1.0 - normPs[dimIndex]),
          0)
      val mv2 = meanAndVariance(results.map { it[dimIndex] })
      assertEquals(0, Math.abs(mv1.mean - mv2.mean) / mv1.mean, Maths.pow(10, -2))
      assertEquals(0, Math.abs(mv1.std - mv2.std) / mv1.std, Maths.pow(10, -2))
    }
  }
}