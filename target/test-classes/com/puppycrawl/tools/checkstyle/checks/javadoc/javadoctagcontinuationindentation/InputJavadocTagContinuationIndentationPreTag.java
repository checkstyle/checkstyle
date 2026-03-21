/*
JavadocTagContinuationIndentation
violateExecutionOnNonTightHtml = (default)false
offset = (default)4


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctagcontinuationindentation;

/**
 * Test class.
 *
 * @apiNote
 *          This is the predefined indentation applied by Eclipse formatter to api/impl taglets.
 *          <pre>
 * Since &lt;pre> blocks retain leading whitespace, in order to avoid extra-indentation from being
 * added by the javadoc renderer, its content herein has to stick to the left margin of the comment
 * area. Unfortunately, checkstyle seems not to properly evaluate the context (&lt;pre> block) of
 * this content, and reports an error:
 *   "JavadocTagContinuationIndentation: Line continuation have incorrect indentation level,
 *   expected level should be 4"</pre>
 */
public class InputJavadocTagContinuationIndentationPreTag {

    /**
     * Test class.
     *
     * @apiNote
     *          This is the predefined indentation applied by Eclipse formatter to api/impl taglets.
     *          <pre>
     *          Since &lt;pre> blocks retain leading whitespace, in order to avoid
     *          the context (&lt;pre> block) of this content, and reports an error:
     *            "JavadocTagContinuationIndentation: Line continuation have
     *            incorrect indentation level,
     *            expected level should be 4"</pre>
     */
    public void test1() {}

    /**
     * <p>Java class for errorResponse complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within class.
     *
     * <pre>
     * &lt;complexType name="errorResponse"&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="errors" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="error" type="{http://www.w3.org/2001/XMLSchema}str
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     *
     *
     */
    public void test2() {}

    /**
     * Test class.
     *
     * @apiNote
     *          This is the predefined indentation applied by Eclipse formatter to api taglets.
     *          <pre>
     *   "JavadocTagContinuationIndentation: Line continuation have incorrect indentation level,
     *   expected level should be 4"</pre> some text after the pre tag to check how the current
     *     behaviour of the check.
     */
    public void test3() {}

    /**
     * Test class.
     *
     * @apiNote
     *          This is the predefined indentation applied by Eclipse formatter to api taglets.
     *          <pre>
     *   "JavadocTagContinuationIndentation: Line continuation have incorrect indentation level,
     *   expected level should be 4"</pre> some text after the pre tag to check how the current
     *   behaviour of the check.
     */
    public void test4() {}
    // violation 3 lines above 'Line continuation have .* expected level should be 4'

    /**
     * Writes the object using a
     * <a href="{@docRoot}/serialized-form.html#ja">deserialized form</a>.
     * @serialData
     * Refer to the serialized form of // violation
     * <a href="{@docRoot}/serialized-formRules">ZoneRules.writeReplace</a> // violation
     * for the encoding of epoch seconds and offsets. // violation
     * <pre style="font-size:1.0em">{@code
     *
     *   out.writeByte(2);                // identifies a ZoneOffsetTransition
     *   out.writeEpochSec(toEpochSecond);
     *   out.writeOffset(offsetAfter);
     * }
     * </pre>
     * @return the replacing object, not null
     */
    private Object writeReplace() {
        return new Object();
    }
}
