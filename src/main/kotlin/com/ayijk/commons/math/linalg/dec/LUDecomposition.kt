package com.ayijk.commons.math.linalg.dec

import Jama.Matrix
import com.ayijk.commons.math.linalg.DenseMatrix
import com.ayijk.commons.test.TestYet


/**
 * Created by ayijk on 2017/01/31.
 */
class LUDecomposition(A: DenseMatrix) {
  private val size = A.size
  private val lud = Jama.LUDecomposition(Matrix(A.array2Copy()))

  @TestYet
  fun getPivot(): DenseMatrix {
    val ret = DenseMatrix(size.rows, size.rows)
    for (i in 0 until size.rows) {
      ret[i, lud.pivot.indexOf(i)] = 1.0
    }

    return ret
  }

  /**
   * Is the matrix nonsingular?
   * @return true if U, and hence A, is nonsingular.
   */
  fun isNonSingular(): Boolean {
    return lud.isNonsingular
  }

  /**
   * Return lower triangular factor
   * @return L
   */
  fun getL(): DenseMatrix {
    return DenseMatrix(lud.l.array)
  }

  /**
   * Return upper triangular factor
   * @return U
   */
  fun getU(): DenseMatrix {
    return DenseMatrix(lud.u.array)
  }

  /**
   * Determinant
   * @return det(A)
   * *
   * @exception IllegalArgumentException
   * *                Matrix must be square
   */
  fun det(): Double {
    return lud.det()
  }


  /**
   * Solve A*X = B
   */
  fun solve(B: DenseMatrix): DenseMatrix? {
    try {
      return DenseMatrix(lud.solve(Matrix(B.array2Copy())).array)
    } catch (e: Exception) {
      return null
    }
  }
}