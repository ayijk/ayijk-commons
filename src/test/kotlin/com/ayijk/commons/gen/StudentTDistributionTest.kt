package com.ayijk.commons.gen

import com.ayijk.commons.math.AssertUtils.assertEquals
import com.ayijk.commons.math.Maths
import com.ayijk.commons.math.gen.StudentTDistribution
import com.ayijk.commons.math.linalg.DenseMatrix
import org.junit.Test

/**
 * Created by ayijk on 2017/02/05.
 */
class StudentTDistributionTest {
  @Test
  fun likelihoodTest() {
    val mu = doubleArrayOf(0.0)
    val variance = arrayOf(1.0)
    val dof = 2.0
    val cov = DenseMatrix(variance, 1)
    val gd = StudentTDistribution(mu, cov, dof)
    val pdf = requireNotNull(gd.likelihood(doubleArrayOf(5.0)))
    assertEquals(0.0071277811011064909199, pdf, Maths.pow(10, -10))
  }
}