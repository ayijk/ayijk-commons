package com.ayijk.commons.math.op

import com.ayijk.commons.math.linalg.DenseMatrix

/**
 * Created by ayijk on 2017/02/05.
 */
// Double operate
operator fun Double.plus(other: DenseMatrix): DenseMatrix {
  return other + this
}

operator fun Double.minus(other: DenseMatrix): DenseMatrix {
  return -other + this
}

operator fun Double.times(other: DenseMatrix): DenseMatrix {
  return other * this
}