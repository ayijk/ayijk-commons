package com.ayijk.commons.math

import Jama.Matrix
import com.ayijk.commons.math.linalg.DenseMatrix
import org.junit.Assert
import java.math.BigDecimal
import java.math.BigInteger

/**
 * Created by ayijk on 2017/01/29.
 */
object AssertUtils {
  fun assertEquals(expected: String, actual: Number): Unit {
    assertEquals(BigDecimal(expected), actual)
  }

  fun assertEquals(expected: Number, actual: Number): Unit {
    fun castToBigDecimal(number: Number): BigDecimal {
      return when (number) {
        is BigInteger -> BigDecimal(number)
        is BigDecimal -> number
        else -> BigDecimal(number.toDouble())
      }
    }

    Assert.assertEquals(castToBigDecimal(expected), castToBigDecimal(actual))
  }

  fun <T> assertEquals(expected: T, actual: T): Unit {
    Assert.assertEquals(expected, actual)
  }

  fun <T> assertEquals(expected: Array<out T>, actual: Array<out T>): Unit {
    Assert.assertArrayEquals(expected, actual)
  }

  fun assertEquals(expected: DoubleArray, actual: DoubleArray): Unit {
    Assert.assertArrayEquals(expected.toTypedArray(), actual.toTypedArray())
  }

  fun assertEquals(expected: Array<out DoubleArray>, actual: DenseMatrix) {
    Assert.assertArrayEquals(expected, actual.array2Copy())
  }

  fun assertEquals(expected: DenseMatrix, actual: DenseMatrix) {
    Assert.assertArrayEquals(expected.arrayCopy().toTypedArray(), actual.arrayCopy().toTypedArray())
  }

  fun assertEquals(expected: DenseMatrix, actual: Matrix) {
    Assert.assertArrayEquals(expected.array2Copy(), actual.arrayCopy)
  }

  fun assertEquals(expected: Matrix, actual: DenseMatrix) {
    Assert.assertArrayEquals(expected.arrayCopy, actual.array2Copy())
  }
}