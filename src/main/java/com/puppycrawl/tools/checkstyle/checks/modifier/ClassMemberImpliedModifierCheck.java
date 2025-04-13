////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.checks.modifier;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;

/**
 * <div>
 * Checks for implicit modifiers on nested types in classes and records.
 * </div>
 *
 * <p>
 * This check is effectively the opposite of
 * <a href="https://checkstyle.org/checks/modifier/redundantmodifier.html#RedundantModifier">
 * RedundantModifier</a>.
 * It checks the modifiers on nested types in classes and records, ensuring that certain modifiers
 * are explicitly specified even though they are actually redundant.
 * </p>
 *
 * <p>
 * Nested enums, interfaces, and records within a class are always {@code static} and as such the
 * compiler does not require the {@code static} modifier. This check provides the ability to enforce
 * that the {@code static} modifier is explicitly coded and not implicitly added by the compiler.
 * </p>
 * <pre>
 * public final class Person {
 *   enum Age {  // violation
 *     CHILD, ADULT
 *   }
 * }
 * </pre>
 *
 * <p>
 * Rationale for this check: Nested enums, interfaces, and records are treated differently from
 * nested classes as they are only allowed to be {@code static}. Developers should not need to
 * remember this rule, and this check provides the means to enforce that the modifier is coded
 * explicitly.
 * </p>
 * <ul>
 * <li>
 * Property {@code violateImpliedStaticOnNestedEnum} - Control whether to enforce that
 * {@code static} is explicitly coded on nested enums in classes and records.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code violateImpliedStaticOnNestedInterface} - Control whether to enforce that
 * {@code static} is explicitly coded on nested interfaces in classes and records.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code violateImpliedStaticOnNestedRecord} - Control whether to enforce that
 * {@code static} is explicitly coded on nested records in classes and records.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code class.implied.modifier}
 * </li>
 * </ul>
 *
 * @since 8.16
 */
@StatelessCheck
public class ClassMemberImpliedModifierCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_KEY = "class.implied.modifier";

    /** Name for 'static' keyword. */
    private static final String STATIC_KEYWORD = "static";

    /**
     * Control whether to enforce that {@code static} is explicitly coded
     * on nested enums in classes and records.
     */
    private boolean violateImpliedStaticOnNestedEnum = true;

    /**
     * Control whether to enforce that {@code static} is explicitly coded
     * on nested interfaces in classes and records.
     */
    private boolean violateImpliedStaticOnNestedInterface = true;

    /**
     * Control whether to enforce that {@code static} is explicitly coded
     * on nested records in classes and records.
     */
    private boolean violateImpliedStaticOnNestedRecord = true;

    /**
     * Setter to control whether to enforce that {@code static} is explicitly coded
     * on nested enums in classes and records.
     *
     * @param violateImplied
     *        True to perform the check, false to turn the check off.
     * @since 8.16
     */
    public void setViolateImpliedStaticOnNestedEnum(boolean violateImplied) {
        violateImpliedStaticOnNestedEnum = violateImplied;
    }

    /**
     * Setter to control whether to enforce that {@code static} is explicitly coded
     * on nested interfaces in classes and records.
     *
     * @param violateImplied
     *        True to perform the check, false to turn the check off.
     * @since 8.16
     */
    public void setViolateImpliedStaticOnNestedInterface(boolean violateImplied) {
        violateImpliedStaticOnNestedInterface = violateImplied;
    }

    /**
     * Setter to control whether to enforce that {@code static} is explicitly coded
     * on nested records in classes and records.
     *
     * @param violateImplied
     *        True to perform the check, false to turn the check off.
     * @since 8.36
     */
    public void setViolateImpliedStaticOnNestedRecord(boolean violateImplied) {
        violateImpliedStaticOnNestedRecord = violateImplied;
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.RECORD_DEF,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (isInTypeBlock(ast)) {
            final DetailAST modifiers = ast.findFirstToken(TokenTypes.MODIFIERS);
            switch (ast.getType()) {
                case TokenTypes.ENUM_DEF:
                    if (violateImpliedStaticOnNestedEnum
                        && modifiers.findFirstToken(TokenTypes.LITERAL_STATIC) == null) {
                        log(ast, MSG_KEY, STATIC_KEYWORD);
                    }
                    break;
                case TokenTypes.INTERFACE_DEF:
                    if (violateImpliedStaticOnNestedInterface
                        && modifiers.findFirstToken(TokenTypes.LITERAL_STATIC) == null) {
                        log(ast, MSG_KEY, STATIC_KEYWORD);
                    }
                    break;
                case TokenTypes.RECORD_DEF:
                    if (violateImpliedStaticOnNestedRecord
                        && modifiers.findFirstToken(TokenTypes.LITERAL_STATIC) == null) {
                        log(ast, MSG_KEY, STATIC_KEYWORD);
                    }
                    break;
                default:
                    throw new IllegalStateException(ast.toString());
            }
        }
    }

    /**
     * Checks if ast is in a class, enum, anon class or record block.
     *
     * @param ast the current ast
     * @return true if ast is in a class, enum, anon class or record
     */
    private static boolean isInTypeBlock(DetailAST ast) {
        return ScopeUtil.isInScope(ast, Scope.ANONINNER)
            || ScopeUtil.isInClassBlock(ast)
            || ScopeUtil.isInEnumBlock(ast)
            || ScopeUtil.isInRecordBlock(ast);
    }

}
