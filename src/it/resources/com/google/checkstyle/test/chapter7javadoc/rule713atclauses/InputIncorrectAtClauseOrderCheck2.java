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
class InputIncorrectAtClauseOrderCheck2 implements Serializable {
  /**
   * Some text.
   *
   * @throws Exception Some text.
   * @param str Some text. // violation 'Block tags have to appear in the order .*'
   */
  void method2(String str) throws Exception {}

  /**
   * Some text.
   *
   * @deprecated Some text.
   * @throws Exception Some text. // violation 'Block tags have to appear in the order .*'
   */
  void method3() throws Exception {}

  /**
   * Some text.
   *
   * @return Some text.
   * @throws Exception Some text.
   */
  String method4() throws Exception {
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
  class InnerClassWithAnnotations2 {
    /**
     * Some text.
     *
     * @serialData Some javadoc.
     * @param str Some text.
     * @throws Exception Some text.
     */
    void method2(String str) throws Exception {}

    /**
     * Some text.
     *
     * @deprecated Some text.
     * @throws Exception Some text. // violation 'Block tags have to appear in the order .*'
     */
    void method3() throws Exception {}

    /**
     * Some text.
     *
     * @throws Exception Some text.
     * @serialData Some javadoc.
     * @return Some text. // violation 'Block tags have to appear in the order .*'
     */
    String method4() throws Exception {
      return "null";
    }
  }

  InnerClassWithAnnotations2 anon =
      new InnerClassWithAnnotations2() {
        /**
         * Some text.
         *
         * @throws Exception Some text.
         * @param str Some text. // violation 'Block tags have to appear in the order .*'
         */
        void method2(String str) throws Exception {}

        /**
         * Some text.
         *
         * @deprecated Some text.
         * @throws Exception Some text. // violation 'Block tags have to appear in the order .*'
         */
        void method3() throws Exception {}

        /**
         * Some text.
         *
         * @throws Exception Some text.
         * @return Some text. // violation 'Block tags have to appear in the order .*'
         */
        String method4() throws Exception {
          return "null";
        }
      };

  /**
   * Some javadoc.
   *
   * @since Some javadoc.
   * @version 1.0
   * @deprecated Some javadoc.
   * @see Some javadoc.
   * @author max
   */
  enum Foo5 {}

  /**
   * Some javadoc.
   *
   * @version 1.0
   * @since Some javadoc.
   * @serialData Some javadoc.
   * @author max
   */
  interface FooIn1 {}
}
