////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTagInfo;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtility;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * <p>
 * This class is used to verify that the {@link Override Override}
 * annotation is present when the inheritDoc javadoc tag is present.
 * </p>
 *
 * <p>
 * Rationale: The {@link Override Override} annotation helps
 * compiler tools ensure that an override is actually occurring.  It is
 * quite easy to accidentally overload a method or hide a static method
 * and using the {@link Override Override} annotation points
 * out these problems.
 * </p>
 *
 * <p>
 * This check will log a violation if using the inheritDoc tag on a method that
 * is not valid (ex: private, or static method).
 * </p>
 *
 * <p>
 * There is a slight difference between the Override annotation in Java 5 versus
 * Java 6 and above. In Java 5, any method overridden from an interface cannot
 * be annotated with Override. In Java 6 this behavior is allowed.
 * </p>
 *
 * <p>
 * As a result of the aforementioned difference between Java 5 and Java 6, a
 * property called {@code javaFiveCompatibility } is available. This
 * property will only check classes, interfaces, etc. that do not contain the
 * extends or implements keyword or are not anonymous classes. This means it
 * only checks methods overridden from {@code java.lang.Object}
 *
 * <b>Java 5 Compatibility mode severely limits this check. It is recommended to
 * only use it on Java 5 source</b>
 * </p>
 *
 * <pre>
 * &lt;module name=&quot;MissingOverride&quot;&gt;
 *    &lt;property name=&quot;javaFiveCompatibility&quot;
 *        value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @author Travis Schneeberger
 */
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
            CommonUtils.createPattern("\\{\\s*@(inheritDoc)\\s*\\}");

    /**
     * Java 5 compatibility option.
     * @see #setJavaFiveCompatibility(boolean)
     */
    private boolean javaFiveCompatibility;

    /**
     * Sets Java 5 compatibility mode.
     *
     * <p>
     * In Java 5, this check could flag code that is not valid for the Override
     * annotation even though it is a proper override. See the class
     * documentation for more information.
     * </p>
     *
     * <p>
     * Set this to true to turn on Java 5 compatibility mode. Set this to
     * false to turn off Java 5 compatibility mode.
     * </p>
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

    @Override
    public void visitToken(final DetailAST ast) {
        final TextBlock javadoc =
            getFileContents().getJavadocBefore(ast.getLineNo());

        final boolean containsTag = containsJavadocTag(javadoc);
        if (containsTag && !JavadocTagInfo.INHERIT_DOC.isValidOn(ast)) {
            log(ast.getLineNo(), MSG_KEY_TAG_NOT_VALID_ON,
                JavadocTagInfo.INHERIT_DOC.getText());
        }
        else {
            boolean check = true;

            if (javaFiveCompatibility) {
                final DetailAST defOrNew = ast.getParent().getParent();

                if (defOrNew.branchContains(TokenTypes.EXTENDS_CLAUSE)
                    || defOrNew.branchContains(TokenTypes.IMPLEMENTS_CLAUSE)
                    || defOrNew.getType() == TokenTypes.LITERAL_NEW) {
                    check = false;
                }
            }

            if (check
                && containsTag
                && !AnnotationUtility.containsAnnotation(ast, OVERRIDE)
                && !AnnotationUtility.containsAnnotation(ast, FQ_OVERRIDE)) {
                log(ast.getLineNo(), MSG_KEY_ANNOTATION_MISSING_OVERRIDE);
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
