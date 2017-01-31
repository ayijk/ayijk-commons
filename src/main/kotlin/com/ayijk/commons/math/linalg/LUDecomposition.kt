package com.ayijk.commons.math.linalg


/**
 * Created by ayijk on 2017/01/31.
 * http://www.java2s.com/Code/Java/Collections-Data-Structure/LUDecomposition.htm
 */
class LUDecomposition(A: DenseMatrix) {
  private val LU: DenseMatrix = A.copy()
  private val size: MatrixSize = A.size

  private val piv: IntArray = (0 until size.rows).toList().toIntArray()

  init {
    // Use a "left-looking", dot-product, Crout/Doolittle algorithm.

    // Outer loop.
    for (j in 0 until size.cols) {
      // Apply previous transformations.
      for (i in 0 until size.rows) {
        // Most of the time is spent in the following dot product.
        val s = (0 until Math.min(i, j)).map { LU[i, it] * LU[it, j] }.sum()

        LU[i, j] -= s
      }

      // Find pivot and exchange if necessary.
      val p = LU[DenseMatrix.RC.Column, j].withIndex()
          .filter { it.index > j }
          .sortedBy { -Math.abs(it.value) }
          .firstOrNull()
          ?.index.let { j }

      if (p != j) {
        for (k in 0 until size.cols) {
          val tmp = LU[p, k]
          LU[p, k] = LU[j, k]
          LU[j, k] = tmp
        }
        val tmp = piv[p]
        piv[p] = piv[j]
        piv[j] = tmp
      }

      // Compute multipliers.
      if ((j < size.rows) && (LU[j, j] != 0.0)) {
        for (i in j + 1 until size.rows) {
          LU[i, j] /= LU[j, j]
        }
      }
    }
  }

  fun getPivot(): DenseMatrix {
    val ret = DenseMatrix(size.rows, size.rows)
    for (i in 0 until size.rows) {
      ret[i, piv.indexOf(i)] = 1.0
    }

    return ret
  }

  /**
   * Is the matrix nonsingular?

   * @return true if U, and hence A, is nonsingular.
   */
  fun isNonsingular(): Boolean {
    for (j in 0 until size.cols) {
      if (LU[j, j] == 0.0) {
        return false
      }
    }

    return true
  }

  /**
   * Return lower triangular factor

   * @return L
   */
  fun getL(): DenseMatrix {
    val L = DenseMatrix(size)
    for (i in 0 until size.rows) {
      for (j in 0 until size.cols) {
        if (i > j) {
          L[i, j] = LU[i, j]
        } else if (i == j) {
          L[i, j] = 1.0
        } else {
          L[i, j] = 0.0
        }
      }
    }

    return L
  }

  /**
   * Return upper triangular factor

   * @return U
   */
  fun getU(): DenseMatrix {
    val U = DenseMatrix(size.cols, size.cols)
    for (i in 0 until size.cols) {
      for (j in 0 until size.cols) {
        if (i <= j) {
          U[i, j] = LU[i, j]
        } else {
          U[i, j] = 0.0
        }
      }
    }

    return U
  }

  /**
   * Determinant

   * @return det(A)
   * *
   * @exception IllegalArgumentException
   * *                Matrix must be square
   */
  fun det(): Double {
    require(size.isSquare(), {
      "Matrix must be square."
    })

    var d = 1.0
    for (j in 0 until size.cols) {
      d *= LU[j, j]
    }

    return d
  }


  /**
   * Solve A*X = B

   * @param B
   * *            A Matrix with as many rows as A and any number of columns.
   * *
   * @return X so that L*U*X = B(piv,:)
   * *
   * @exception IllegalArgumentException
   * *                Matrix row dimensions must agree.
   * *
   * @exception RuntimeException
   * *                Matrix is singular.
   */
  fun solve(B: DenseMatrix): DenseMatrix? {
    require(B.size.rows == size.rows, {
      "Matrix row dimensions must agree."
    })

    // 特異解なら計算できない
    if (!this.isNonsingular()) {
      println("Matrix is singular.")
      return null
    }

    // Copy right hand side with pivoting
    val nx = B.size.cols
    val X = B[piv.toList(), (0 until nx).toList()]

    // Solve L*Y = B(piv,:)
    for (k in 0 until size.cols) {
      for (i in k + 1 until size.cols) {
        for (j in 0 until nx) {
          X[i, j] -= X[k, j] * LU[i, k]
        }
      }
    }

    // Solve U*X = Y;
    for (k in size.cols - 1 downTo 0) {
      for (j in 0 until nx) {
        X[k, j] /= LU[k, k]
      }
      for (i in 0 until k) {
        for (j in 0 until nx) {
          X[i, j] -= X[k, j] * LU[i, k]
        }
      }
    }

    return X
  }
}