package com.ayijk.commons.math.linalg.dec

import Jama.CholeskyDecomposition
import Jama.Matrix
import com.ayijk.commons.math.linalg.DenseMatrix

/**
 * Created by ayijk on 2017/02/04.
 */
class CholeskyDecomposition(mat: DenseMatrix) {
  private val cd = CholeskyDecomposition(Matrix(mat.array2Copy()))
}