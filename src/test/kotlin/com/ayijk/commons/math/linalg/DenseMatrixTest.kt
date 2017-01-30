package com.ayijk.commons.math.linalg

import Jama.Matrix
import com.ayijk.commons.math.AssertUtils.assertEquals
import org.junit.Test
import java.util.*

/**
 * Created by ayijk on 2017/01/29.
 */
class DenseMatrixTest {

  private fun createRandomArrayArray(rows: Int, cols: Int): Array<out DoubleArray> {
    return Matrix.random(rows, cols).array
  }

  @Test
  fun initializeTest() {
    val rows = 3
    val cols = 2
    val mat = DenseMatrix(rows, cols)

    var count = 0
    for (i in 0 until rows) {
      for (j in 0 until cols) {
        mat[i, j] = count++
      }
    }

    val m2 = mat.copy()
    m2[0, 0] = 100

    println(mat.makeString())
    println(m2.makeString())
  }

  @Test
  fun timesMatrixTest() {
    val m1 = DenseMatrix.rand(10, 5)
    val m2 = DenseMatrix.rand(5, 3)
    val m3 = m1 * m2

    assertEquals(MatrixSize(10, 3), m3.size)

    println(m3.makeString())
  }

  @Test
  fun array2CopyTest() {
    val data = createRandomArrayArray(10, 20)
    val m = DenseMatrix(data)
    assertEquals(data, m)
  }

  @Test
  fun arrayCopyTest() {
    val data = createRandomArrayArray(10, 20)
    val m1 = Matrix(data)
    val m2 = DenseMatrix(data)
    assertEquals(m1.columnPackedCopy, m2.arrayCopy())
  }

  @Test
  fun copyTest() {
    val m1 = DenseMatrix.rand(30, 20)
    val m2 = m1.copy()
    assertEquals(m1, m2)
  }

  @Test
  fun transposeTest() {
    val rows = 5
    val cols = 10
    val data = createRandomArrayArray(rows, cols)

    val m1 = Matrix(data)
    val m2 = DenseMatrix(data)

    assertEquals(m1.transpose(), m2.transpose())
  }

  @Test
  fun plusTest() {
    val data1 = createRandomArrayArray(10, 20)
    val data2 = createRandomArrayArray(10, 20)

    val dm1 = DenseMatrix(data1)
    val m1 = Matrix(data1)
    val dm2 = DenseMatrix(data2)
    val m2 = Matrix(data2)

    assertEquals(m1 + m2, dm1 + dm2)
    assertEquals(m1 + m1.copy().apply {
      for (i in 0 until rowDimension) {
        for (j in 0 until columnDimension) {
          this[i, j] = 10.0
        }
      }
    }, dm1 + 10)
  }

  @Test
  fun plusAssignTest() {
    val data1 = createRandomArrayArray(10, 20)
    val data2 = createRandomArrayArray(10, 20)

    val dm1 = DenseMatrix(data1)
    val dm2 = DenseMatrix(data2)
    dm1 += dm2

    val m1 = Matrix(data1)
    val m2 = Matrix(data2)

    assertEquals(m1 + m2, dm1)

    val dm3 = DenseMatrix(data1)
    dm3 += 10
    assertEquals(m1 + m1.copy().apply {
      for (i in 0 until rowDimension) {
        for (j in 0 until columnDimension) {
          this[i, j] = 10.0
        }
      }
    }, dm3)
  }

  @Test
  fun timesTest() {
    val data1 = createRandomArrayArray(10, 20)
    val data2 = createRandomArrayArray(20, 25)

    val dm1 = DenseMatrix(data1)
    val dm2 = DenseMatrix(data2)

    val m1 = Matrix(data1)
    val m2 = Matrix(data2)

    assertEquals(m1 * m2, dm1 * dm2)
  }
}