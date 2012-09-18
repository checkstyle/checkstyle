////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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

import com.puppycrawl.tools.checkstyle.api.AnnotationUtility;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.JavadocTagInfo;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Utils;

/**
 * <p>
 * This class is used to verify that the {@link java.lang.Override Override}
 * annotation is present when the inheritDoc javadoc tag is present.
 * </p>
 *
 * <p>
 * Rationale: The {@link java.lang.Override Override} annotation helps
 * compiler tools ensure that an override is actually occurring.  It is
 * quite easy to accidentally overload a method or hide a static method
 * and using the {@link java.lang.Override Override} annotation points
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
 * property called <code> javaFiveCompatibility </code> is available. This
 * property will only check classes, interfaces, etc. that do not contain the
 * extends or implements keyword or are not anonymous classes. This means it
 * only checks methods overridden from <code>java.lang.Object</code>
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
public final class MissingOverrideCheck extends Check
{
    /** {@link Override Override} annotation name */
    private static final String OVERRIDE = "Override";

    /** fully-qualified {@link Override Override} annotation name */
    private static final String FQ_OVERRIDE = "java.lang." + OVERRIDE;

    /** compiled regexp to match Javadoc tags with no argument and {} * */
    private static final Pattern MATCH_INHERITDOC =
        Utils.createPattern("\\{\\s*@(inheritDoc)\\s*\\}");

    /** @see MissingDeprecatedCheck#setJavaFiveCompatibility(boolean) */
    private boolean mJavaFiveCompatibility;

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
     * @param aCompatibility compatibility or not
     */
    public void setJavaFiveCompatibility(final boolean aCompatibility)
    {
        this.mJavaFiveCompatibility = aCompatibility;
    }

    /** {@inheritDoc} */
    @Override
    public int[] getDefaultTokens()
    {
        return this.getRequiredTokens();
    }

    /** {@inheritDoc} */
    @Override
    public int[] getAcceptableTokens()
    {
        return this.getRequiredTokens();
    }

    /** {@inheritDoc} */
    @Override
    public int[] getRequiredTokens()
    {
        return new int[]
        {TokenTypes.METHOD_DEF, };
    }

    /** {@inheritDoc} */
    @Override
    public void visitToken(final DetailAST aAST)
    {
        final TextBlock javadoc =
            this.getFileContents().getJavadocBefore(aAST.getLineNo());


        final boolean containsTag = this.containsJavadocTag(javadoc);
        if (containsTag && !JavadocTagInfo.INHERIT_DOC.isValidOn(aAST)) {
            this.log(aAST.getLineNo(), "tag.not.valid.on",
                JavadocTagInfo.INHERIT_DOC.getText());
            return;
        }

        if (this.mJavaFiveCompatibility) {
            final DetailAST defOrNew = aAST.getParent().getParent();

            if (defOrNew.branchContains(TokenTypes.EXTENDS_CLAUSE)
                || defOrNew.branchContains(TokenTypes.IMPLEMENTS_CLAUSE)
                || defOrNew.getType() == TokenTypes.LITERAL_NEW)
            {
                return;
            }
        }

        if (containsTag
            && (!AnnotationUtility.containsAnnotation(aAST, OVERRIDE)
            && !AnnotationUtility.containsAnnotation(aAST, FQ_OVERRIDE)))
        {
            this.log(aAST.getLineNo(), "annotation.missing.override");
        }
    }

    /**
     * Checks to see if the text block contains a inheritDoc tag.
     *
     * @param aJavadoc the javadoc of the AST
     * @return true if contains the tag
     */
    private boolean containsJavadocTag(final TextBlock aJavadoc)
    {
        if (aJavadoc == null) {
            return false;
        }

        final String[] lines = aJavadoc.getText();

        for (final String line : lines) {
            final Matcher matchInheritDoc =
                MissingOverrideCheck.MATCH_INHERITDOC.matcher(line);

            if (matchInheritDoc.find()) {
                return true;
            }
        }
        return false;
    }
}
