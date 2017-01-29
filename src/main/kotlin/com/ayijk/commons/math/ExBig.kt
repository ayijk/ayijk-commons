package com.ayijk.commons.math

import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext

/**
 * Created by ayijk on 2017/01/28.
 */
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
// BigInteger
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
operator fun BigInteger.plus(another: BigInteger): BigInteger {
  return this.add(another)
}

operator fun BigInteger.plus(another: Long): BigInteger {
  return plus(another.toBigInteger())
}

operator fun BigInteger.plus(another: Int): BigInteger {
  return plus(another.toLong())
}

/*
operator fun BigInteger.minus(another: BigInteger): BigInteger {
  return this.add(another * -1)
}
*/

operator fun BigInteger.minus(another: Long): BigInteger {
  return minus(another.toBigInteger())
}

operator fun BigInteger.minus(another: Int): BigInteger {
  return minus(another.toLong())
}

operator fun BigInteger.times(another: BigInteger): BigInteger {
  return this.multiply(another)
}

operator fun BigInteger.times(another: Long): BigInteger {
  return times(another.toBigInteger())
}

operator fun BigInteger.times(another: Int): BigInteger {
  return times(another.toLong())
}

val Iterable<BigInteger>.sum: BigInteger
  get() {
    return this.reduce { it1, it2 -> it1 + it2 }
  }


//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
// BigDecimal
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
operator fun BigDecimal.plus(another: BigDecimal): BigDecimal {
  return this.add(another)
}

operator fun BigDecimal.plus(another: BigInteger): BigDecimal {
  return this + another.toBigDecimal()
}

operator fun BigDecimal.plus(another: Double): BigDecimal {
  return this + another.toBigDecimal()
}

operator fun BigDecimal.plus(another: Int): BigDecimal {
  return this + another.toDouble()
}

operator fun BigDecimal.times(another: BigDecimal): BigDecimal {
  return this.multiply(another)
}

operator fun BigDecimal.times(another: BigInteger): BigDecimal {
  return this * another.toBigDecimal()
}

operator fun BigDecimal.times(another: Double): BigDecimal {
  return this * another.toBigDecimal()
}

operator fun BigDecimal.times(another: Int): BigDecimal {
  return this * another.toDouble()
}

operator fun BigDecimal.div(another: BigDecimal): BigDecimal {
  return divide(another, MathContext.DECIMAL128)
}

operator fun BigDecimal.div(another: BigInteger): BigDecimal {
  return this / another.toBigDecimal()
}

operator fun BigDecimal.div(another: Double): BigDecimal {
  return this / another.toBigDecimal()
}

operator fun BigDecimal.div(another: Int): BigDecimal {
  return this / another.toDouble()
}

val Iterable<BigDecimal>.sum: BigDecimal
  get() {
    return this.reduce { it1, it2 -> it1 + it2 }
  }

// BigInteger to BigDecimal
operator fun BigInteger.plus(another: BigDecimal): BigDecimal {
  return another + this
}

operator fun BigInteger.minus(another: BigDecimal): BigDecimal {
  return -another + this
}

operator fun BigInteger.times(another: BigDecimal): BigDecimal {
  return another * this
}

// Double to BigDecimal
operator fun Double.plus(another: BigDecimal): BigDecimal {
  return another + this
}

operator fun Double.minus(another: BigDecimal): BigDecimal {
  return -another + this
}

operator fun Double.times(another: BigDecimal): BigDecimal {
  return another * this
}

operator fun Double.div(another: BigDecimal): BigDecimal {
  return BigDecimal(this) / another
}

// Int to BigDecimal
operator fun Int.plus(another: BigDecimal): BigDecimal {
  return another + this
}

operator fun Int.minus(another: BigDecimal): BigDecimal {
  return -another + this
}

operator fun Int.times(another: BigDecimal): BigDecimal {
  return another * this
}

operator fun Int.div(another: BigDecimal): BigDecimal {
  return this.toBigDecimal() / another
}

// Int to BigInteger
operator fun Int.plus(another: BigInteger): BigInteger {
  return another + this
}

operator fun Int.minus(another: BigInteger): BigInteger {
  return -another + this
}

operator fun Int.times(another: BigInteger): BigInteger {
  return another * this
}

/*
// log / exp
fun BigDecimal.log(): BigDecimal {
  return spire.std.BigDecimalIsTrig(MathContext.DECIMAL128).log(scala.math.BigDecimal(this)).bigDecimal()
}

fun BigDecimal.exp(): BigDecimal {
  return spire.std.BigDecimalIsTrig(MathContext.DECIMAL128).exp(scala.math.BigDecimal(this)).bigDecimal()
}
*/

// others
fun Double.toBigDecimal(): BigDecimal {
  return BigDecimal(this)
}

fun Long.toBigInteger(): BigInteger {
  return BigInteger.valueOf(this)
}

fun BigInteger.toBigDecimal(): BigDecimal {
  return BigDecimal(this)
}

fun Int.toBigDecimal(): BigDecimal {
  return BigDecimal(this)
}