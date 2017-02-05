package com.ayijk.commons.math.linalg

import Jama.Matrix
import com.ayijk.commons.math.AssertUtils.assertEquals
import com.ayijk.commons.math.Maths
import org.junit.Test

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
  }

  @Test
  fun timesMatrixTest() {
    val m1 = DenseMatrix.rand(10, 5)
    val m2 = DenseMatrix.rand(5, 3)
    val m3 = m1 * m2

    assertEquals(MatrixSize(10, 3), m3.size)
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
  fun getTest() {
    val m = DenseMatrix((0 until 9).map(Int::toDouble).toList().toTypedArray(), 3).transpose()
    //println(m.makeString())

    val m2 = m[arrayListOf(2, 1), (0..1).toList()]
    //println(m2.makeString())
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
  fun unaryMinusTest() {
    val data = createRandomArrayArray(30, 40)
    val dm1 = DenseMatrix(data)
    val dm2 = DenseMatrix(data.map { it.map { -it }.toDoubleArray() }.toTypedArray())
    assertEquals(dm2, -dm1)
  }

  @Test
  fun eyeTest() {
    assertEquals(Matrix.identity(20, 20), DenseMatrix.eye(20))
    assertEquals(Matrix.identity(20, 30), DenseMatrix.eye(20, 30))
    assertEquals(Matrix.identity(30, 20), DenseMatrix.eye(30, 20))
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

  @Test
  fun detTest() {
    val dm1 = DenseMatrix(2, 2)
    dm1[0, 0] = 2
    dm1[0, 1] = 1
    dm1[1, 0] = 6
    dm1[1, 1] = 3
    assertEquals(0, dm1.det())

    val dm2 = DenseMatrix(2, 2)
    dm2[0, 0] = 2
    dm2[0, 1] = 1
    dm2[1, 0] = 6
    dm2[1, 1] = 0
    assertEquals(-6, dm2.det())

    val dm3 = DenseMatrix(3, 3)
    dm3[0, 0] = 1
    dm3[0, 1] = 2
    dm3[0, 2] = -1
    dm3[1, 0] = 10
    dm3[1, 1] = 3
    dm3[1, 2] = 4
    dm3[2, 0] = -5
    dm3[2, 1] = 2
    dm3[2, 2] = 3
    assertEquals(-134, dm3.det())

    (0 until 100).forEach {
      val data = createRandomArrayArray(20, 20)
      val m = Matrix(data)
      val dm = DenseMatrix(data)
      assertEquals(m.det(), dm.det(), Maths.pow(10, -5))
    }
  }

  private fun createRegularMatrix(n: Int): DenseMatrix {
    var ret: DenseMatrix
    do {
      val data = createRandomArrayArray(n, n)
      ret = DenseMatrix(data)
    } while (ret.inv() == null)

    return ret
  }

  @Test
  fun invTest() {
    val dm1 = createRegularMatrix(200)
    val eye = dm1 * requireNotNull(dm1.inv())
    eye.size.forEach {
      val i = it.first
      val j = it.second
      val expected = if (i == j) {
        1.0
      } else {
        0.0
      }
      assertEquals(expected, eye[i, j], Maths.pow(10, -5))
    }

    val dm2 = DenseMatrix(2, 2)
    dm2[0, 0] = 2
    dm2[0, 1] = 1
    dm2[1, 0] = 6
    dm2[1, 1] = 3
    assertEquals(null, dm2.inv())
  }

  @Test
  fun traceTest() {
    val data = createRandomArrayArray(30, 40)
    val dm1 = DenseMatrix(data)
    val m1 = Matrix(data)
    assertEquals(m1.trace(), dm1.trace())
    assertEquals(m1.transpose().trace(), dm1.transpose().trace())
  }

  @Test
  fun norm1Test() {
    val data = createRandomArrayArray(30, 40)
    val dm1 = DenseMatrix(data)
    val m1 = Matrix(data)
    assertEquals(m1.norm1(), dm1.norm1())
  }

  @Test
  fun norm2Test() {
    val data = createRandomArrayArray(30, 40)
    val dm1 = DenseMatrix(data)
    val m1 = Matrix(data)
    assertEquals(m1.norm2(), dm1.norm2())
  }

  @Test
  fun normInfTest() {
    val data = createRandomArrayArray(30, 40)
    val dm1 = DenseMatrix(data)
    val m1 = Matrix(data)
    assertEquals(m1.normInf(), dm1.normInf())
  }

  @Test
  fun normFTest() {
    val data = createRandomArrayArray(30, 40)
    val dm1 = DenseMatrix(data)
    val m1 = Matrix(data)
    assertEquals(m1.normF(), dm1.normF(), Maths.pow(10, -5))
  }

  @Test
  fun rankTest() {
    val data = createRandomArrayArray(30, 40)
    val dm1 = DenseMatrix(data)
    val m1 = Matrix(data)
    assertEquals(m1.rank(), dm1.rank())
  }

  @Test
  fun solveTest() {
    val data = createRandomArrayArray(30, 10)
    val ans = createRandomArrayArray(10, 30)
    val dm = DenseMatrix(data)
    val dmans = dm * DenseMatrix(ans)
    val m = Matrix(data)
    val mans = m * Matrix(ans)
    assertEquals(m.solve(mans), dm.solve(dmans))
  }
}