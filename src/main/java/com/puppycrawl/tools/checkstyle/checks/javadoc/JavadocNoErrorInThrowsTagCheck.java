///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.util.HashSet;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * <div>
 * Checks that {@code Error} types are not documented in {@code @throws} or
 * {@code @exception} Javadoc tags.
 * </div>
 *
 * <p>
 * Per the documentation comments style guide, errors generally should not be
 * documented because they are unpredictable. This check reports documented
 * throwable names whose simple name ends with {@code Error}, unless the same
 * Error type is explicitly thrown with {@code throw new} in the documented
 * method or constructor.
 * </p>
 *
 * @since 14.1.0
 */
@FileStatefulCheck
public class JavadocNoErrorInThrowsTagCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "javadoc.no.error.in.throws.tag";

    /** Type names explicitly thrown in the current documented construct. */
    private final Set<String> thrownTypeNames = new HashSet<>();

    /**
     * Creates a new {@code JavadocNoErrorInThrowsTagCheck} instance.
     */
    public JavadocNoErrorInThrowsTagCheck() {
        // no code by default
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public final int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
        };
    }

    @Override
    public int[] getDefaultJavadocTokens() {
        return getRequiredJavadocTokens();
    }

    @Override
    public int[] getRequiredJavadocTokens() {
        return new int[] {
            JavadocCommentsTokenTypes.THROWS_BLOCK_TAG,
            JavadocCommentsTokenTypes.EXCEPTION_BLOCK_TAG,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST blockCommentNode = JavadocUtil.getAttachedJavadocComment(ast);
        if (blockCommentNode != null) {
            collectThrownErrorTypes(ast);
            try {
                super.visitToken(blockCommentNode);
            }
            finally {
                thrownTypeNames.clear();
            }
        }
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        final DetailNode identifier = JavadocUtil.findFirstToken(
                ast, JavadocCommentsTokenTypes.IDENTIFIER);

        if (identifier != null
                && isErrorType(identifier.getText())
                && !isExplicitlyThrown(identifier.getText())) {
            final String tagName = getTagName(ast);
            log(ast, MSG_KEY, identifier.getText(), tagName);
        }
    }

    /**
     * Collects type names explicitly thrown by {@code throw new} in a construct.
     *
     * @param ast documented construct
     */
    private void collectThrownErrorTypes(DetailAST ast) {
        DetailAST current = ast;
        while (current != null) {
            if (current.getType() == TokenTypes.LITERAL_THROW
                    && !isInIgnoreBlock(ast, current)) {
                collectThrownErrorType(current);
            }

            if (current.hasChildren()) {
                current = current.getFirstChild();
            }
            else {
                while (!current.equals(ast) && current.getNextSibling() == null) {
                    current = current.getParent();
                }
                if (current.equals(ast)) {
                    current = null;
                }
                else {
                    current = current.getNextSibling();
                }
            }
        }
    }

    /**
     * Collects type name from a {@code throw new} statement.
     *
     * @param throwAst throw statement
     */
    private void collectThrownErrorType(DetailAST throwAst) {
        final DetailAST expressionAst = throwAst.getFirstChild();
        final DetailAST firstExpressionAst = expressionAst.getFirstChild();
        if (firstExpressionAst.getType() == TokenTypes.LITERAL_NEW) {
            final String typeName = FullIdent.createFullIdentBelow(firstExpressionAst)
                    .getText();
            thrownTypeNames.add(getSimpleName(typeName));
        }
    }

    /**
     * Checks if a 'throw' usage is contained within a block that should be ignored.
     * Such blocks consist of try (with catch) blocks, local classes, anonymous classes,
     * and lambda expressions. Note that a try block without catch is not considered.
     *
     * @param constructAst DetailAST node representing the documented construct
     * @param throwAst DetailAST node representing the 'throw' literal
     * @return true if throwAst is inside a block that should be ignored
     */
    private static boolean isInIgnoreBlock(DetailAST constructAst, DetailAST throwAst) {
        DetailAST ancestor = throwAst;
        while (!ancestor.equals(constructAst)) {
            if (ancestor.getType() == TokenTypes.LAMBDA
                    || ancestor.getType() == TokenTypes.OBJBLOCK
                    || ancestor.findFirstToken(TokenTypes.LITERAL_CATCH) != null) {
                break;
            }
            if (ancestor.getType() == TokenTypes.LITERAL_CATCH
                    || ancestor.getType() == TokenTypes.LITERAL_FINALLY) {
                ancestor = ancestor.getParent();
            }
            ancestor = ancestor.getParent();
        }
        return !ancestor.equals(constructAst);
    }

    /**
     * Checks whether class name is an Error type name.
     *
     * @param className class name
     * @return true if class name is an Error type name
     */
    private static boolean isErrorType(String className) {
        return className.endsWith("Error");
    }

    /**
     * Checks whether Error type is explicitly thrown in current documented construct.
     *
     * @param className class name
     * @return true if Error type is explicitly thrown in current documented construct
     */
    private boolean isExplicitlyThrown(String className) {
        return thrownTypeNames.contains(getSimpleName(className));
    }

    /**
     * Gets simple class name.
     *
     * @param className class name
     * @return simple class name
     */
    private static String getSimpleName(String className) {
        return className.substring(className.lastIndexOf('.') + 1);
    }

    /**
     * Gets the Javadoc tag name from a throws or exception block tag.
     *
     * @param ast throws or exception block tag
     * @return tag name with leading at sign
     */
    private static String getTagName(DetailNode ast) {
        return "@" + JavadocUtil.findFirstToken(ast,
                JavadocCommentsTokenTypes.TAG_NAME).getText();
    }

}
