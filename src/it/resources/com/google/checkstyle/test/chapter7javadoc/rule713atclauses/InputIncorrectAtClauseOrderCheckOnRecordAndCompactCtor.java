package com.google.checkstyle.test.chapter7javadoc.rule713atclauses;

// violation 6 lines below 'Block tags have to appear in the order'
// violation 6 lines below 'Block tags have to appear in the order'
/**
 * Record with wrong tag order.
 *
 * @deprecated use NewPoint
 * @param x the x coordinate
 * @param y the y coordinate
 */
record InputIncorrectAtClauseOrderCheckOnRecordAndCompactCtor(int x, int y) {

  // violation 6 lines below 'Block tags have to appear in the order'
  // violation 6 lines below 'Block tags have to appear in the order'
  /**
   * Some text.
   *
   * @deprecated old method
   * @param val some text
   * @return some text
   */
  static String method(String val) {
    return val;
  }

  // violation 6 lines below 'Block tags have to appear in the order'
  // violation 6 lines below 'Block tags have to appear in the order'
  /**
   * Generic record with wrong tag order.
   *
   * @deprecated use other record
   * @param <T> the type
   * @param name the name
   */
  record InnerGenericRecord<T>(String name) {}

  /**
   * Record with compact ctor that has wrong tag order.
   *
   * @param lo the lower bound
   * @param hi the upper bound
   */
  record InnerRecordWithCompactCtor(int lo, int hi) {
    // violation 7 lines below 'Block tags have to appear in the order'
    // violation 7 lines below 'Block tags have to appear in the order'
    // violation 7 lines below 'Block tags have to appear in the order'
    /**
     * Validates range.
     *
     * @deprecated use factory method
     * @param lo the lower bound
     * @param hi the upper bound
     * @throws Exception if error
     */
    InnerRecordWithCompactCtor {
      if (lo > hi) {
        throw new IllegalArgumentException();
      }
    }
  }
}
