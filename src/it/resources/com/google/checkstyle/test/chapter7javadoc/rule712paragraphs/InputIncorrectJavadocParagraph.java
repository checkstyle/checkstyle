package com.google.checkstyle.test.chapter7javadoc.rule712paragraphs;

// 2 violations 5 lines below:
//  '\<p\> tag should be placed immediately before the first word'
//  '\<p\> tag should be preceded with an empty line.'
/**
 * Some Javadoc.
 * <p>
 * /^ WARN/ Some Javadoc.<p>
 */
// 2 violations 2 lines above:
//  '\<p\> tag should be placed immediately before the first word'
//  '\<p\> tag should be preceded with an empty line.'

class InputIncorrectJavadocParagraph {

  // 2 violations 4 lines below:
  //  '\<p\> tag should be placed immediately before the first word'
  //  '\<p\> tag should be preceded with an empty line.'
  /**
   * Some Javadoc.<p>
   *
   * <p>  Some Javadoc.
   *
   * @since 8.0
   */
  // violation 4 lines above '\<p\> tag should be placed immediately before the first word'
  public static final byte NUL = 0;

  // violation 2 lines below '\<p\> tag should be preceded with an empty line.'
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

  // violation 4 lines below 'Redundant \<p\> tag.'
  // 2 violations 4 lines below:
  //                            '\<p\> tag should be placed immediately before the first word'
  //                            '\<p\> tag should be preceded with an empty line.'
  /**<p>Some Javadoc.
   * <p>
   * <p><p>
   * <p>/^WARN/ Some Javadoc.<p>
   */
  // 2 violations 3 lines above:
  //  '\<p\> tag should be placed immediately before the first word'
  //  '\<p\> tag should be preceded with an empty line.'
  // 2 violations 5 lines above:
  //  '\<p\> tag should be placed immediately before the first word'
  //  '\<p\> tag should be preceded with an empty line.'
  class InnerInputCorrectJavaDocParagraphCheck {

    // 2 violations 4 lines below:
    //  '\<p\> tag should be placed immediately before the first word'
    //  '\<p\> tag should be preceded with an empty line.'
    /**
     * Some Javadoc.<p>
     *
     * @since 8.0
     */
    public static final byte NUL = 0;

    // 2 violations 4 lines below:
    //  '\<p\> tag should be placed immediately before the first word'
    //  'Redundant \<p\> tag.'
    // violation 4 lines below '\<p\> tag should be placed immediately before the first word'
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
    // 2 violations 6 lines above:
    //  '\<p\> tag should be placed immediately before the first word'
    //  '\<p\> tag should be preceded with an empty line.'
    // 2 violations 8 lines above:
    //  '\<p\> tag should be placed immediately before the first word'
    //  '\<p\> tag should be preceded with an empty line.'
    // violation 10 lines above 'Javadoc tag '@see' should be preceded with an empty line.'
    boolean emulated() {
      return false;
    }
  }

  InnerInputCorrectJavaDocParagraphCheck anon =
      new InnerInputCorrectJavaDocParagraphCheck() {

        // violation 2 lines below 'Redundant \<p\> tag.'
        /**
         * <p>Some Javadoc.
         *
         * <p>Some Javadoc.
         *
         * @since 8.0
         */
        public static final byte NUL = 0;

        // 2 violations 5 lines below:
        //  '\<p\> tag should be placed immediately before the first word'
        //  '\<p\> tag should be preceded with an empty line.'
        // violation 4 lines below '\<p\> tag should be placed immediately before the first word'
        /**
         * /WARN/  Some Javadoc.<p>
         *
         *  <p>  Some Javadoc.
         *
         * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
         *     Documentation about <p> GWT emulated source</a>
         */
        // 2 violations 2 lines above:
        //  '\<p\> tag should be placed immediately before the first word'
        //  '\<p\> tag should be preceded with an empty line.'
        boolean emulated() {
          return false;
        }
      };
}
