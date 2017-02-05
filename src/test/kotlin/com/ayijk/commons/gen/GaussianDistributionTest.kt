package com.ayijk.commons.gen

import com.ayijk.commons.math.AssertUtils.assertEquals
import com.ayijk.commons.math.Maths
import com.ayijk.commons.math.gen.GaussianDistribution
import com.ayijk.commons.math.gen.UniformDistribution
import com.ayijk.commons.math.linalg.DenseMatrix
import com.ayijk.commons.math.linalg.dec.EigenValueDecomposition
import com.ayijk.commons.stats.meanAndVariance
import org.apache.commons.math3.distribution.MultivariateNormalDistribution
import org.junit.Test

/**
 * Created by ayijk on 2017/02/05.
 */
class GaussianDistributionTest {
  @Test
  fun generateTest() {
    val uni = UniformDistribution()
    val dim = 7
    val mean = DoubleArray(dim).withIndex().map { uni.generate(30) }.toDoubleArray()
    val cov = DenseMatrix(dim, dim).apply {
      var evd: EigenValueDecomposition
      do {
        size.forEach {
          val i = it.first
          val j = it.second
          if (i <= j) {
            this[i, j] = uni.generate()
            this[j, i] = this[i, j]
          }
        }
        evd = this.evd()
      } while (evd.hasComplexEigenValues() || !evd.realEigenValues().none { it < 0 })
    }

    val dist1 = MultivariateNormalDistribution(mean, cov.array2Copy())
    val dist2 = GaussianDistribution(mean, cov)
    val results = (0 until 1000000).map {
      dist1.sample() to dist2.generateArray()
    }

    (0 until dim).forEach { dimIndex ->
      val mv1 = meanAndVariance(results.map { it.first[dimIndex] })
      val mv2 = meanAndVariance(results.map { it.second[dimIndex] })
      assertEquals(0, Math.abs(mv1.mean - mv2.mean) / mv1.mean, Maths.pow(10, -2))
      assertEquals(0, Math.abs(mv1.std - mv2.std) / mv1.std, Maths.pow(10, -2))
    }
  }

  @Test
  fun likelihood1dTest() {
    val mu = doubleArrayOf(10.0)
    val variance = arrayOf(16.0)
    val cov = DenseMatrix(variance, 1)
    val gd = GaussianDistribution(mu, cov)
    val l = requireNotNull(gd.likelihood(doubleArrayOf(14.0)))
    assertEquals(0.0604926811, l, Maths.pow(10, -10))
  }
}