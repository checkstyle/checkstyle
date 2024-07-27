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

  /**
   * Some text.
   *
   * @deprecated Some text.
   * @return Some text. // violation 'Block tags have to appear in the order .*'
   * @param str Some text. // violation 'Block tags have to appear in the order .*'
   */
  String method5(String str) {
    return "null";
  }

  /**
   * Some text.
   *
   * @param str Some text.
   * @return Some text.
   * @serialData Some javadoc.
   * @param number Some text. // violation 'Block tags have to appear in the order .*'
   * @throws Exception Some text.
   * @param bool Some text. // violation 'Block tags have to appear in the order .*'
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

    /**
     * Some text.
     *
     * @param str Some text.
     * @deprecated Some text.
     * @return Some text. // violation 'Block tags have to appear in the order .*'
     */
    String method5(String str) {
      return "null";
    }

    /**
     * Some text.
     *
     * @param str Some text.
     * @return Some text.
     * @param number Some text. // violation 'Block tags have to appear in the order .*'
     * @throws Exception Some text.
     * @param bool Some text. // violation 'Block tags have to appear in the order .*'
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
         * @deprecated Some text.
         * @return Some text. // violation 'Block tags have to appear in the order .*'
         * @param str Some text. // violation 'Block tags have to appear in the order .*'
         */
        String method5(String str) {
          return "null";
        }

        /**
         * Some text.
         *
         * @param str Some text.
         * @return Some text.
         * @param number Some text. // violation 'Block tags have to appear in the order .*'
         * @throws Exception Some text.
         * @param bool Some text. // violation 'Block tags have to appear in the order .*'
         * @deprecated Some text.
         */
        String method6(String str, int number, boolean bool) throws Exception {
          return "null";
        }
      };
}
