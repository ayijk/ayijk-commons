package com.ayijk.commons.math.gen

import com.ayijk.commons.math.Maths
import com.ayijk.commons.math.linalg.DenseMatrix
import com.ayijk.commons.test.TestYet

/**
 * Created by ayijk on 2017/02/05.
 */
class StudentTDistribution(mean: DenseMatrix, val cov: DenseMatrix, val dof: Double) : UniVariateGenerative, MultiVariateGenerative {
  constructor(mean: DoubleArray, cov: DenseMatrix, dof: Double) : this(DenseMatrix(mean, mean.size), cov, dof)
  constructor(mu: DoubleArray, cov: Array<DoubleArray>, dof: Double) : this(mu, DenseMatrix(cov), dof)
  constructor(mu: DoubleArray, cov: Collection<DoubleArray>, dof: Double) : this(mu, DenseMatrix(cov.toTypedArray()), dof)
  constructor(mu: DoubleArray, cov: Array<Array<Double>>, dof: Double) : this(mu, cov.map { it.toDoubleArray() }.toTypedArray(), dof)

  private val mean = if (mean.size.rows == 1 && mean.size.cols > 1) {
    mean.transpose()
  } else {
    mean.copy()
  }
  private val dim = mean.size.rows

  init {
    require(cov.size.rows == dim && cov.size.cols == dim, {
    })
    require(!cov.exists { i, j, v -> v < 0 }, {
    })
  }

  override fun generate(): Double {
    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun generateArray(): DoubleArray {
    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  /**
   * gamma((dof+dim)/2) / gamma(dof/2) / (dof*pi)^{dim/2} / |cov|^{1/2}
   * * (1 + 1/dof * (x-mean)' * cov^{-1} * (x-mean))^{-(dof+dim)/2}
   * ↓ln
   * loggamma((dof+dim)/2) - loggamma(dof/2) - (dim/2) * log(dof*pi) - 1/2 * log(|cov|)
   * - (dof+dim)/2 * log(1 + 1/dof * (x-mean)' * cov^{-1} *(x-mean))
   */
  @TestYet
  fun logLikelihood(values: DoubleArray): Double? {
    val x = DenseMatrix(values, values.size)
    val covInv = cov.inv()
    if (covInv == null) {
      return null
    } else {
      val xsx = (x - mean).transpose() * covInv * (x - mean)
      require(xsx.isScalar(), {})

      val ll = Maths.logGamma((dof + dim) / 2.0) - Maths.logGamma(dof / 2.0) +
          -(dim / 2.0) * Math.log(dof * Math.PI) - Math.log(cov.det()) / 2.0 +
          -(dof + dim) / 2.0 * Math.log(1 + xsx.trace() / dof)
      return ll
    }
  }

  @TestYet
  fun likelihood(values: DoubleArray): Double? {
    val ll = logLikelihood(values)
    if (ll == null) {
      return null
    } else {
      return Math.exp(ll)
    }
  }
}