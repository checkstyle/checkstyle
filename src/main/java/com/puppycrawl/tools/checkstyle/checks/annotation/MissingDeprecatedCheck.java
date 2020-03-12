////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTagInfo;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;

/**
 * <p>
 * Verifies that the annotation {@code @Deprecated} and the Javadoc tag
 * {@code @deprecated} are both present when either of them is present.
 * </p>
 * <p>
 * Both ways of flagging deprecation serve their own purpose.
 * The &#64;Deprecated annotation is used for compilers and development tools.
 * The &#64;deprecated javadoc tag is used to document why something is deprecated
 * and what, if any, alternatives exist.
 * </p>
 * <p>
 * In order to properly mark something as deprecated both forms of
 * deprecation should be present.
 * </p>
 * <p>
 * Package deprecation is a exception to the rule of always using the
 * javadoc tag and annotation to deprecate.  It is not clear if the javadoc
 * tool will support it or not as newer versions keep flip flopping on if
 * it is supported or will cause an error. See
 * <a href="https://bugs.openjdk.java.net/browse/JDK-8160601">JDK-8160601</a>.
 * The deprecated javadoc tag is currently the only way to say why the package
 * is deprecated and what to use instead.  Until this is resolved, if you don't
 * want to print violations on package-info, you can use a
 * <a href="https://checkstyle.org/config_filters.html">filter</a> to ignore
 * these files until the javadoc tool faithfully supports it. An example config
 * using SuppressionSingleFilter is:
 * </p>
 * <pre>
 * &lt;!-- required till https://bugs.openjdk.java.net/browse/JDK-8160601 --&gt;
 * &lt;module name="SuppressionSingleFilter"&gt;
 *     &lt;property name="checks" value="MissingDeprecatedCheck"/&gt;
 *     &lt;property name="files" value="package-info\.java"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <ul>
 * <li>
 * Property {@code violateExecutionOnNonTightHtml} - Control when to
 * print violations if the Javadoc being examined by this check violates the
 * tight html rules defined at
 * <a href="https://checkstyle.org/writingjavadocchecks.html#Tight-HTML_rules">
 * Tight-HTML Rules</a>.
 * Default value is {@code false}.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;MissingDeprecated&quot;/&gt;
 * </pre>
 * <p>
 * Examples of validating source code:
 * </p>
 * <pre>
 * &#64;Deprecated
 * public static final int MY_CONST = 123456; // no violation
 *
 * &#47;** This javadoc is missing deprecated tag. *&#47;
 * &#64;Deprecated
 * public static final int COUNTER = 10; // violation
 * </pre>
 *
 * @since 5.0
 */
@StatelessCheck
public final class MissingDeprecatedCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_ANNOTATION_MISSING_DEPRECATED =
            "annotation.missing.deprecated";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_JAVADOC_DUPLICATE_TAG =
            "javadoc.duplicateTag";

    /** {@link Deprecated Deprecated} annotation name. */
    private static final String DEPRECATED = "Deprecated";

    /** Fully-qualified {@link Deprecated Deprecated} annotation name. */
    private static final String FQ_DEPRECATED = "java.lang." + DEPRECATED;

    /** List of token types to find parent of. */
    private static final Set<Integer> TYPES_HASH_SET = new HashSet<>(Arrays.asList(
            TokenTypes.TYPE, TokenTypes.MODIFIERS, TokenTypes.ANNOTATION,
            TokenTypes.ANNOTATIONS, TokenTypes.ARRAY_DECLARATOR,
            TokenTypes.TYPE_PARAMETERS, TokenTypes.DOT));

    @Override
    public int[] getDefaultJavadocTokens() {
        return getRequiredJavadocTokens();
    }

    @Override
    public int[] getRequiredJavadocTokens() {
        return new int[] {
            JavadocTokenTypes.JAVADOC,
        };
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        final DetailAST parentAst = getParent(getBlockCommentAst());

        final boolean containsAnnotation =
            AnnotationUtil.containsAnnotation(parentAst, DEPRECATED)
            || AnnotationUtil.containsAnnotation(parentAst, FQ_DEPRECATED);

        final boolean containsJavadocTag = containsDeprecatedTag(ast);

        if (containsAnnotation ^ containsJavadocTag) {
            log(parentAst.getLineNo(), MSG_KEY_ANNOTATION_MISSING_DEPRECATED);
        }
    }

    /**
     * Checks to see if the javadoc contains a deprecated tag.
     *
     * @param javadoc the javadoc of the AST
     * @return true if contains the tag
     */
    private boolean containsDeprecatedTag(DetailNode javadoc) {
        boolean found = false;
        for (DetailNode child : javadoc.getChildren()) {
            if (child.getType() == JavadocTokenTypes.JAVADOC_TAG
                    && child.getChildren()[0].getType() == JavadocTokenTypes.DEPRECATED_LITERAL) {
                if (found) {
                    log(child.getLineNumber(), MSG_KEY_JAVADOC_DUPLICATE_TAG,
                            JavadocTagInfo.DEPRECATED.getText());
                }
                found = true;
            }
        }
        return found;
    }

    /**
     * Returns the parent node of the comment.
     * @param commentBlock child node.
     * @return parent node.
     */
    private static DetailAST getParent(DetailAST commentBlock) {
        DetailAST result = commentBlock.getParent();

        if (result == null) {
            result = commentBlock.getNextSibling();
        }

        while (true) {
            final int type = result.getType();
            if (TYPES_HASH_SET.contains(type)) {
                result = result.getParent();
            }
            else if (type == TokenTypes.SINGLE_LINE_COMMENT) {
                result = result.getNextSibling();
            }
            else {
                break;
            }
        }

        return result;
    }

}
