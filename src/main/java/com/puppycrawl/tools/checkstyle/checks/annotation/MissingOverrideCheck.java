////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.annotation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTagInfo;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <p>
 * Verifies that the {@code @Override} annotation is present
 * when the {@code @inheritDoc} javadoc tag is present.
 * </p>
 * <p>
 * Rationale: The &#64;Override annotation helps
 * compiler tools ensure that an override is actually occurring.  It is
 * quite easy to accidentally overload a method or hide a static method
 * and using the &#64;Override annotation points out these problems.
 * </p>
 * <p>
 * This check will log a violation if using the &#64;inheritDoc tag on a method that
 * is not valid (ex: private, or static method).
 * </p>
 * <p>
 * There is a slight difference between the &#64;Override annotation in Java 5 versus
 * Java 6 and above. In Java 5, any method overridden from an interface cannot
 * be annotated with &#64;Override. In Java 6 this behavior is allowed.
 * </p>
 * <p>
 * As a result of the aforementioned difference between Java 5 and Java 6, a
 * property called {@code javaFiveCompatibility} is available. This
 * property will only check classes, interfaces, etc. that do not contain the
 * extends or implements keyword or are not anonymous classes. This means it
 * only checks methods overridden from {@code java.lang.Object}.
 * <b>Java 5 Compatibility mode severely limits this check. It is recommended to
 * only use it on Java 5 source.</b>
 * </p>
 * <ul>
 * <li>
 * Property {@code javaFiveCompatibility} - Enable java 5 compatibility mode.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;MissingOverride&quot;/&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * class Test extends SuperClass {
 *
 *     &#47;** {&#64;inheritDoc} *&#47;
 *     &#64;Override
 *     public void test1() { // OK
 *
 *     }
 *
 *     &#47;** {&#64;inheritDoc} *&#47;
 *     public void test2() { // violation, should be annotated with &#64;Override
 *
 *     }
 *
 *     &#47;** {&#64;inheritDoc} *&#47;
 *     private void test3() { // violation, using the &#64;inheritDoc tag on private method
 *
 *     }
 *
 *     &#47;** {&#64;inheritDoc} *&#47;
 *     public static void test4() { // violation, using the &#64;inheritDoc tag on static method
 *
 *     }
 * }
 * </pre>
 * <p>
 * To configure the check for the {@code javaFiveCompatibility} mode:
 * </p>
 * <pre>
 * &lt;module name="MissingOverride"&gt;
 *   &lt;property name="javaFiveCompatibility"
 *       value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * class Test1 {
 *
 *     &#47;** {&#64;inheritDoc} *&#47;
 *     public void equals() { // violation, should be annotated with &#64;Override
 *
 *     }
 * }
 *
 * interface Test2 {
 *
 *     &#47;** {&#64;inheritDoc} *&#47;
 *     void test(); // violation, should be annotated with &#64;Override
 * }
 *
 * class Test3 extends SuperClass {
 *
 *     &#47;** {&#64;inheritDoc} *&#47;
 *     public void test() { // OK, is ignored because class extends other class
 *
 *     }
 * }
 *
 * class Test4 implements SuperInterface {
 *
 *     &#47;** {&#64;inheritDoc} *&#47;
 *     public void test() { // OK, is ignored because class implements interface
 *
 *     }
 * }
 *
 * class Test5 {
 *     Runnable r = new Runnable() {
 *          &#47;** {&#64;inheritDoc} *&#47;
 *          public void run() { // OK, is ignored because class is anonymous class
 *
 *          }
 *     };
 * }
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code annotation.missing.override}
 * </li>
 * <li>
 * {@code tag.not.valid.on}
 * </li>
 * </ul>
 *
 * @since 5.0
 */
@StatelessCheck
public final class MissingOverrideCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_TAG_NOT_VALID_ON = "tag.not.valid.on";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_ANNOTATION_MISSING_OVERRIDE =
        "annotation.missing.override";

    /** {@link Override Override} annotation name. */
    private static final String OVERRIDE = "Override";

    /** Fully-qualified {@link Override Override} annotation name. */
    private static final String FQ_OVERRIDE = "java.lang." + OVERRIDE;

    /** Compiled regexp to match Javadoc tags with no argument and {}. */
    private static final Pattern MATCH_INHERIT_DOC =
            CommonUtil.createPattern("\\{\\s*@(inheritDoc)\\s*\\}");

    /**
     * Enable java 5 compatibility mode.
     */
    private boolean javaFiveCompatibility;

    /**
     * Setter to enable java 5 compatibility mode.
     *
     * @param compatibility compatibility or not
     */
    public void setJavaFiveCompatibility(final boolean compatibility) {
        javaFiveCompatibility = compatibility;
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[]
        {TokenTypes.METHOD_DEF, };
    }

    // -@cs[CyclomaticComplexity] Too complex to break apart.
    @Override
    public void visitToken(final DetailAST ast) {
        final TextBlock javadoc =
            getFileContents().getJavadocBefore(ast.getLineNo());

        final boolean containsTag = containsJavadocTag(javadoc);
        if (containsTag && !JavadocTagInfo.INHERIT_DOC.isValidOn(ast)) {
            log(ast, MSG_KEY_TAG_NOT_VALID_ON,
                JavadocTagInfo.INHERIT_DOC.getText());
        }
        else {
            boolean check = true;

            if (javaFiveCompatibility) {
                final DetailAST defOrNew = ast.getParent().getParent();

                if (defOrNew.findFirstToken(TokenTypes.EXTENDS_CLAUSE) != null
                    || defOrNew.findFirstToken(TokenTypes.IMPLEMENTS_CLAUSE) != null
                    || defOrNew.getType() == TokenTypes.LITERAL_NEW) {
                    check = false;
                }
            }

            if (check
                && containsTag
                && !AnnotationUtil.containsAnnotation(ast, OVERRIDE)
                && !AnnotationUtil.containsAnnotation(ast, FQ_OVERRIDE)) {
                log(ast, MSG_KEY_ANNOTATION_MISSING_OVERRIDE);
            }
        }
    }

    /**
     * Checks to see if the text block contains a inheritDoc tag.
     *
     * @param javadoc the javadoc of the AST
     * @return true if contains the tag
     */
    private static boolean containsJavadocTag(final TextBlock javadoc) {
        boolean javadocTag = false;

        if (javadoc != null) {
            final String[] lines = javadoc.getText();

            for (final String line : lines) {
                final Matcher matchInheritDoc =
                    MATCH_INHERIT_DOC.matcher(line);

                if (matchInheritDoc.find()) {
                    javadocTag = true;
                    break;
                }
            }
        }
        return javadocTag;
    }

}
