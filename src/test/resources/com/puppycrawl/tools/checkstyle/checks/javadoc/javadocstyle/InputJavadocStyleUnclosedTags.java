/*
JavadocStyle
scope = (default)private
excludeScope = (default)null
checkFirstSentence = false
endOfSentenceFormat = (default)([.?!][ \t\n\r\f<])|([.?!]$)
checkEmptyJavadoc = (default)false
checkHtml = (default)true
tokens = (default)ANNOTATION_DEF, ANNOTATION_FIELD_DEF, CLASS_DEF, CTOR_DEF, \
         ENUM_CONSTANT_DEF, ENUM_DEF, INTERFACE_DEF, METHOD_DEF, PACKAGE_DEF, \
         VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocstyle;

public class InputJavadocStyleUnclosedTags {

    // violation 5 lines below 'Unclosed HTML tag found: <span>'
    /**
     * Test with nested unclosed tags to test getUnclosedTagNameTokens with multiple scenarios.
     * <div>
     *   <p>Text in paragraph.</p>
     *   <span>Text in span.
     * </div>
     */
    private void testNestedUnclosedTags() {}

    // 2 violations 3 lines below:
    //                            'Unclosed HTML tag found: <code>'
    //                            'Extra HTML tag found: </pre>'
    /**
     * Test with non-matching tags.
     * <code>Code text</pre>
     */
    private void testNonMatchingTags() {}

    /**
     * Test with tag at start of Javadoc (tests isOpenTagName with null previousToken).
     * <p>Some text.</p>
     */
    private void testTagAtStart() {}

    /**
     * Test with properly closed tags (tests isOpenTagName with slash token).
     * <p>Some text.</p>
     * <b>Bold text.</b>
     */
    private void testClosedTags() {}

}

