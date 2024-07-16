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
  private String fFirstName;

  /**
   * The client's first name.
   *
   * @serial
   *     Some javadoc.
   */
  private String sSecondName;

  /**
   * The client's first name.
   *
   * @serialField
   *     Some javadoc.
   */
  private String tThirdName;

  /**
   * Some text.
   * @param aString Some javadoc.
   *     Some javadoc.
   * @return Some text.
   * @serialData Some javadoc.
   * @throws Exception Some text.
   *    Some javadoc. // violation '.* incorrect indentation level, expected level should be 4.'
   * @deprecated Some text.
   */
  String method(String aString) throws Exception {
    return "null";
  }

  /**
   * Some text.
   *
   * @serialData Some javadoc.
   * @param aString Some text.
   *     Some javadoc.
   * @return Some text.
   * @throws Exception Some text.
   */
  String method1(String aString) throws Exception {
    return "null";
  }

  /**
   * Some text.
   *
   * @param aString Some text.
   *     Some javadoc.
   * @throws Exception Some text.
   */
  void method2(String aString) throws Exception {}

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
   * @param aString Some text.
   * @return Some text.
   * @deprecated Some text.
   */
  String method5(String aString) {
    return "null";
  }

  /**
   * Some text.
   *
   * @param aString Some text.
   * @param aBoolean Some text.
   * @param aInt Some text.
   *    Some javadoc. // violation '.* incorrect indentation level, expected level should be 4.'
   * @return Some text.
   *    Some javadoc. // violation '.* incorrect indentation level, expected level should be 4.'
   * @serialData Some javadoc.
   * @throws Exception Some text.
   * @deprecated Some text.
   */
  String method6(String aString, int aInt, boolean aBoolean) throws Exception {
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
     * @param aString Some text.
     * @return Some text.
     * @throws Exception Some text.
     *     Some javadoc.
     * @deprecated Some text.
     *     Some javadoc.
     */
    String method(String aString) throws Exception {
      return "null";
    }

    /**
     * Some text.
     *
     * @param aString Some text.
     *     Some javadoc.
     * @return Some text.
     * @throws Exception Some text.
     *     Some javadoc.
     */
    String method1(String aString) throws Exception {
      return "null";
    }

    /**
     * Some text.
     *
     * @serialData Some javadoc.
     *     Some javadoc.
     * @param aString Some text.
     *     Some javadoc.
     * @throws Exception Some text.
     */
    void method2(String aString) throws Exception {}

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
     * @param aString Some text.
     * @return Some text.
     * @deprecated Some text.
     */
    String method5(String aString) {
      return "null";
    }

    /**
     * Some text.
     *
     * @param aString Some text.
     * @param aInt Some text.
     *    Some javadoc.
     *     // violation above '.* incorrect indentation level, expected level should be 4.'
     * @param aBoolean Some text.
     *    Some javadoc.
     *     // violation above '.* incorrect indentation level, expected level should be 4.'
     * @return Some text.
     * @throws Exception Some text.
     * @deprecated Some text.
     */
    String method6(String aString, int aInt, boolean aBoolean) throws Exception {
      return "null";
    }
  }

  InnerClassWithAnnotations anon =
      new InnerClassWithAnnotations() {
        /**
         * Some text.
         *
         * @param aString Some text.
         *   Some javadoc.
         *     // violation above '.* incorrect indentation level, expected level should be 4.'
         * @return Some text.
         * @throws Exception Some text.
         * @serialData Some javadoc.
         *    Some javadoc.
         *     // violation above '.* incorrect indentation level, expected level should be 4.'
         * @deprecated Some text.
         */
        String method(String aString) throws Exception {
          return "null";
        }

        /**
         * Some text.
         *
         * @param aString Some text.
         *     Some javadoc.
         * @return Some text.
         * @throws Exception Some text.
         */
        String method1(String aString) throws Exception {
          return "null";
        }

        /**
         * Some text.
         *
         * @param aString Some text.
         * @throws Exception Some text.
         *     Some javadoc.
         */
        void method2(String aString) throws Exception {}

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
         * @param aString Some text.
         * @return Some text.
         * @deprecated Some text.
         */
        String method5(String aString) {
          return "null";
        }

        /**
         * Some text.
         *       Some javadoc.
         *
         * @param aString Some text.
         *    Some javadoc.
         *     // violation above '.* incorrect indentation level, expected level should be 4.'
         * @param aInt Some text.
         *    Some javadoc.
         *     // violation above '.* incorrect indentation level, expected level should be 4.'
         * @param aBoolean Some text.
         * @return Some text.
         * @throws Exception Some text.
         *    Some javadoc.
         *     // violation above '.* incorrect indentation level, expected level should be 4.'
         * @deprecated Some text.
         */
        String method6(String aString, int aInt, boolean aBoolean) throws Exception {
          return "null";
        }
      };
}

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
