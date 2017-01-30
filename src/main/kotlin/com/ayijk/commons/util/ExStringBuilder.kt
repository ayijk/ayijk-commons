package com.ayijk.commons.util

/**
 * Created by ayijk on 2017/01/29.
 */
operator fun StringBuilder.plusAssign(obj: Any): Unit {
  this.append(obj)
}
