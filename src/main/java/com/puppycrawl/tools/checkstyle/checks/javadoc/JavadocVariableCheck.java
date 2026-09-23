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
import java.util.Optional;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.naming.AccessModifierOption;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;
import com.puppycrawl.tools.checkstyle.utils.NullUtil;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;
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
    public static final String MSG_JAVADOC_MISSING = "javadoc.missing.named";

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
     * Control whether the access modifier of the type that declares the field is
     * taken into account, so that a field is checked only when the type that holds
     * it is itself in one of the {@code accessModifiers}.
     */
    private boolean considerEnclosingScope;

    /**
     * Creates a new {@code JavadocVariableCheck} instance.
     */
    public JavadocVariableCheck() {
        // no code by default
    }

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

    /**
     * Setter to control whether the access modifier of the type that declares the field is
     * taken into account, so that a field is checked only when the type that holds it is
     * itself in one of the {@code accessModifiers}.
     *
     * @param considerEnclosingScope whether to take the enclosing type into account.
     * @since 14.2.0
     */
    public void setConsiderEnclosingScope(boolean considerEnclosingScope) {
        this.considerEnclosingScope = considerEnclosingScope;
    }

    @Override
    public boolean isCommentNodesRequired() {
        return true;
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
        if (shouldCheck(ast)) {
            final DetailAST blockCommentNode = JavadocUtil.getAttachedJavadocComment(ast);
            if (blockCommentNode == null) {
                final String name = NullUtil.notNull(ast.findFirstToken(TokenTypes.IDENT))
                    .getText();
                log(ast, MSG_JAVADOC_MISSING, name);
            }
        }
    }

    /**
     * Decides whether the variable name of an AST is in the ignore list.
     *
     * @param ast the AST to check
     * @return true if the variable name of ast is in the ignore list.
     */
    private boolean isIgnored(DetailAST ast) {
        final String name = NullUtil.notNull(ast.findFirstToken(TokenTypes.IDENT))
            .getText();
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
            result = matchAccessModifiers(accessModifier)
                    && matchEnclosingScope(ast);
        }
        return result;
    }

    /**
     * Checks whether the type that declares the field is itself in one of the configured
     * access modifiers. As in {@code JavadocMethod}, a field without a named enclosing
     * type, such as a field of an anonymous class, is not reachable through one and is
     * not checked.
     *
     * @param ast the field to check.
     * @return whether the enclosing type allows the field to be checked.
     */
    private boolean matchEnclosingScope(DetailAST ast) {
        boolean result = true;
        if (considerEnclosingScope) {
            DetailAST node = ast;
            if (node.getType() == TokenTypes.ENUM_CONSTANT_DEF) {
                // the modifier of the enum already stands for the constant itself,
                // so the type around the enum is the one left to look at
                while (node.getType() != TokenTypes.ENUM_DEF) {
                    node = node.getParent();
                }
            }
            result = findEnclosingTypeDeclaration(node)
                    .map(CheckUtil::getAccessModifierFromModifiersToken)
                    .map(this::matchAccessModifiers)
                    .orElse(Boolean.FALSE);
        }
        return result;
    }

    /**
     * Finds the nearest type declaration above a node, records included. The search
     * stops at an anonymous class, since it has no access modifier of its own.
     *
     * @param node the node to start above.
     * @return the enclosing type declaration, or empty if there is none.
     */
    private static Optional<DetailAST> findEnclosingTypeDeclaration(DetailAST node) {
        Optional<DetailAST> result = Optional.empty();
        DetailAST token = node.getParent();
        while (token != null && token.getType() != TokenTypes.LITERAL_NEW) {
            if (TokenUtil.isTypeDeclaration(token.getType())) {
                result = Optional.of(token);
                break;
            }
            token = token.getParent();
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

}
