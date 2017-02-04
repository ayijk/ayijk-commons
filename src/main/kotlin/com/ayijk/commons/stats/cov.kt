package com.ayijk.commons.stats

/**
 * Created by ayijk on 2017/02/03.
 */
object cov {
  enum class NaNFlag {
    IncludeNaN, OmitRows, PartialRows
  }

  enum class WeightForNormalize {
    Minus1, TotalNum
  }

  @Deprecated("???")
  operator fun invoke(data: DoubleArray, w: WeightForNormalize = WeightForNormalize.Minus1, flag: NaNFlag = NaNFlag.IncludeNaN): Double {
    if (data.isEmpty()) {
      return 0.0
    } else {
      return variance(data)
    }
  }
}