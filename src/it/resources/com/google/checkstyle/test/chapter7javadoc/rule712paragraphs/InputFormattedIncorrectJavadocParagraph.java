package com.google.checkstyle.test.chapter7javadoc.rule712paragraphs;

/**
 * Some Javadoc.
 *
 * <p>/^ WARN/ Some Javadoc.
 *
 * <p>
 */
class InputFormattedIncorrectJavadocParagraph {

  /**
   * Some Javadoc.
   *
   * <p>
   *
   * <p>Some Javadoc.
   *
   * @since 8.0
   */
  public static final byte NUL = 0;

  /**
   * Some
   *
   * <p>Javadoc.
   *
   * <p>Some Javadoc.
   *
   * @see <a
   *     href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
   *     Documentation about GWT emulated source</a>
   */
  boolean emulated() {
    return false;
  }

  /**
   * Some Javadoc.
   *
   * <p>
   *
   * <p>
   *
   * <p>
   *
   * <p>/^WARN/ Some Javadoc.
   *
   * <p>
   */
  class InnerInputCorrectJavaDocParagraphCheck {

    /**
     * Some Javadoc.
     *
     * <p>
     *
     * @since 8.0
     */
    public static final byte NUL = 0;

    /**
     * /^WARN/ Some Javadoc.
     *
     * <p>/^WARN/
     *
     * <p>/^WARN/ Some Javadoc.
     *
     * <p>
     *
     * @see <a
     *     href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
     *     Documentation about GWT emulated source</a>
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
         * @since 8.0
         */
        public static final byte NUL = 0;

        /**
         * /WARN/ Some Javadoc.
         *
         * <p>
         *
         * <p>Some Javadoc.
         *
         * @see <a
         *     href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
         *     Documentation about
         *     <p>GWT emulated source</a>
         */
        // violation 2 lines above '<p> tag should be preceded with an empty line.'
        boolean emulated() {
          return false;
        }
      };

  /* 4 lines below, no violation until #15685 */
  /**
   * Some summary.
   *
   * <p>
   *
   * <h1>Testing...</h1>
   */
  class InnerPrecedingPtag {
    /* 5 lines below, no violation until #15685 */
    /**
     * Some summary.
     *
     * <p>
     *
     * <ul>
     *   <p>
     *   <li>1 // should NOT give violation as there is not empty line before
     * </ul>
     */
    // violation 4 lines above '<p> tag should be preceded with an empty line.'
    public void foo() {}

    /* 5 lines below, no violation until #15685 */
    /**
     * Some summary.
     *
     * <p>
     *
     * <table>
     *  </table>
     */
    public void fooo() {}

    /* 5 lines below, no violation until #15685 */
    /**
     * Some summary.
     *
     * <p>
     *
     * <pre>testing...</pre>
     *
     * <pre>testing...</pre>
     */
    public void foooo() {}
  }
}
