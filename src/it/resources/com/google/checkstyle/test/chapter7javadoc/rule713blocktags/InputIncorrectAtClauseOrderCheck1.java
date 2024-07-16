package com.google.checkstyle.test.chapter7javadoc.rule713blocktags;

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
  private String fFirstName;

  /**
   * The client's first name.
   *
   * @serial
   */
  private String sSecondName;

  /**
   * The client's first name.
   *
   * @serialField
   */
  private String tThirdName;

  /**
   * Some text.
   *
   * @param aString Some text.
   * @return Some text.
   * @serialData Some javadoc.
   * @deprecated Some text.
   * @throws Exception Some text. // violation 'Block tags have to appear in the order .*'
   */
  String method(String aString) throws Exception {
    return "null";
  }

  /**
   * Some text.
   *
   * @serialData Some javadoc.
   * @return Some text.
   * @param aString Some text. // violation 'Block tags have to appear in the order .*'
   * @throws Exception Some text.
   */
  String method1(String aString) throws Exception {
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
     * @param aString Some text. // violation 'Block tags have to appear in the order .*'
     * @throws Exception Some text. // violation 'Block tags have to appear in the order .*'
     */
    String method(String aString) throws Exception {
      return "null";
    }

    /**
     * Some text.
     *
     * @throws Exception Some text.
     * @return Some text. // violation 'Block tags have to appear in the order .*'
     * @param aString Some text. // violation 'Block tags have to appear in the order .*'
     */
    String method1(String aString) throws Exception {
      return "null";
    }
  }

  InnerClassWithAnnotations1 anon =
      new InnerClassWithAnnotations1() {
        /**
         * Some text.
         *
         * @throws Exception Some text.
         * @param aString Some text. // violation 'Block tags have to appear in the order .*'
         * @serialData Some javadoc.
         * @deprecated Some text.
         * @return Some text. // violation 'Block tags have to appear in the order .*'
         */
        String method(String aString) throws Exception {
          return "null";
        }

        /**
         * Some text.
         *
         * @param aString Some text.
         * @throws Exception Some text.
         * @return Some text. // violation 'Block tags have to appear in the order .*'
         */
        String method1(String aString) throws Exception {
          return "null";
        }
      };
}
