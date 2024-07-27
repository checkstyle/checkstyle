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
class InputIncorrectAtClauseOrderCheck1 implements Serializable {
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
   * @serialData Some javadoc.
   * @deprecated Some text.
   * @throws Exception Some text. // violation 'Block tags have to appear in the order .*'
   */
  String method(String str) throws Exception {
    return "null";
  }

  /**
   * Some text.
   *
   * @serialData Some javadoc.
   * @return Some text.
   * @param str Some text. // violation 'Block tags have to appear in the order .*'
   * @throws Exception Some text.
   */
  String method1(String str) throws Exception {
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
  class InnerClassWithAnnotations1 {
    /**
     * Some text.
     *
     * @return Some text.
     * @deprecated Some text.
     * @param str Some text. // violation 'Block tags have to appear in the order .*'
     * @throws Exception Some text. // violation 'Block tags have to appear in the order .*'
     */
    String method(String str) throws Exception {
      return "null";
    }

    /**
     * Some text.
     *
     * @throws Exception Some text.
     * @return Some text. // violation 'Block tags have to appear in the order .*'
     * @param str Some text. // violation 'Block tags have to appear in the order .*'
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
         * @throws Exception Some text.
         * @param str Some text. // violation 'Block tags have to appear in the order .*'
         * @serialData Some javadoc.
         * @deprecated Some text.
         * @return Some text. // violation 'Block tags have to appear in the order .*'
         */
        String method(String str) throws Exception {
          return "null";
        }

        /**
         * Some text.
         *
         * @param str Some text.
         * @throws Exception Some text.
         * @return Some text. // violation 'Block tags have to appear in the order .*'
         */
        String method1(String str) throws Exception {
          return "null";
        }
      };
}
