package com.ayijk.commons.math.linalg.dec

import Jama.Matrix
import com.ayijk.commons.math.linalg.DenseMatrix

/**
 * Created by ayijk on 2017/02/04.
 */
class QRDecomposition(mat: DenseMatrix) {
  private val qrd = Jama.QRDecomposition(Matrix(mat.array2Copy()))
  /**
   * Solve A*X = B
   */
  fun solve(B: DenseMatrix): DenseMatrix? {
    try {
      return DenseMatrix(qrd.solve(Matrix(B.array2Copy())).array)
    } catch (e: Exception) {
      return null
    }
  }
}