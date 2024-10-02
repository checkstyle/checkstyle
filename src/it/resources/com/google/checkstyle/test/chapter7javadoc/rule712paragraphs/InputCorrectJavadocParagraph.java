package com.google.checkstyle.test.chapter7javadoc.rule712paragraphs;

/**
 * Some Javadoc.
 *
 * <p>Some Javadoc.
 */
class InputCorrectJavadocParagraph {

  /**
   * Some Javadoc.
   *
   * <p>{@code function} will never be invoked with a null value.
   *
   * @since 8.0
   */
  public static final byte NUL = 0;

  /**
   * Some Javadoc.
   *
   * <p>Some Javadoc.
   *
   * <pre>
   * class Foo {
   *
   *   void foo() {}
   * }
   * </pre>
   *
   * @see <a href="example.com">Documentation about GWT emulated source</a>
   */
  boolean emulated() {
    return false;
  }

  /**
   * Some Javadoc.
   *
   * <p>Some Javadoc.
   */
  class InnerInputCorrectJavaDocParagraphCheck {

    /**
     * Some Javadoc.
     *
     * <p>Some Javadoc.
     *
     * <p>Some Javadoc.
     *
     * @since 8.0
     */
    public static final byte NUL = 0;

    /**
     * Some Javadoc.
     *
     * <p>Some Javadoc.
     *
     * @see <a href="example.com">Documentation about GWT emulated source</a>
     */
    boolean emulated() {
      return false;
    }
  }

  InnerInputCorrectJavaDocParagraphCheck anon =
      new InnerInputCorrectJavaDocParagraphCheck() {

        /**
         * Some Javadoc.
         *
         * <p>Some Javadoc.
         *
         * <p>Some Javadoc.
         *
         * @since 8.0
         */
        public static final byte NUL = 0;

        /**
         * Some Javadoc.
         *
         * <p>Some Javadoc.
         *
         * <p>Some Javadoc.
         *
         * @see <a href="example.com">Documentation about GWT emulated source</a>
         */
        boolean emulated() {
          return false;
        }
      };

  /**
   * Some summary.
   *
   * <p>testing
   *
   * <p>testing
   */
  class InnerPrecedingPtag {
    /**
     * Some summary.
     *
     * <p><b>testing</b>
     */
    public void foo() {}

    /**
     * Some summary.
     *
     * <table>
     *   <thead>
     *     <p></p>
     *   </thead>
     * </table>
     */
    public void fooo() {}

    /**
     * Some summary.
     *
     * <p>testing...
     *
     * <pre>testing...</pre>
     */
    public void foooo() {}
  }
}
