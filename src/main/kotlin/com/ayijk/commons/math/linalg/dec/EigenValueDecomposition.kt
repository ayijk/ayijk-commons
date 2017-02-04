package com.ayijk.commons.math.linalg.dec

import Jama.Matrix
import com.ayijk.commons.math.linalg.DenseMatrix

/**
 * Created by ayijk on 2017/02/04.
 */
class EigenValueDecomposition(mat: DenseMatrix) {
  private val evd = Jama.EigenvalueDecomposition(Matrix(mat.array2Copy()))
}