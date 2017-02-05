package com.ayijk.commons.math.linalg

import com.ayijk.commons.math.linalg.dec.*
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

    @TestDone
    fun eye(n: Int): DenseMatrix {
      val ret = DenseMatrix(n, n)
      for (i in 0 until n) {
        ret[i, i] = 1
      }

      return ret
    }

    @TestDone
    fun eye(rows: Int, cols: Int): DenseMatrix {
      val ret = DenseMatrix(rows, cols)
      for (i in 0 until Math.min(rows, cols)) {
        ret[i, i] = 1
      }

      return ret
    }

    @TestDone
    fun eye(size: MatrixSize): DenseMatrix {
      return eye(size.rows, size.cols)
    }

    @TestYet
    fun diag(vs: DoubleArray): DenseMatrix {
      val len = vs.size
      val ret = DenseMatrix(len, len)
      (0 until len).forEach { i ->
        ret[i, i] = vs[i]
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

  private fun checkIndexRange(i: Int, j: Int) {
    require(i >= 0 && i < size.rows, {
      "dimension error. 'i' should be in [0, ${size.rows - 1}], but actually is $i."
    })
    require(j >= 0 && j < size.cols, {
      "dimension error. 'ｊ' should be in [0, ${size.cols - 1}], but actually is $j."
    })
  }

  val size: MatrixSize
  private val mat: DoubleArray

  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
  // Constructors
  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
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
  //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
  // Constructors
  //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
  // getter / setter
  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
  @TestYet
  operator fun get(rows: Collection<Int>, cols: Collection<Int>): DenseMatrix {
    val ret = DenseMatrix(rows.count(), cols.count())
    for (i in 0 until ret.size.rows) {
      for (j in 0 until ret.size.cols) {
        ret[i, j] = this[rows.elementAt(i), cols.elementAt(j)]
      }
    }

    return ret
  }

  @TestYet
  operator fun get(rows: IntRange, cols: IntRange): DenseMatrix {
    return this[rows.toList(), cols.toList()]
  }

  @TestYet
  operator fun get(i: Int, j: Int): Double {
    checkIndexRange(i, j)
    return mat[i + j * size.rows]
  }

  enum class RC {
    Row, Column,
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
    this[i, j] = value.toDouble()
  }

  @TestYet
  operator fun set(rc: RC, index: Int, values: DoubleArray): Unit {
    when (rc) {
      RC.Row -> {
        require(size.cols == values.size, {

        })
        for (j in 0 until size.cols) {
          this[index, j] = values[j]
        }
      }
      RC.Column -> {
        require(size.rows == values.size, {

        })
        for (i in 0 until size.rows) {
          this[i, index] = values[i]
        }
      }
    }
  }
  //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
  // getter / setter
  //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

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
        sb += "%.3f".format(this[i, j])
        if (j != size.cols - 1) {
          sb += delimiter
        }
      }
      sb += System.lineSeparator()
    }

    return sb.toString()
  }

  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
  // Math operations
  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
  @TestDone
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

  @TestYet
  fun timesAssignByElement(another: DenseMatrix): Unit {
    checkSize(size, another.size, Operator.Same)

    size.forEach {
      val i = it.first
      val j = it.second
      this[i, j] *= another[i, j]
    }
  }

  @TestYet
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
  //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
  // Math operations
  //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
  // Functional methods
  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
  fun exists(predicate: (Int, Int, Double) -> Boolean): Boolean {
    return this.count(predicate) > 0
  }

  fun count(predicate: (Int, Int, Double) -> Boolean): Int {
    return size.map { it ->
      val i = it.first
      val j = it.second
      val v = this[i, j]
      if (predicate.invoke(i, j, v)) {
        1
      } else {
        0
      }
    }.sum()
  }

  fun filter(predicate: (Int, Int, Double) -> Boolean): List<Triple<Int, Int, Double>> {
    val ret = arrayListOf<Triple<Int, Int, Double>>()
    size.forEach {
      val i = it.first
      val j = it.second
      val v = this[i, j]
      if (predicate(i, j, v)) {
        ret += Triple(i, j, v)
      }
    }

    return ret
  }

  fun map(transform: (Int, Int, Double) -> Double): DenseMatrix {
    val ret = this.copy()
    ret.size.forEach {
      val i = it.first
      val j = it.second
      val v = this[i, j]
      ret[i, j] = transform(i, j, v)
    }

    return ret
  }
  //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
  // Functional methods
  //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
  // Size validation
  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
  fun isSquare(): Boolean {
    return size.isSquare()
  }

  fun isVector(): Boolean {
    return size.isVector()
  }

  fun isScalar(): Boolean {
    return size.isScalar()
  }
  //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
  // Size validation
  //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
  // Decomposition
  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
  @TestYet
  fun cd(): CholeskyDecomposition {
    return CholeskyDecomposition(this)
  }

  @TestYet
  fun evd(): EigenValueDecomposition {
    return EigenValueDecomposition(this)
  }

  @TestYet
  fun lud(): LUDecomposition {
    return LUDecomposition(this)
  }

  @TestYet
  fun qrd(): QRDecomposition {
    return QRDecomposition(this)
  }

  @TestYet
  fun svd(): SingularValueDecomposition {
    return SingularValueDecomposition(this)
  }
  //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
  // Decomposition
  //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

  @TestDone
  fun transpose(): DenseMatrix {
    val ret = DenseMatrix(size.cols, size.rows)

    this.size.forEach {
      val i = it.first
      val j = it.second
      ret[j, i] = this[i, j]
    }

    return ret
  }

  @TestDone
  fun solve(B: DenseMatrix): DenseMatrix? {
    return if (isSquare()) {
      LUDecomposition(this).solve(B)
    } else {
      QRDecomposition(this).solve(B)
    }
  }

  @TestDone
  fun det(): Double {
    return LUDecomposition(this).det()
  }

  @TestDone
  fun inv(): DenseMatrix? {
    return this.solve(DenseMatrix.eye(size.rows, size.rows))
  }

  @TestDone
  fun trace(): Double {
    return (0 until Math.min(size.rows, size.cols)).map { i ->
      this[i, i]
    }.sum()
  }

  @TestDone
  fun rank(): Int {
    return SingularValueDecomposition(this).rank()
  }

  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
  // Matrix norm
  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
  /**
   * 1ノルム
   * 列sumの最大値
   */
  @TestDone
  fun norm1(): Double {
    val max = (0 until size.cols).map { j ->
      this[RC.Column, j].sum()
    }.max()

    return requireNotNull(max)
  }

  /**
   * 2ノルム
   * 最大特異値
   */
  @TestDone
  fun norm2(): Double {
    return SingularValueDecomposition(this).norm2()
  }

  /**
   * 無限ノルム
   * 行sumの最大値
   */
  @TestDone
  fun normInf(): Double {
    val max = (0 until size.rows).map { i ->
      this[RC.Row, i].sum()
    }.max()

    return requireNotNull(max)
  }

  /**
   * フロベニウスノルム
   */
  @TestDone
  fun normF(): Double {
    return Math.sqrt(mat.map { it * it }.sum())
  }
  //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
  // Matrix norm
  //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
}