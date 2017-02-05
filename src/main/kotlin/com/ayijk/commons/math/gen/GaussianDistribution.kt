package com.ayijk.commons.math.gen

import com.ayijk.commons.math.linalg.DenseMatrix
import com.ayijk.commons.test.TestDone
import com.ayijk.commons.test.TestYet
import java.util.*

/**
 * Created by ayijk on 2017/02/04.
 */
class GaussianDistribution(mean: DenseMatrix, val cov: DenseMatrix) : UniVariateGenerative, MultiVariateGenerative {
  constructor(mean: DoubleArray, cov: DenseMatrix) : this(DenseMatrix(mean, mean.size), cov)
  constructor(mu: DoubleArray, cov: Array<DoubleArray>) : this(mu, DenseMatrix(cov))
  constructor(mu: DoubleArray, cov: Collection<DoubleArray>) : this(mu, DenseMatrix(cov.toTypedArray()))
  constructor(mu: DoubleArray, cov: Array<Array<Double>>) : this(mu, cov.map { it.toDoubleArray() }.toTypedArray())

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

  @TestDone
  override fun generate(): Double {
    require(dim == 1, {
    })

    return generateArray().first()
  }

  @TestDone
  override fun generateArray(): DoubleArray {
    val ret = DoubleArray(dim)

    val r = Random()
    (0 until dim).forEach { i ->
      ret[i] = mean[i, 0] + Math.sqrt(cov[i, i]) * r.nextGaussian()
    }

    return ret
  }

  /**
   * (2 pi)^{-dim/2} * |cov|^{-1/2} * exp(-1/2 * (x-mu)' cov^{-1} (x-mu))
   * ↓ln
   * (-dim/2) * ln(2pi) - 1/2 * det(|cov|) + (-1/2) * (x-mu)' cov^{-1} (x-mu)
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

      val ll = (-0.5 * dim) * Math.log(2 * Math.PI) +
          -0.5 * Math.log(cov.det()) +
          -0.5 * xsx.trace()
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