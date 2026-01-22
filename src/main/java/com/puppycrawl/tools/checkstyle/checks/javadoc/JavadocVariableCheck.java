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
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.naming.AccessModifierOption;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
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

    // suppress deprecation until https://github.com/checkstyle/checkstyle/issues/11166
    @Override
    @SuppressWarnings("deprecation")
    public void visitToken(DetailAST ast) {
        if (shouldCheck(ast)) {
            final FileContents contents = getFileContents();
            final TextBlock textBlock =
                contents.getJavadocBefore(ast.getLineNo());

            if (textBlock == null) {
                log(ast, MSG_JAVADOC_MISSING);
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
}
