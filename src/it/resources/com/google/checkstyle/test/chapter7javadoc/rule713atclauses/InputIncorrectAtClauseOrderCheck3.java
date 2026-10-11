package com.google.checkstyle.test.chapter7javadoc.rule713atclauses;

import java.io.Serializable;

/**
 * Some javadoc.
 *
 * @since Some javadoc.
 * @version 1.0
 * @deprecated Some javadoc.
 * @see Some javadoc.
 * @author max
 */
class InputIncorrectAtClauseOrderCheck3 implements Serializable {

  // violation 6 lines below 'Block tags have to appear in the order .*'
  // violation 6 lines below 'Block tags have to appear in the order .*'
  /**
   * Some text.
   *
   * @deprecated Some text.
   * @return Some text.
   * @param str Some text.
   */
  String method5(String str) {
    return "null";
  }

  // violation 8 lines below 'Block tags have to appear in the order .*'
  // violation 9 lines below 'Block tags have to appear in the order .*'
  /**
   * Some text.
   *
   * @param str Some text.
   * @return Some text.
   * @serialData Some javadoc.
   * @param number Some text.
   * @throws Exception Some text.
   * @param bool Some text.
   * @deprecated Some text.
   */
  String method6(String str, int number, boolean bool) throws Exception {
    return "null";
  }

  /**
   * Some javadoc.
   *
   * @version 1.0
   * @since Some javadoc.
   * @serialData Some javadoc.
   * @author max
   */
  class InnerClassWithAnnotations3 {

    // violation 6 lines below 'Block tags have to appear in the order .*'
    /**
     * Some text.
     *
     * @param str Some text.
     * @deprecated Some text.
     * @return Some text.
     */
    String method5(String str) {
      return "null";
    }

    // violation 7 lines below 'Block tags have to appear in the order .*'
    // violation 8 lines below 'Block tags have to appear in the order .*'
    /**
     * Some text.
     *
     * @param str Some text.
     * @return Some text.
     * @param number Some text.
     * @throws Exception Some text.
     * @param bool Some text.
     * @deprecated Some text.
     */
    String method6(String str, int number, boolean bool) throws Exception {
      return "null";
    }
  }

  InnerClassWithAnnotations3 anon =
      new InnerClassWithAnnotations3() {

        // violation 6 lines below 'Block tags have to appear in the order .*'
        // violation 6 lines below 'Block tags have to appear in the order .*'
        /**
         * Some text.
         *
         * @deprecated Some text.
         * @return Some text.
         * @param str Some text.
         */
        String method5(String str) {
          return "null";
        }

        // violation 7 lines below 'Block tags have to appear in the order .*'
        // violation 8 lines below 'Block tags have to appear in the order .*'
        /**
         * Some text.
         *
         * @param str Some text.
         * @return Some text.
         * @param number Some text.
         * @throws Exception Some text.
         * @param bool Some text.
         * @deprecated Some text.
         */
        String method6(String str, int number, boolean bool) throws Exception {
          return "null";
        }
      };
}
