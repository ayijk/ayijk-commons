package com.ayijk.commons.gen

import com.ayijk.commons.math.AssertUtils.assertEquals
import com.ayijk.commons.math.Maths
import com.ayijk.commons.math.gen.DirichletDistribution
import com.ayijk.commons.math.gen.UniformDistribution
import com.ayijk.commons.stats.meanAndVariance
import org.junit.Test

/**
 * Created by ayijk on 2017/02/05.
 */
class DirichletDistributionTest {
  @Test
  fun generateTest() {
    val dim = 10
    val uni = UniformDistribution()
    val alphas = DoubleArray(dim).map { uni.generate(10) }.toDoubleArray()

    val dist2 = DirichletDistribution(alphas)
    val results = (0 until 1000000).map {
      dist2.generateArray()
    }

    val alphas2 = dist2.alphas
    val sum = alphas2.sum()
    (0 until dim).forEach { dimIndex ->
      val mv1 = meanAndVariance.MeanAndVariance(alphas2[dimIndex] / sum,
          alphas2[dimIndex] * (sum - alphas2[dimIndex]) / (Maths.pow(sum, 2) * (sum + 1)),
          1)
      val mv2 = meanAndVariance(results.map { it[dimIndex] })
      assertEquals(0, Math.abs(mv1.mean - mv2.mean) / mv1.mean, Maths.pow(10, -2))
      assertEquals(0, Math.abs(mv1.std - mv2.std) / mv1.std, Maths.pow(10, -2))
    }
  }
}