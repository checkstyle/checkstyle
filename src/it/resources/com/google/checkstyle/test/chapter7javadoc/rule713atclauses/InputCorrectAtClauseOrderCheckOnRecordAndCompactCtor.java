package com.google.checkstyle.test.chapter7javadoc.rule713atclauses;

/**
 * Record with correct tag order.
 *
 * @param x the x coordinate
 * @param y the y coordinate
 * @deprecated use NewPoint
 */
record InputCorrectAtClauseOrderCheckOnRecordAndCompactCtor(int x, int y) {

  /**
   * Some text.
   *
   * @param val some text
   * @return some text
   * @deprecated old method
   */
  static String method(String val) {
    return val;
  }

  /**
   * Generic record with correct tag order.
   *
   * @param <T> the type
   * @param name the name
   * @deprecated use other record
   */
  record InnerGenericRecord<T>(String name) {}

  /**
   * Record with compact ctor that has correct tag order.
   *
   * @param lo the lower bound
   * @param hi the upper bound
   */
  record InnerRecordWithCompactCtor(int lo, int hi) {
    /**
     * Validates range.
     *
     * @param lo the lower bound
     * @param hi the upper bound
     * @throws Exception if error
     * @deprecated use factory method
     */
    InnerRecordWithCompactCtor {
      if (lo > hi) {
        throw new IllegalArgumentException();
      }
    }
  }
}
