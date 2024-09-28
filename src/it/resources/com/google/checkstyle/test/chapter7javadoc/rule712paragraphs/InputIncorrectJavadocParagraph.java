package com.google.checkstyle.test.chapter7javadoc.rule712paragraphs;

// violation 3 lines below '<p> tag should be preceded with an empty line.'
/**
 * Some Javadoc.
 * <p>
 * /^ WARN/ Some Javadoc.<p>
 */
// violation 2 lines above '<p> tag should be preceded with an empty line.'

class InputIncorrectJavadocParagraph {

  // violation 2 lines below '<p> tag should be preceded with an empty line.'
  /**
   * Some Javadoc.<p>
   *
   * <p>  Some Javadoc.
   *
   * @since 8.0
   */
  // violation 4 lines above '<p> tag should be placed immediately before the first word'
  public static final byte NUL = 0;

  // violation 2 lines below '<p> tag should be preceded with an empty line.'
  /**
   * Some <p>Javadoc.
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

  // violation 2 lines below 'Redundant <p> tag.'
  // violation 2 lines below '<p> tag should be preceded with an empty line.'
  /**<p>Some Javadoc.
   * <p>
   * <p><p>
   * <p>/^WARN/ Some Javadoc.<p>
   */
  // violation 3 lines above '<p> tag should be preceded with an empty line.'
  // violation 3 lines above '<p> tag should be preceded with an empty line.'
  class InnerInputCorrectJavaDocParagraphCheck {

    // violation 2 lines below '<p> tag should be preceded with an empty line.'
    /**
     * Some Javadoc.<p>
     *
     * @since 8.0
     */
    public static final byte NUL = 0;

    // violation below 'Redundant <p> tag.'
    /**<p>
     * /^WARN/ Some Javadoc.
     *
     * <P>
     * /^WARN/
     * <p>
     *  /^WARN/ Some Javadoc.<p>
     * @see <a
     *     href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
     *     Documentation about GWT emulated source</a>
     */
    // violation 6 lines above '<p> tag should be preceded with an empty line.'
    // violation 6 lines above '<p> tag should be preceded with an empty line.'
    // violation 6 lines above 'Javadoc tag '@see' should be preceded with an empty line.'
    boolean emulated() {
      return false;
    }
  }

  InnerInputCorrectJavaDocParagraphCheck anon =
      new InnerInputCorrectJavaDocParagraphCheck() {

        // violation 2 lines below 'Redundant <p> tag.'
        /**
         * <p>Some Javadoc.
         *
         * <p>Some Javadoc.
         *
         * @since 8.0
         */
        public static final byte NUL = 0;

        // violation 3 lines below '<p> tag should be preceded with an empty line.'
        // violation 4 lines below '<p> tag should be placed immediately before the first word'
        /**
         * /WARN/  Some Javadoc.<p>
         *
         *  <p>  Some Javadoc.
         *
         * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
         *     Documentation about <p> GWT emulated source</a>
         */
        // 2 violations 2 lines above:
        //  '<p> tag should be placed immediately before the first word'
        //  '<p> tag should be preceded with an empty line.'
        boolean emulated() {
          return false;
        }
      };

  /* 4 lines below, no violation until #15685 */
  /**
   * Some summary.
   *
   * <p><h1>Testing...</h1></p>
   */
  class InnerPrecedingPtag {
    /* 5 lines below, no violation until #15685 */
    /**
     * Some summary.
     *
     *<p>
     *  <ul>
     *    <p>
     *      <li>1</li> // should NOT give violation as there is not empty line before
     *    </p>
     *  </ul>
     *</p>
     */
    public void foo() {}

    /* 5 lines below, no violation until #15685 */
    /**
     *  Some summary.
     *
     * <p>
     *  <table>
     *  </table>
     * </p>
     */
    public void fooo() {}

    /* 5 lines below, no violation until #15685 */
    /**
     * Some summary.
     *
     * <p>
     *   <pre>testing...</pre>
     *   <pre>testing...</pre>
     * </p>
     */
    public void foooo() {}
  }
}
