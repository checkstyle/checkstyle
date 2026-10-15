/*
JavadocTagContinuationIndentation
violateExecutionOnNonTightHtml = (default)false
offset = (default)4
forceStrictCondition = true


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctagcontinuationindentation;

public class InputJavadocTagContinuationIndentationForceStrictCondition {

    /**
     * Continuation lines with exactly offset indentation.
     *
     * @param value first line of description
     *     continuation at exactly four spaces.
     *     another continuation at exactly four spaces.
     * @return first line of description
     *     continuation at exactly four spaces.
     */
    public int exactIndentation(int value) {
        return value;
    }

    // violation 5 lines below 'Line continuation .* expected level should be 4'
    /**
     * Continuation line indented one space too deep.
     *
     * @param value first line of description
     *      continuation at five spaces.
     */
    public void oneSpaceTooDeep(int value) {
    }

    // violation 6 lines below 'Line continuation .* expected level should be 4'
    // violation 7 lines below 'Line continuation .* expected level should be 4'
    /**
     * Continuation lines indented much deeper than offset.
     *
     * @param value first line of description
     *                         continuation at twenty five spaces.
     * @return first line of description
     *         continuation at nine spaces.
     */
    public int muchTooDeep(int value) {
        return value;
    }

    // violation 5 lines below 'Line continuation .* expected level should be 4'
    /**
     * Continuation line indented less than offset is still a violation.
     *
     * @param value first line of description
     *   continuation at two spaces.
     */
    public void tooShallow(int value) {
    }

    /**
     * Lines inside html elements may be indented deeper than offset.
     *
     * @param value first line of description
     *     <ul>
     *         <li>item at eight spaces inside html element.</li>
     *             <li>item at twelve spaces inside html element.</li>
     *     </ul>
     *     continuation after html element.
     */
    public void nestedHtmlList(int value) {
    }

    // violation 5 lines below 'Line continuation .* expected level should be 4'
    /**
     * Html element start in plain description must have exact indentation.
     *
     * @param value first line of description
     *         <ul>
     *             <li>item inside html element.</li>
     *         </ul>
     */
    public void htmlElementTooDeep(int value) {
    }

    /**
     * Lines inside pre tag are not validated.
     *
     * @param value first line of description
     *     <pre>
     *             preformatted line at thirteen spaces.
     *     </pre>
     */
    public void preTag(int value) {
    }

    /**
     * Empty continuation line is not a violation.
     *
     * @param value first line of description
     *
     *     continuation after empty line.
     */
    public void emptyLine(int value) {
    }

    // violation 5 lines below 'Line continuation .* expected level should be 4'
    /**
     * Description starting on the line after the tag.
     *
     * @return
     *         continuation at eight spaces.
     */
    public int tagOnOwnLine() {
        return 0;
    }
}
