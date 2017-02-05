package com.ayijk.commons.math

import com.ayijk.commons.test.TestDone
import com.ayijk.commons.test.TestYet
import java.math.BigDecimal
import java.math.BigInteger

/**
 * Created by ayijk on 2017/01/29.
 */
object Maths {
  val EPS = 2 * Maths.pow(10, -52)

  fun arsinh(x: Double): Double {
    return Math.log(x + Math.sqrt(x * x + 1.0))
  }

  fun arcosh(x: Double): Double {
    return Math.log(x + Math.sqrt(x * x - 1.0))
  }

  fun artanh(x: Double): Double {
    return 0.5 * (Math.log(1.0 + x) - Math.log(1.0 - x))
  }

  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
  // pow
  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
  @TestYet
  fun pow(a: Number, b: Number): Double {
    return Math.pow(a.toDouble(), b.toDouble())
  }

  @TestYet
  fun pow(a: BigInteger, b: Int): BigInteger {
    return a.pow(b)
  }

  @TestYet
  fun bigIntegerPow(a: Int, b: Int): BigInteger {
    return BigInteger.valueOf(a.toLong()).pow(b)
  }

  @TestYet
  fun bigDecimalPow(a: Double, b: Int): BigDecimal {
    return BigDecimal(a).pow(b)
  }

  @TestYet
  fun bigDecimalPow(a: Int, b: Int): BigDecimal {
    return bigDecimalPow(a.toDouble(), b)
  }
  //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
  // pow
  //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

  @TestYet
  fun logSumExp(array: DoubleArray): Double {
    var maxValue = -Double.MAX_VALUE
    array.indices.forEach { row ->
      if (maxValue < array[row]) {
        maxValue = array[row]
      }
    }

    var logsumexp = 0.0
    array.indices.forEach { row ->
      logsumexp += Math.exp(array[row] - maxValue)
    }

    return maxValue + Math.log(logsumexp)
  }

  @TestDone
  tailrec fun logFactorial(n: Int, f: Double = 0.0): Double {
    require(n >= 0, {
    })

    return when (n) {
      0 -> f
      else -> logFactorial(n - 1, Math.log(n.toDouble()) + f)
    }
  }

  /**
   * ベータ関数
   * Beta(a,b) = Γ(a) * Γ(b) / Γ(a+b)
   */
  @TestDone
  fun beta(a: Number, b: Number): Double {
    val input1 = a.toDouble()
    val input2 = b.toDouble()
    return Math.exp(logGamma(input1) + logGamma(input2) - logGamma(input1 + input2))
  }

  /**
   * http://www.java2s.com/Code/Java/Development-Class/LogGamma.htm
   */
  @TestDone
  fun logGamma(x: Number): Double {
    var input = x.toDouble()
    require(input > 0, {
    })

    var result: Double
    var y: Double
    var xnum: Double
    var xden: Double

    val d1 = -5.772156649015328605195174e-1
    val p1 = doubleArrayOf(4.945235359296727046734888e0, 2.018112620856775083915565e2, 2.290838373831346393026739e3, 1.131967205903380828685045e4, 2.855724635671635335736389e4, 3.848496228443793359990269e4, 2.637748787624195437963534e4, 7.225813979700288197698961e3)
    val q1 = doubleArrayOf(6.748212550303777196073036e1, 1.113332393857199323513008e3, 7.738757056935398733233834e3, 2.763987074403340708898585e4, 5.499310206226157329794414e4, 6.161122180066002127833352e4, 3.635127591501940507276287e4, 8.785536302431013170870835e3)
    val d2 = 4.227843350984671393993777e-1
    val p2 = doubleArrayOf(4.974607845568932035012064e0, 5.424138599891070494101986e2, 1.550693864978364947665077e4, 1.847932904445632425417223e5, 1.088204769468828767498470e6, 3.338152967987029735917223e6, 5.106661678927352456275255e6, 3.074109054850539556250927e6)
    val q2 = doubleArrayOf(1.830328399370592604055942e2, 7.765049321445005871323047e3, 1.331903827966074194402448e5, 1.136705821321969608938755e6, 5.267964117437946917577538e6, 1.346701454311101692290052e7, 1.782736530353274213975932e7, 9.533095591844353613395747e6)
    val d4 = 1.791759469228055000094023e0
    val p4 = doubleArrayOf(1.474502166059939948905062e4, 2.426813369486704502836312e6, 1.214755574045093227939592e8, 2.663432449630976949898078e9, 2.940378956634553899906876e10, 1.702665737765398868392998e11, 4.926125793377430887588120e11, 5.606251856223951465078242e11)
    val q4 = doubleArrayOf(2.690530175870899333379843e3, 6.393885654300092398984238e5, 4.135599930241388052042842e7, 1.120872109616147941376570e9, 1.488613728678813811542398e10, 1.016803586272438228077304e11, 3.417476345507377132798597e11, 4.463158187419713286462081e11)
    val c = doubleArrayOf(-1.910444077728e-03, 8.4171387781295e-04, -5.952379913043012e-04, 7.93650793500350248e-04, -2.777777777777681622553e-03, 8.333333333333333331554247e-02, 5.7083835261e-03)
    val a = 0.6796875

    if (input <= 0.5 || input > a && input <= 1.5) {
      if (input <= 0.5) {
        result = -Math.log(input)
        /*  Test whether X < machine epsilon. */
        if (input + 1 == 1.0) {
          return result
        }
      } else {
        result = 0.0
        input = input - 0.5 - 0.5
      }
      xnum = 0.0
      xden = 1.0
      for (i in 0 until 8) {
        xnum = xnum * input + p1[i]
        xden = xden * input + q1[i]
      }
      result += input * (d1 + input * (xnum / xden))
    } else if (input <= a || input > 1.5 && input <= 4) {
      if (input <= a) {
        result = -Math.log(input)
        input = input - 0.5 - 0.5
      } else {
        result = 0.0
        input -= 2
      }
      xnum = 0.0
      xden = 1.0
      for (i in 0 until 8) {
        xnum = xnum * input + p2[i]
        xden = xden * input + q2[i]
      }
      result += input * (d2 + input * (xnum / xden))
    } else if (input <= 12) {
      input -= 4
      xnum = 0.0
      xden = -1.0
      for (i in 0 until 8) {
        xnum = xnum * input + p4[i]
        xden = xden * input + q4[i]
      }
      result = d4 + input * (xnum / xden)
    } else {
      y = Math.log(input)
      result = input * (y - 1) - y * 0.5 + .9189385332046727417803297
      input = 1 / input
      y = input * input
      xnum = c[6]
      for (i in 0 until 6) {
        xnum = xnum * y + c[i]
      }
      xnum *= input
      result += xnum
    }/*  X > 12  */
    return result
  }
}