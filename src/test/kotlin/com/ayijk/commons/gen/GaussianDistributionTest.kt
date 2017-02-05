package com.ayijk.commons.gen

import com.ayijk.commons.math.AssertUtils.assertEquals
import com.ayijk.commons.math.Maths
import com.ayijk.commons.math.gen.GaussianDistribution
import com.ayijk.commons.math.linalg.DenseMatrix
import com.ayijk.commons.stats.meanAndVariance
import org.junit.Test
import java.util.*

/**
 * Created by ayijk on 2017/02/05.
 */
class GaussianDistributionTest {
  @Test
  fun generateTest() {
    val dim = 7
    val r = Random()
    val mean = DoubleArray(dim).withIndex().map { r.nextDouble() * 50 }.toDoubleArray()
    val variance = DoubleArray(dim).withIndex().map { r.nextDouble() * 100 }.toDoubleArray()
    val cov = DenseMatrix.diag(variance)

    val gd = GaussianDistribution(mean, cov)
    val results = (0 until 100000).map {
      gd.generateArray()
    }
    (0 until dim).forEach { dimIndex ->
      val mv = meanAndVariance(results.map { it[dimIndex] })
      assertEquals(mean[dimIndex], mv.mean, Maths.pow(10, -1))
      assertEquals(Math.sqrt(variance[dimIndex]), mv.std, Maths.pow(10, -1))
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