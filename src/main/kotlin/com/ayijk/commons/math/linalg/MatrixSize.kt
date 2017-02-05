package com.ayijk.commons.math.linalg

import kotlin.jvm.internal.iterator

/**
 * Created by ayijk on 2017/01/29.
 */
data class MatrixSize(val rows: Int, val cols: Int) : Iterable<Pair<Int, Int>> {
  val dim = rows * cols

  override fun iterator(): Iterator<Pair<Int, Int>> {
    val buffer = arrayListOf<Pair<Int, Int>>()
    for (row in 0 until rows) {
      for (col in 0 until cols) {
        buffer += Pair(row, col)
      }
    }

    return iterator(buffer.toTypedArray())
  }

  fun isSquare(): Boolean {
    return rows == cols
  }

  fun isVector(): Boolean {
    return rows == 1 || cols == 1
  }

  fun isScalar(): Boolean {
    return rows == 1 && cols == 1
  }
}