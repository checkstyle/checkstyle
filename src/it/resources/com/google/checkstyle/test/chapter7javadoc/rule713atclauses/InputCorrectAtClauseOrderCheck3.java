package com.google.checkstyle.test.chapter7javadoc.rule713atclauses;

import java.io.Serializable;

/**
 * Some javadoc.
 *
 * @author max
 * @version 1.0
 * @see Some javadoc.
 * @since Some javadoc.
 * @deprecated Some javadoc.
 */
class InputCorrectAtClauseOrderCheck3 implements Serializable {
  /**
   * Some text.
   *
   * @param str Some text.
   * @return Some text.
   * @serialData Some javadoc.
   * @deprecated Some text.
   */
  String method5(String str) {
    return "null";
  }

  /**
   * Some text.
   *
   * @param str Some text.
   * @param number Some text.
   * @param bool Some text.
   * @return Some text.
   * @throws Exception Some text.
   * @deprecated Some text.
   */
  String method6(String str, int number, boolean bool) throws Exception {
    return "null";
  }

  /**
   * Summary.
   *
   * @author max
   * @version 1.0
   * @since Some javadoc.
   */
  class InnerClassWithAnnotations3 {
    /**
     * Some text.
     *
     * @param str Some text.
     * @return Some text.
     * @serialData Some javadoc.
     * @deprecated Some text.
     */
    String method5(String str) {
      return "null";
    }

    /**
     * Some text.
     *
     * @param str Some text.
     * @param number Some text.
     * @param bool Some text.
     * @return Some text.
     * @throws Exception Some text.
     * @deprecated Some text.
     */
    String method6(String str, int number, boolean bool) throws Exception {
      return "null";
    }
  }

  InnerClassWithAnnotations3 anon =
      new InnerClassWithAnnotations3() {
        /**
         * Some text.
         *
         * @param str Some text.
         * @return Some text.
         * @deprecated Some text.
         */
        String method5(String str) {
          return "null";
        }

        /**
         * Some text.
         *
         * @param str Some text.
         * @param number Some text.
         * @param bool Some text.
         * @return Some text.
         * @throws Exception Some text.
         * @deprecated Some text.
         */
        String method6(String str, int number, boolean bool) throws Exception {
          return "null";
        }
      };
}
