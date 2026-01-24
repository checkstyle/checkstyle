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
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
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

    /** Restrictiveness level for public access (least restrictive). */
    private static final int LEVEL_PUBLIC = 0;

    /** Restrictiveness level for protected access. */
    private static final int LEVEL_PROTECTED = 1;

    /** Restrictiveness level for package-private access. */
    private static final int LEVEL_PACKAGE = 2;

    /** Restrictiveness level for private access (most restrictive). */
    private static final int LEVEL_PRIVATE = 3;

    /**
     * Map of access modifiers to their restrictiveness levels (higher = more restrictive).
     * Uses EnumMap + unmodifiable wrapper for immutability.
     */
    private static final Map<AccessModifierOption, Integer> RESTRICTIVENESS_LEVELS;

    static {
        final Map<AccessModifierOption, Integer> map =
                new EnumMap<>(AccessModifierOption.class);

        map.put(AccessModifierOption.PUBLIC, LEVEL_PUBLIC);
        map.put(AccessModifierOption.PROTECTED, LEVEL_PROTECTED);
        map.put(AccessModifierOption.PACKAGE, LEVEL_PACKAGE);
        map.put(AccessModifierOption.PRIVATE, LEVEL_PRIVATE);

        RESTRICTIVENESS_LEVELS = Collections.unmodifiableMap(map);
    }

    /**
     * Specify the set of access modifiers used to determine which fields should be checked.
     * This includes both explicitly declared modifiers and implicit ones, such as package-private
     * for fields without an explicit modifier. It also accounts for special cases where fields
     * have implicit modifiers, such as {@code public static final} for interface fields and
     * {@code public static} for enum constants, or where the nesting types accessibility is more
     * restrictive and hides the nested field.
     * Only fields matching the specified modifiers will be analyzed.
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
     * constant, and also considers class/interface visibility when evaluating the accessibility
     * of a field.
     * <br>
     * <a href="https://github.com/checkstyle/checkstyle/pull/16787/files#r2073671898">Implemented
     * separately</a> to reduce scope of fix for
     * <a href="https://github.com/checkstyle/checkstyle/issues/16786">issue #16786</a> until a
     * wider solution can be developed.
     *
     * @param ast the token of the method/constructor/field.
     * @return the access modifier of the method/constructor/field.
     */
    public static AccessModifierOption getAccessModifierFromModifiersTokenWithPrivateEnumSupport(
            DetailAST ast) {
        final AccessModifierOption declaredModifier =
                CheckUtil.getAccessModifierFromModifiersToken(ast);
        final AccessModifierOption result;

        if (ScopeUtil.isInInterfaceOrAnnotationBlock(ast)) {
            result = declaredModifier;
        }
        else {
            final AccessModifierOption parentModifier = getParentClassModifier(ast);
            result = getMoreRestrictive(declaredModifier, parentModifier);
        }

        return result;
    }

    /**
     * Gets the access modifier of the parent class/interface/enum containing the given field.
     * For nested classes, finds the most restrictive access modifier among all ancestor
     * class-like definitions, excluding the top-level class.
     *
     * @param ast the field AST node.
     * @return the access modifier of the parent class/interface/enum.
     */
    private static AccessModifierOption getParentClassModifier(DetailAST ast) {
        AccessModifierOption mostRestrictive = AccessModifierOption.PUBLIC;
        DetailAST parent = ast;

        while (parent != null) {
            switch (parent.getType()) {
                case TokenTypes.CLASS_DEF,
                        TokenTypes.INTERFACE_DEF,
                        TokenTypes.ENUM_DEF,
                        TokenTypes.RECORD_DEF -> {
                    if (parent.getParent().getType() == TokenTypes.OBJBLOCK) {
                        final AccessModifierOption parentModifier =
                                CheckUtil.getAccessModifierFromModifiersToken(parent);
                        mostRestrictive = getMoreRestrictive(mostRestrictive, parentModifier);
                    }
                }
                default -> {
                    break;
                }
            }
            parent = parent.getParent();
        }

        return mostRestrictive;
    }

    /**
     * Returns the more restrictive of two access modifiers.
     * Order from least to most restrictive: PUBLIC, PROTECTED, PACKAGE, PRIVATE.
     *
     * @param modifier1 the first access modifier.
     * @param modifier2 the second access modifier.
     * @return the more restrictive access modifier.
     */
    private static AccessModifierOption getMoreRestrictive(
            AccessModifierOption modifier1,
            AccessModifierOption modifier2) {

        final int level1 = getRestrictiveLevel(modifier1);
        final int level2 = getRestrictiveLevel(modifier2);

        final int maxLevel = Math.max(level1, level2);

        final AccessModifierOption result;

        if (maxLevel == level1) {
            result = modifier1;
        }
        else {
            result = modifier2;
        }
        return result;
    }

    /**
     * Gets the restrictiveness level of an access modifier.
     *
     * @param modifier the access modifier.
     * @return the restrictiveness level (higher = more restrictive).
     */
    /* package */ static int getRestrictiveLevel(AccessModifierOption modifier) {
        return RESTRICTIVENESS_LEVELS.get(modifier);
    }
}
