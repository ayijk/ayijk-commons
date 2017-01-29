package com.ayijk.commons.math

/**
 * Created by ayijk on 2017/01/28.
 */
val Double.Companion.EPS: Double
  get() {
    return 2 * Maths.pow(10, -52)
  }
