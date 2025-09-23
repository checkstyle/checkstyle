/*
JavadocTagContinuationIndentation
violateExecutionOnNonTightHtml = (default)false
offset = (default)4

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctagcontinuationindentation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class InputJavadocTagContinuationIndentationPreTag3 {

    @Target(ElementType.TYPE_USE)
    @interface Internal {}

    /**
     * @return sub role of this role, which match `item`.
     *
     * <pre><code>
     * CtMethod method = ...
     * CtRole role = CtRole.TYPE_MEMBER.getMatchingSubRoleFor(method);
     * </code></pre>
     */
    @Internal
    public Object testMethod() {
        return new Object();
    }

    /**
     * @return sub role of this role, which match `item`.
     *
     * <code> // violation
     * CtMethod method = ... // violation
     * CtRole role = CtRole.TYPE_MEMBER.getMatchingSubRoleFor(method); // violation
     * </code> // violation
     */
    @Internal
    public Object testMethod2() {
        return new Object();
    }
}
