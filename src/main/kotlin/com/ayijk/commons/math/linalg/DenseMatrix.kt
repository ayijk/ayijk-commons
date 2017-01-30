package com.ayijk.commons.math.linalg

import com.ayijk.commons.test.TestDone
import com.ayijk.commons.test.TestYet
import com.ayijk.commons.util.plusAssign
import java.util.*

/**
 * Created by ayijk on 2017/01/29.
 */
class DenseMatrix {
  companion object {
    fun rand(rows: Int, cols: Int): DenseMatrix {
      val r = Random()

      val ret = DenseMatrix(rows, cols)
      for (i in 0 until rows) {
        for (j in 0 until cols) {
          ret[i, j] = r.nextDouble()
        }
      }

      return ret
    }

    enum class Operator {
      Same, Multipliable,
    }

    private fun checkSize(size1: MatrixSize, size2: MatrixSize, operator: Operator): Unit {
      when (operator) {
        Operator.Same -> {
          require(size1 == size2, {
            "dimension should be the same."
          })
        }
        Operator.Multipliable -> {
          require(size1.cols == size2.rows, {
            "col of mat1 should be the same as row of mat2."
          })
        }
      }
    }
  }

  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
  // Matrix operator overload
  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
  private fun checkIndexRange(i: Int, j: Int) {
    require(i >= 0 && i < size.rows, {
      "dimension error. 'i' should be in [0, ${size.rows - 1}], but actually is $i."
    })
    require(j >= 0 && j < size.cols, {
      "dimension error. 'ｊ' should be in [0, ${size.cols - 1}], but actually is $j."
    })
  }

  enum class RC {
    Row, Column,
  }

  @TestYet
  operator fun get(rows: IntRange, cols: IntRange): DenseMatrix {
    val ret = DenseMatrix(rows.count(), cols.count())
    for (i in 0 until ret.size.rows) {
      for (j in 0 until ret.size.cols) {
        ret[i, j] = this[rows.elementAt(i), cols.elementAt(j)]
      }
    }

    return ret
  }

  @TestYet
  operator fun get(i: Int, j: Int): Double {
    checkIndexRange(i, j)
    return mat[i + j * size.rows]
  }

  @TestYet
  operator fun get(rc: RC, index: Int): DoubleArray {
    val ret: DoubleArray
    when (rc) {
      RC.Row -> {
        ret = DoubleArray(size.cols)
        for (j in 0 until size.cols) {
          ret[j] = this[index, j]
        }
      }
      RC.Column -> {
        ret = DoubleArray(size.rows)
        for (i in 0 until size.rows) {
          ret[i] = this[i, index]
        }
      }
    }

    return ret
  }

  @TestYet
  operator fun set(i: Int, j: Int, value: Double): Unit {
    checkIndexRange(i, j)
    mat[i + j * size.rows] = value
  }

  @TestYet
  operator fun set(i: Int, j: Int, value: Int): Unit {
    checkIndexRange(i, j)
    mat[i + j * size.rows] = value.toDouble()
  }
  //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
  // Matrix operator overload
  //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

  val size: MatrixSize
  private val mat: DoubleArray

  @TestDone
  fun arrayCopy(): DoubleArray {
    return mat.copyOf()
  }

  @TestDone
  fun array2Copy(): Array<out DoubleArray> {
    val ret = Array(size.rows, { DoubleArray(size.cols) })
    size.forEach {
      val i = it.first
      val j = it.second
      ret[i][j] = this[i, j]
    }

    return ret
  }

  constructor(rows: Int, cols: Int) {
    this.size = MatrixSize(rows, cols)
    this.mat = DoubleArray(size.dim)
  }

  constructor(size: MatrixSize) {
    this.size = size
    this.mat = DoubleArray(size.dim)
  }

  constructor(values: DoubleArray, rows: Int) {
    this.size = MatrixSize(rows, values.size / rows)
    this.mat = DoubleArray(size.dim)
    for (i in 0 until values.size) {
      this.mat[i] = values[i]
    }
  }

  constructor(values: Array<out Double>, rows: Int) : this(values.toDoubleArray(), rows)

  constructor(data: Array<out DoubleArray>) {
    this.size = MatrixSize(data.size, data.first().size)
    this.mat = DoubleArray(size.dim)
    for (i in 0 until size.rows) {
      for (j in 0 until size.cols) {
        this[i, j] = data[i][j]
      }
    }
  }

  @TestDone
  fun copy(): DenseMatrix {
    val copy = DenseMatrix(size)
    size.forEach {
      val row = it.first
      val col = it.second
      copy[row, col] = this[row, col]
    }

    return copy
  }

  @TestYet
  fun makeString(delimiter: String = "    "): String {
    val sb = StringBuilder()
    (0 until size.rows).forEach { i ->
      (0 until size.cols).forEach { j ->
        sb += this[i, j]
        if (j != size.cols - 1) {
          sb += delimiter
        }
      }
      sb += System.lineSeparator()
    }

    return sb.toString()
  }

  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
  // math operation
  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
  @TestYet
  operator fun unaryMinus(): DenseMatrix {
    return this * -1
  }

  @TestDone
  operator fun plus(another: DenseMatrix): DenseMatrix {
    checkSize(size, another.size, Operator.Same)

    val ret = this.copy()
    for (i in 0 until size.dim) {
      ret.mat[i] += another.mat[i]
    }

    return ret
  }

  @TestDone
  operator fun plus(another: Double): DenseMatrix {
    val ret = this.copy()
    for (i in 0 until size.dim) {
      ret.mat[i] += another
    }

    return ret
  }

  @TestYet
  operator fun plus(another: Int): DenseMatrix {
    return this + another.toDouble()
  }

  @TestYet
  operator fun plusAssign(another: DenseMatrix): Unit {
    checkSize(size, another.size, Operator.Same)

    for (i in 0 until size.dim) {
      mat[i] += another.mat[i]
    }
  }

  @TestYet
  operator fun plusAssign(another: Double): Unit {
    for (i in 0 until size.dim) {
      mat[i] += another
    }
  }

  @TestYet
  operator fun plusAssign(another: Int): Unit {
    this += another.toDouble()
  }

  @TestYet
  operator fun minus(another: DenseMatrix): DenseMatrix {
    checkSize(size, another.size, Operator.Same)

    val ret = this.copy()
    for (i in 0 until size.dim) {
      ret.mat[i] -= another.mat[i]
    }

    return ret
  }

  @TestYet
  operator fun minus(another: Double): DenseMatrix {
    return this + (-another)
  }

  @TestYet
  operator fun minus(another: Int): DenseMatrix {
    return this + (-another)
  }

  @TestYet
  operator fun minusAssign(another: DenseMatrix): Unit {
    for (i in 0 until size.dim) {
      mat[i] -= another.mat[i]
    }
  }

  @TestYet
  operator fun minusAssign(another: Double): Unit {
    this += (-another)
  }

  @TestYet
  operator fun minusAssign(another: Int): Unit {
    this -= another.toDouble()
  }

  @TestYet
  operator fun times(another: DenseMatrix): DenseMatrix {
    checkSize(size, another.size, Operator.Multipliable)

    val ret = DenseMatrix(size.rows, another.size.cols)
    ret.size.forEach {
      val i = it.first
      val j = it.second

      ret[i, j] = this[RC.Row, i].zip(another[RC.Column, j]).map { it.first * it.second }.sum()
    }

    return ret
  }

  @TestYet
  operator fun times(scale: Double): DenseMatrix {
    val ret = this.copy()
    for (i in 0 until size.dim) {
      ret.mat[i] = mat[i] * scale
    }

    return ret
  }

  @TestYet
  operator fun times(scale: Int): DenseMatrix {
    return this * scale.toDouble()
  }

  @TestYet
  operator fun timesAssign(another: Double): Unit {
    for (i in 0 until size.dim) {
      mat[i] *= another
    }
  }

  @TestYet
  operator fun timesAssign(another: Int): Unit {
    this *= another.toDouble()
  }

  @TestYet
  fun timesByElement(another: DenseMatrix): DenseMatrix {
    checkSize(size, another.size, Operator.Same)

    val ret = this.copy()
    ret.size.forEach {
      val i = it.first
      val j = it.second
      ret[i, j] *= another[i, j]
    }

    return ret
  }

  infix fun `*`(another: DenseMatrix): DenseMatrix {
    return this.timesByElement(another)
  }

  @TestYet
  fun timesAssignByElement(another: DenseMatrix): Unit {
    checkSize(size, another.size, Operator.Same)

    size.forEach {
      val i = it.first
      val j = it.second
      this[i, j] *= another[i, j]
    }
  }

  infix fun `*=`(another: DenseMatrix): Unit {
    this.timesAssignByElement(another)
  }

  fun divByElement(another: DenseMatrix): DenseMatrix {
    checkSize(size, another.size, Operator.Same)

    val ret = this.copy()
    ret.size.forEach {
      val i = it.first
      val j = it.second
      ret[i, j] /= another[i, j]
    }

    return ret
  }

  infix fun `--`(another: DenseMatrix): DenseMatrix {
    return this.divByElement(another)
  }
  //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
  // math operation
  //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

  @TestYet
  fun transpose(): DenseMatrix {
    val ret = DenseMatrix(size.cols, size.rows)

    this.size.forEach {
      val i = it.first
      val j = it.second
      ret[j, i] = this[i, j]
    }

    return ret
  }

  fun inv(): DenseMatrix {
    return this
  }
}