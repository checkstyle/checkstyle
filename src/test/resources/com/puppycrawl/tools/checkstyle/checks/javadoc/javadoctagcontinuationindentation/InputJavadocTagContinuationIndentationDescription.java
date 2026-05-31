/*
JavadocTagContinuationIndentation
violateExecutionOnNonTightHtml = (default)false
offset = (default)4


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctagcontinuationindentation;

public class InputJavadocTagContinuationIndentationDescription {
    // violation 7 lines below 'Line continuation .* expected level should be 4'
    // violation 7 lines below 'Line continuation .* expected level should be 4'
    // violation 7 lines below 'Line continuation .* expected level should be 4'
    /**
     * General Description here.
     *
     * @param s
     * Description 1.
     * Description 2.
     * Description 3.
     *                         Description 4 with extra indentation.
     *
     *
     */
    public void testWithInvalidIndentation(String s) {}

    /**
     * General Description here.
     *
     * @param a
     *     Description 1
     * @param b
     *     Description 2
     */
    public void testWithValidIndentation(int a, int b) {}

    /** This is an inline description */
    public void testWithInlineDescription() {}

    /**
     * General Description.
     * @param x
       Description 1.
     */
    public void testWithMissingAsterisk(int x) {}

    // violation 5 lines below 'Line continuation .* expected level should be 4'
    // violation 6 lines below 'Line continuation .* expected level should be 4'
    // violation 6 lines below 'Line continuation .* expected level should be 4'
    /**
     * @param s
     *Description with missing space.
     * @param s2
     *****Description with multiple asterisk and missing space
     ***** Description with multiple asterisk and missing space
     */
    public void testOtherCases(String s, String s2) {}
    
    /**
    * @since Ant 1.6
    *    
    *     revised by <a href="mailto:daniel.armbrust@mayo.edu">Dan Armbrust</a>
    */
    public void testWithTextWithWhitespacesOnlyAloneOnLine() {}
    
   /**
    * @since Ant 1.6
    *
    *     revised by <a href="mailto:daniel.armbrust@mayo.edu">Dan Armbrust</a>
    */
    public void testWithBlankLine() {}
    
    /**
     * @see <a href=
     * "http://www.saxproject.org/apidoc/org/xml/sax/package-summary.html"
     *  >SAX.</a>     
     */
    public void test() { }
    // violation 4 lines above 'Line continuation .* expected level should be 4'
    // violation 4 lines above 'Line continuation .* expected level should be 4'
}
