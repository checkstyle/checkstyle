package com.google.checkstyle.test.chapter7javadoc.rule713atclauses;

/**
 * Record with wrong tag order.
 *
 * @deprecated use NewPoint
 * @param x the x coordinate // violation 'Block tags have to appear in the order'
 * @param y the y coordinate // violation 'Block tags have to appear in the order'
 */
record InputIncorrectAtClauseOrderCheckOnRecordAndCompactCtor(int x, int y) {

  /**
   * Some text.
   *
   * @deprecated old method
   * @param val some text // violation 'Block tags have to appear in the order'
   * @return some text // violation 'Block tags have to appear in the order'
   */
  static String method(String val) {
    return val;
  }

  /**
   * Generic record with wrong tag order.
   *
   * @deprecated use other record
   * @param <T> the type // violation 'Block tags have to appear in the order'
   * @param name the name // violation 'Block tags have to appear in the order'
   */
  record InnerGenericRecord<T>(String name) {}

  /**
   * Record with compact ctor that has wrong tag order.
   *
   * @param lo the lower bound
   * @param hi the upper bound
   */
  record InnerRecordWithCompactCtor(int lo, int hi) {
    /**
     * Validates range.
     *
     * @deprecated use factory method
     * @param lo the lower bound // violation 'Block tags have to appear in the order'
     * @param hi the upper bound // violation 'Block tags have to appear in the order'
     * @throws Exception if error // violation 'Block tags have to appear in the order'
     */
    InnerRecordWithCompactCtor {
      if (lo > hi) {
        throw new IllegalArgumentException();
      }
    }
  }
}
