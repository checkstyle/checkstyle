package com.google.checkstyle.test.chapter7javadoc.rule712paragraphs;

/**
 * Some Javadoc.
 *
 * <p>/^ WARN/ Some Javadoc.
 *
 * <p>
 */
class InputFormattedIncorrectJavadocParagraph {
  // violation 3 lines above '<p> tag should be placed immediately before the first word'

  // violation 4 lines below '<p> tag should be placed immediately before the first word'
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

  // violation 6 lines below '<p> tag should be placed immediately before the first word'
  // violation 7 lines below '<p> tag should be placed immediately before the first word'
  // violation 8 lines below '<p> tag should be placed immediately before the first word'
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
    // violation 3 lines above '<p> tag should be placed immediately before the first word'

    // violation 4 lines below '<p> tag should be placed immediately before the first word'
    /**
     * Some Javadoc.
     *
     * <p>
     *
     * @since 8.0
     */
    public static final byte NUL = 0;

    // violation 8 lines below '<p> tag should be placed immediately before the first word'
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

        // violation 4 lines below '<p> tag should be placed immediately before the first word'
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
}
