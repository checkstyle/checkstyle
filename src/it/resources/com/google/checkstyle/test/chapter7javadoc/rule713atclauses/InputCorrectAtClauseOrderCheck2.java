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
class InputCorrectAtClauseOrderCheck2 implements Serializable {
  /**
   * Some text.
   *
   * @param str Some text.
   * @throws Exception Some text.
   */
  void method2(String str) throws Exception {}

  /**
   * Some text.
   *
   * @throws Exception Some text.
   * @deprecated Some text.
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
   * Summary.
   *
   * @author max
   * @version 1.0
   * @since Some javadoc.
   */
  class InnerClassWithAnnotations2 {
    /**
     * Some text.
     *
     * @param str Some text.
     * @throws Exception Some text.
     */
    void method2(String str) throws Exception {}

    /**
     * Some text.
     *
     * @throws Exception Some text.
     * @deprecated Some text.
     */
    void method3() throws Exception {}

    /**
     * Some text.
     *
     * @return Some text.
     * @throws Exception Some text.
     * @serialData Some javadoc.
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
         * @param str Some text.
         * @throws Exception Some text.
         * @serialData Some javadoc.
         */
        void method2(String str) throws Exception {}

        /**
         * Some text.
         *
         * @throws Exception Some text.
         * @deprecated Some text.
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
      };

  /**
   * Some javadoc.
   *
   * @author max
   * @version 1.0
   * @see Some javadoc.
   * @since Some javadoc.
   * @deprecated Some javadoc.
   */
  enum Foo {}

  /**
   * Some javadoc.
   *
   * @author max
   * @version 1.0
   * @see Some javadoc.
   * @since Some javadoc.
   * @deprecated Some javadoc.
   */
  interface FooIn {}
}
