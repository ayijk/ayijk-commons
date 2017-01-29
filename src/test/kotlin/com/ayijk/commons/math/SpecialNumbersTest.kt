package com.ayijk.commons.math

import com.ayijk.commons.math.AssertUtils.assertEquals
import org.junit.Test

/**
 * Created by ayijk on 2017/01/28.
 */
class SpecialNumbersTest {
  @Test
  fun factTest() {
    assertEquals(1, SpecialNumbers.fact(0).toLong())
    assertEquals(1, SpecialNumbers.fact(1).toLong())
    assertEquals(2, SpecialNumbers.fact(2).toLong())
    assertEquals(3628800, SpecialNumbers.fact(10).toLong())
  }

  @Test
  fun permutationTest() {
    assertEquals(1, SpecialNumbers.permutation(0, 0))
    assertEquals(2, SpecialNumbers.permutation(2, 1))
    assertEquals("8450550186924629495838157093855404565441366722012461965560414732385728621597296137876420884621412468214583850753160522570648517967532382305454834505647607520427964189812887040005874546880020480000000000000000000000000",
        SpecialNumbers.permutation(200, 100))
  }

  @Test
  fun combinationTest() {
    assertEquals(1, SpecialNumbers.combination(0, 0))
    assertEquals(2, SpecialNumbers.combination(2, 1))
    assertEquals("90548514656103281165404177077484163874504589675413336841320",
        SpecialNumbers.combination(200, 100))
  }

  @Test
  fun stirlingNumber1stTest() {
    assertEquals(1, SpecialNumbers.stirlingNumber1st(0, 0))
    assertEquals(1764, SpecialNumbers.stirlingNumber1st(7, 2))
    assertEquals("6634460278534540725", SpecialNumbers.stirlingNumber1st(30, 20))
  }

  @Test
  fun stirlingNumber2ndTest() {
    assertEquals(1, SpecialNumbers.stirlingNumber2nd(0, 0))
    assertEquals(63, SpecialNumbers.stirlingNumber2nd(7, 2))
    assertEquals("581535955088511150", SpecialNumbers.stirlingNumber2nd(30, 20))
  }
}