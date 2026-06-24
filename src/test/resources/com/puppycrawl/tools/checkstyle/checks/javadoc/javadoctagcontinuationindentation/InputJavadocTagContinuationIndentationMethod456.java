/*
JavadocTagContinuationIndentation
violateExecutionOnNonTightHtml = (default)false
offset = (default)4


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctagcontinuationindentation;

class InputJavadocTagContinuationIndentationMethod456
{
    /**
     * Some text.
     * @return Some text.
     * @throws Exception Some text.
     */
    String method4() throws Exception
    {
        return "null";
    }

    /**
     * Some text.
     * @see Some text.
     * @return Some text.
     * @param aString Some text.
     */
    String method5(String aString)
    {
        return "null";
    }

    /**
     * Some text.
     * @param aString Some text.
     * @return Some text.
     *    Some javadoc. // violation, 'Line continuation .* expected level should be 4'
     * @serialData Some javadoc.
     * @param aInt Some text.
     *    Some javadoc. // violation, 'Line continuation .* expected level should be 4'
     * @throws Exception Some text.
     * @param aBoolean Some text.
     * @see Some text.
     */
    String method6(String aString, int aInt, boolean aBoolean) throws Exception
    {
        return "null";
    }
}
