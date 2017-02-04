package com.ayijk.commons.math.linalg.dec

import Jama.Matrix
import com.ayijk.commons.math.linalg.DenseMatrix


/**
 * Created by ayijk on 2017/02/03.
 */
class SingularValueDecomposition(mat: DenseMatrix) {
  private val svd = Jama.SingularValueDecomposition(Matrix.constructWithCopy(mat.array2Copy()))

  fun norm2(): Double {
    return svd.norm2()
  }

  fun rank(): Int {
    return svd.rank()
  }
}