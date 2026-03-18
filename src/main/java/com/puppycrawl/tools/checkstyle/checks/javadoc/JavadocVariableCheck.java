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

import java.util.Arrays;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.naming.AccessModifierOption;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;
import com.puppycrawl.tools.checkstyle.utils.UnmodifiableCollectionUtil;

/**
 * <div>
 * Checks that a variable has a Javadoc comment. Ignores {@code serialVersionUID} fields.
 * </div>
 *
 * @since 3.0
 */
@StatelessCheck
public class JavadocVariableCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */

    public static final String MSG_JAVADOC_MISSING = "javadoc.missing";
    /**
     * Specify the set of access modifiers used to determine which fields should be checked.
     *  This includes both explicitly declared modifiers and implicit ones, such as package-private
     *  for fields without an explicit modifier. It also accounts for special cases where fields
     *  have implicit modifiers, such as {@code public static final} for interface fields and
     *  {@code public static} for enum constants, or where the nesting types accessibility is more
     *  restrictive and hides the nested field.
     *  Only fields matching the specified modifiers will be analyzed.
     */
    private AccessModifierOption[] accessModifiers = {
        AccessModifierOption.PUBLIC,
        AccessModifierOption.PROTECTED,
        AccessModifierOption.PACKAGE,
        AccessModifierOption.PRIVATE,
    };

    /** Specify the regexp to define variable names to ignore. */
    private Pattern ignoreNamePattern;

    /**
     * Setter to specify the set of access modifiers used to determine which fields should be
     * checked. This includes both explicitly declared modifiers and implicit ones, such as
     * package-private for fields without an explicit modifier. It also accounts for special
     * cases where fields have implicit modifiers, such as {@code public static final}
     * for interface fields and {@code public static} for enum constants, or where the nesting
     * types accessibility is more restrictive and hides the nested field.
     * Only fields matching the specified modifiers will be analyzed.
     *
     * @param accessModifiers access modifiers of fields to check.
     * @since 10.22.0
     */
    public void setAccessModifiers(AccessModifierOption... accessModifiers) {
        this.accessModifiers =
            UnmodifiableCollectionUtil.copyOfArray(accessModifiers, accessModifiers.length);
    }

    /**
     * Setter to specify the regexp to define variable names to ignore.
     *
     * @param pattern a pattern.
     * @since 5.8
     */
    public void setIgnoreNamePattern(Pattern pattern) {
        ignoreNamePattern = pattern;
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.VARIABLE_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
        };
    }

    /*
     * Skipping enum values is requested.
     * Checkstyle's issue #1669: https://github.com/checkstyle/checkstyle/issues/1669
     */
    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.VARIABLE_DEF,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (shouldCheck(ast) && !hasJavadocComment(ast)) {
            log(ast, MSG_JAVADOC_MISSING);
        }
    }

    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    /**
     * Decides whether the variable name of an AST is in the ignore list.
     *
     * @param ast the AST to check
     * @return true if the variable name of ast is in the ignore list.
     */
    private boolean isIgnored(DetailAST ast) {
        final String name = ast.findFirstToken(TokenTypes.IDENT).getText();
        return ignoreNamePattern != null && ignoreNamePattern.matcher(name).matches()
            || "serialVersionUID".equals(name);
    }

    /**
     * Checks whether a method has the correct access modifier to be checked.
     *
     * @param accessModifier the access modifier of the method.
     * @return whether the method matches the expected access modifier.
     */
    private boolean matchAccessModifiers(AccessModifierOption accessModifier) {
        return Arrays.stream(accessModifiers)
            .anyMatch(modifier -> modifier == accessModifier);
    }

    /**
     * Whether we should check this node.
     *
     * @param ast a given node.
     * @return whether we should check a given node.
     */
    private boolean shouldCheck(final DetailAST ast) {
        boolean result = false;
        if (!ScopeUtil.isInCodeBlock(ast) && !isIgnored(ast)) {
            final AccessModifierOption accessModifier =
                    getAccessModifierFromModifiersTokenWithPrivateEnumSupport(ast);
            result = matchAccessModifiers(accessModifier);
        }
        return result;
    }

    /**
     * A derivative of {@link CheckUtil#getAccessModifierFromModifiersToken(DetailAST)} that
     * considers enum definitions' visibility when evaluating the accessibility of an enum
     * constant.
     * <br>
     * <a href="https://github.com/checkstyle/checkstyle/pull/16787/files#r2073671898">Implemented
     * separately</a> to reduce scope of fix for
     * <a href="https://github.com/checkstyle/checkstyle/issues/16786">issue #16786</a> until a
     * wider solution can be developed.
     *
     * @param ast the token of the method/constructor.
     * @return the access modifier of the method/constructor.
     */
    public static AccessModifierOption getAccessModifierFromModifiersTokenWithPrivateEnumSupport(
            DetailAST ast) {
        // In some scenarios we want to investigate a parent AST instead
        DetailAST selectedAst = ast;

        if (selectedAst.getType() == TokenTypes.ENUM_CONSTANT_DEF) {
            // Enum constants don't have modifiers
            // implicitly public but validate against parent(s)
            while (selectedAst.getType() != TokenTypes.ENUM_DEF) {
                selectedAst = selectedAst.getParent();
            }
        }

        return CheckUtil.getAccessModifierFromModifiersToken(selectedAst);
    }

    /**
     * Checks whether the variable definition has a Javadoc comment.
     * For {@code VARIABLE_DEF}, Javadoc is searched among children of
     * the {@code MODIFIERS} node and the {@code TYPE} node.
     * For {@code ENUM_CONSTANT_DEF}, Javadoc is searched among direct
     * children of the constant definition.
     *
     * @param variableDefAst the AST of the variable definition to check.
     * @return true if a Javadoc comment is found, false otherwise.
     */
    private static boolean hasJavadocComment(DetailAST variableDefAst) {
        final boolean result;
        if (variableDefAst.getType() == TokenTypes.VARIABLE_DEF) {
            result = hasJavadocCommentOnVariable(variableDefAst);
        }
        else {
            // For enum constants, Javadoc can be attached to the constant definition itself, or to its annotations if they exist.
            final DetailAST annotations = variableDefAst.findFirstToken(TokenTypes.ANNOTATIONS);
            result = hasJavadocCommentOnAnnotations(annotations) || hasBlockCommentJavadoc(variableDefAst.getFirstChild());
        }
        return result;
    }

    /**
     * Checks whether a {@code VARIABLE_DEF} has a Javadoc comment
     * attached to its modifiers, annotations, or type.
     * For a {@code VARIABLE_DEF}, Javadoc can be attached to the modifiers,
     * annotations, or type.
     * This method checks for Javadoc comments in all of these locations
     * to ensure that the variable definition is properly documented.
     *
     * @param variableDefAst the AST of the variable definition to check.
     * @return true if a Javadoc comment is found, false otherwise.
     */
    private static boolean hasJavadocCommentOnVariable(
        DetailAST variableDefAst) {
        final DetailAST modifiers =
            variableDefAst.findFirstToken(TokenTypes.MODIFIERS);

        boolean found = hasBlockCommentJavadoc(modifiers.getFirstChild());

        if (!found) {
            found = hasJavadocCommentOnAnnotations(modifiers);
        }

        if (!found) {
            final DetailAST type =
                variableDefAst.findFirstToken(TokenTypes.TYPE);
            DetailAST firstChild = type.getFirstChild();
            found = hasBlockCommentJavadoc(firstChild);

            while (!found) {
                if (firstChild.getType() != TokenTypes.DOT) {
                    break;
                }

                firstChild = firstChild.getFirstChild();
                found = hasBlockCommentJavadoc(firstChild);
            }
        }

        return found;
    }

    /**
     * Checks whether any annotation within the modifiers has a
     * Javadoc comment as a child node.
     *
     * @param modifiers the MODIFIERS node.
     * @return true if a Javadoc comment exists inside any annotation.
     */
    private static boolean hasJavadocCommentOnAnnotations(
            DetailAST modifiers) {
        boolean found = false;
        for (DetailAST child = modifiers.getFirstChild();
                child != null; child = child.getNextSibling()) {
            if (hasBlockCommentJavadoc(child.getFirstChild())) {
                found = true;
                break;
            }
        }
        return found;
    }

    /**
     * Checks whether the given AST node or any of its siblings is a
     * block comment that is a Javadoc comment.
     *
     * @param firstChild the first child of the node to check.
     * @return true if a Javadoc block comment is found among the
     *     siblings, false otherwise.
     */
    private static boolean hasBlockCommentJavadoc(DetailAST firstChild) {
        boolean found = false;
        for (DetailAST child = firstChild; child != null;
                child = child.getNextSibling()) {
            if (child.getType() == TokenTypes.BLOCK_COMMENT_BEGIN
                    && JavadocUtil.isJavadocComment(
                        JavadocUtil.getBlockCommentContent(child))) {
                found = true;
                break;
            }
        }
        return found;
    }
}
