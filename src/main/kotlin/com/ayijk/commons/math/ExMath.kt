package com.ayijk.commons.math

/**
 * Created by ayijk on 2017/01/28.
 */
val Double.Companion.EPS: Double
  get() {
    return 2 * Math.pow(10, -52)
  }

class Math {
  companion object
}

fun Math.Companion.pow(a: Int, b: Int): Double {
  return java.lang.Math.pow(a.toDouble(), b.toDouble())
}

fun Math.Companion.pow(a: Int, b: Double): Double {
  return java.lang.Math.pow(a.toDouble(), b)
}

fun Math.Companion.pow(a: Double, b: Int): Double {
  return java.lang.Math.pow(a, b.toDouble())
}

fun Math.Companion.pow(a: Double, b: Double): Double {
  return java.lang.Math.pow(a, b)
}