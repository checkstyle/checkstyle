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
class InputCorrectAtClauseOrderCheck1 implements Serializable {

  /**
   * The client's first name.
   *
   * @serial
   */
  private String firstName;

  /**
   * The client's first name.
   *
   * @serial
   */
  private String secondName;

  /**
   * The client's first name.
   *
   * @serialField
   */
  private String thirdName;

  /**
   * Some text.
   *
   * @param str Some text.
   * @return Some text.
   * @throws Exception Some text.
   * @serialData Some javadoc.
   * @deprecated Some text.
   */
  String method(String str) throws Exception {
    return "null";
  }

  /**
   * Some text.
   *
   * @param str Some text.
   * @return Some text.
   * @throws Exception Some text.
   * @serialData Some javadoc.
   */
  String method1(String str) throws Exception {
    return "null";
  }

  /**
   * Summary.
   *
   * @author max
   * @version 1.0
   * @since Some javadoc.
   */
  class InnerClassWithAnnotations1 {
    /**
     * Some text.
     *
     * @param str Some text.
     * @return Some text.
     * @throws Exception Some text.
     * @deprecated Some text.
     */
    String method(String str) throws Exception {
      return "null";
    }

    /**
     * Some text.
     *
     * @param str Some text.
     * @return Some text.
     * @throws Exception Some text.
     * @serialData Some javadoc.
     */
    String method1(String str) throws Exception {
      return "null";
    }
  }

  InnerClassWithAnnotations1 anon =
      new InnerClassWithAnnotations1() {
        /**
         * Some text.
         *
         * @param str Some text.
         * @return Some text.
         * @throws Exception Some text.
         * @deprecated Some text.
         */
        String method(String str) throws Exception {
          return "null";
        }

        /**
         * Some text.
         *
         * @param str Some text.
         * @return Some text.
         * @throws Exception Some text.
         */
        String method1(String str) throws Exception {
          return "null";
        }
      };
}
