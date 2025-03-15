package com.google.checkstyle.test.chapter7javadoc.rule713atclauses;

import java.io.Serializable;

/**
 * Some javadoc.
 *
 * @since Some javadoc.
 *     Some javadoc.
 * @version 1.0
 * @deprecated Some javadoc.
 *     Some javadoc.
 * @see Some javadoc.
 * @author max
 *     Some javadoc.
 */
class InputJavaDocTagContinuationIndentation implements Serializable {
  /**
   * The client's first name.
   *
   * @serial Some javadoc.
   *     Some javadoc.
   */
  private String firstName;

  /**
   * The client's first name.
   *
   * @serial
   *     Some javadoc.
   */
  private String secondName;

  /**
   * The client's first name.
   *
   * @serialField
   *     Some javadoc.
   */
  private String thirdName;

  /**
   * Some text.
   *
   * @param str Some javadoc.
   *     Some javadoc.
   * @return Some text.
   * @serialData Some javadoc.
   * @throws Exception Some text.
   *    Some javadoc. // violation '.* incorrect indentation level, expected level should be 4.'
   * @deprecated Some text.
   */
  String method(String str) throws Exception {
    return "null";
  }

  /**
   * Some text.
   *
   * @serialData Some javadoc.
   * @param str Some text.
   *     Some javadoc.
   * @return Some text.
   * @throws Exception Some text.
   */
  String method1(String str) throws Exception {
    return "null";
  }

  /**
   * Some text.
   *
   * @param str Some text.
   *     Some javadoc.
   * @throws Exception Some text.
   */
  void method2(String str) throws Exception {}

  /**
   * Some text.
   *
   * @throws Exception Some text.
   * @deprecated Some text.
   *     Some javadoc.
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
   * @param bool Some text.
   * @param number Some text.
   *    Some javadoc. // violation '.* incorrect indentation level, expected level should be 4.'
   * @return Some text.
   *    Some javadoc. // violation '.* incorrect indentation level, expected level should be 4.'
   * @serialData Some javadoc.
   * @throws Exception Some text.
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
  class InnerClassWithAnnotations {
    /**
     * Some text.
     *
     * @param str Some text.
     * @return Some text.
     * @throws Exception Some text.
     *     Some javadoc.
     * @deprecated Some text.
     *     Some javadoc.
     */
    String method(String str) throws Exception {
      return "null";
    }

    /**
     * Some text.
     *
     * @param str Some text.
     *     Some javadoc.
     * @return Some text.
     * @throws Exception Some text.
     *     Some javadoc.
     */
    String method1(String str) throws Exception {
      return "null";
    }

    /**
     * Some text.
     *
     * @serialData Some javadoc.
     *     Some javadoc.
     * @param str Some text.
     *     Some javadoc.
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
     * @see <a href="https://checkstyle.org/checks/javadoc/javadoctag.html">
     *    JavadocTagContinuationIndentation: Checks the indentation </a>
     *     // violation above 'Line continuation .* expected level should be 4'
     */
    String method6() {
      return "null";
    }

    /**
     * Some text.
     *
     * @see reference <a href="https://checkstyle.org/checks/javadoc/javadoctag.html">
     *    JavadocTagContinuationIndentation: Checks the indentation </a>
     *     // violation above 'Line continuation .* expected level should be 4'
     */
    String method7() {
      return "null";
    }

    /**
     * Some text.
     *
     * @param str Some text.
     * @param number Some text.
     *    Some javadoc.
     *     // violation above '.* incorrect indentation level, expected level should be 4.'
     * @param bool Some text.
     *    Some javadoc.
     *     // violation above '.* incorrect indentation level, expected level should be 4.'
     * @return Some text.
     * @throws Exception Some text.
     * @deprecated Some text.
     */
    String method8(String str, int number, boolean bool) throws Exception {
      return "null";
    }
  }

  InnerClassWithAnnotations anon =
      new InnerClassWithAnnotations() {
        /**
         * Some text.
         *
         * @param str Some text.
         *   Some javadoc.
         *     // violation above '.* incorrect indentation level, expected level should be 4.'
         * @return Some text.
         * @throws Exception Some text.
         * @serialData Some javadoc.
         *    Some javadoc.
         *     // violation above '.* incorrect indentation level, expected level should be 4.'
         * @deprecated Some text.
         */
        String method(String str) throws Exception {
          return "null";
        }

        /**
         * Some text.
         *
         * @param str Some text.
         *     Some javadoc.
         * @return Some text.
         * @throws Exception Some text.
         */
        String method1(String str) throws Exception {
          return "null";
        }

        /**
         * Some text.
         *
         * @param str Some text.
         * @throws Exception Some text.
         *     Some javadoc.
         */
        void method2(String str) throws Exception {}

        /**
         * Some text.
         *
         * @throws Exception Some text.
         * @deprecated Some text.
         *     Some javadoc.
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
         *       Some javadoc.
         *
         * @param str Some text.
         *    Some javadoc.
         *     // violation above '.* incorrect indentation level, expected level should be 4.'
         * @param number Some text.
         *    Some javadoc.
         *     // violation above '.* incorrect indentation level, expected level should be 4.'
         * @param bool Some text.
         * @return Some text.
         * @throws Exception Some text.
         *    Some javadoc.
         *     // violation above '.* incorrect indentation level, expected level should be 4.'
         * @deprecated Some text.
         */
        String method6(String str, int number, boolean bool) throws Exception {
          return "null";
        }
      };

  /**
   * Some javadoc.
   *
   * @since Some javadoc.
   * @version 1.0
   * @deprecated Some javadoc.
   *     Some javadoc.
   *     Some javadoc.
   * @see Some javadoc.
   *    Some javadoc. // violation '.* incorrect indentation level, expected level should be 4.'
   * @author max
   */
  enum Foo3 {}

  /**
   * Some javadoc.
   *
   * @version 1.0
   * @since Some javadoc.
   *     Some javadoc.
   * @serialData Some javadoc.
   *   Some javadoc. // violation '.* incorrect indentation level, expected level should be 4.'
   * @author max
   */
  interface FooIn5 {}
}
