package com.ayijk.commons.stats

import com.ayijk.commons.math.linalg.DenseMatrix

/**
 * Created by ayijk on 2017/02/03.
 */
object sum {
  // DenseMatrix
  operator fun invoke(v: Array<DenseMatrix>): DenseMatrix {
    return v.reduce { dm1, dm2 -> dm1 + dm2 }
  }

  // Double
  operator fun invoke(v: DoubleArray): Double {
    return v.sum()
  }

  operator fun invoke(v: Array<Double>): Double {
    return v.sum()
  }

  operator fun invoke(v: Collection<Double>): Double {
    return v.sum()
  }

  // Int
  operator fun invoke(v: IntArray): Int {
    return v.sum()
  }

  operator fun invoke(v: Array<Int>): Int {
    return v.sum()
  }

  operator fun invoke(v: Collection<Int>): Int {
    return v.sum()
  }

  // generic
  operator fun <T : Comparable<T>> invoke(v: Array<T>, f: (T, T) -> T): T {
    return v.reduce { t1, t2 -> f(t1, t2) }
  }

  operator fun <T : Comparable<T>> invoke(v: Collection<T>, f: (T, T) -> T): T {
    return v.reduce { t1, t2 -> f(t1, t2) }
  }
}