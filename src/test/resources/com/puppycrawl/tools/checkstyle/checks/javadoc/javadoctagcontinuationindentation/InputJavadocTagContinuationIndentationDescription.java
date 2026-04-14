/*
JavadocTagContinuationIndentation
violateExecutionOnNonTightHtml = (default)false
offset = (default)4


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctagcontinuationindentation;

public class InputJavadocTagContinuationIndentationDescription {
    /**
     * General Description here.
     *
     * @param s
     * Description 1. // violation, 'Line continuation .* expected level should be 4'
     * Description 2. // violation, 'Line continuation .* expected level should be 4'
     * Description 3. // violation, 'Line continuation .* expected level should be 4'
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

    /**
     * @param s // violation below 'Line continuation .* expected level should be 4'
     *Description with missing space.
     * @param s2 // violation below 'Line continuation .* expected level should be 4'
     *****Description with multiple asterisk and missing space
     ***** Description with multiple asterisk and missing space
     */ // violation above 'Line continuation .* expected level should be 4'
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
