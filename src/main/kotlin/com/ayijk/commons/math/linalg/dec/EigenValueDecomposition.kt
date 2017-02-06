package com.ayijk.commons.math.linalg.dec

import Jama.Matrix
import com.ayijk.commons.math.Maths
import com.ayijk.commons.math.linalg.DenseMatrix

/**
 * Created by ayijk on 2017/02/04.
 */
class EigenValueDecomposition(mat: DenseMatrix) {
  private val evd = Jama.EigenvalueDecomposition(Matrix(mat.array2Copy()))

  fun hasComplexEigenValues(): Boolean {
    return evd.imagEigenvalues.indices.none {
      Math.abs(evd.imagEigenvalues[it]) < Maths.pow(10, -12)
    }
  }

  fun realEigenValues(): DoubleArray {
    return evd.realEigenvalues
  }
}