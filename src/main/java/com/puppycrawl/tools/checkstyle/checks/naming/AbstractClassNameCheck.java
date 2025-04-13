///
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

package com.puppycrawl.tools.checkstyle.checks.naming;

import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Ensures that the names of abstract classes conforming to some pattern
 * and check that {@code abstract} modifier exists.
 * </div>
 *
 * <p>
 * Rationale: Abstract classes are convenience base class implementations of
 * interfaces. For this reason, it should be made obvious that a given class
 * is abstract by prefacing the class name with 'Abstract'.
 * </p>
 * <ul>
 * <li>
 * Property {@code format} - Specify valid identifiers.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "^Abstract.+$"}.</li>
 * <li>
 * Property {@code ignoreModifier} - Control whether to ignore checking for the
 * {@code abstract} modifier on classes that match the name.
 * Type is {@code boolean}.
 * Default value is {@code false}.</li>
 * <li>
 * Property {@code ignoreName} - Control whether to ignore checking the name.
 * Realistically only useful if using the check to identify that match name and
 * do not have the {@code abstract} modifier.
 * Type is {@code boolean}.
 * Default value is {@code false}.
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
 * {@code illegal.abstract.class.name}
 * </li>
 * <li>
 * {@code no.abstract.class.modifier}
 * </li>
 * </ul>
 *
 * @since 3.2
 */
@StatelessCheck
public final class AbstractClassNameCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_ILLEGAL_ABSTRACT_CLASS_NAME = "illegal.abstract.class.name";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_NO_ABSTRACT_CLASS_MODIFIER = "no.abstract.class.modifier";

    /**
     * Control whether to ignore checking for the {@code abstract} modifier on
     * classes that match the name.
     */
    private boolean ignoreModifier;

    /**
     * Control whether to ignore checking the name. Realistically only useful
     * if using the check to identify that match name and do not have the
     * {@code abstract} modifier.
     */
    private boolean ignoreName;

    /** Specify valid identifiers. */
    private Pattern format = Pattern.compile("^Abstract.+$");

    /**
     * Setter to control whether to ignore checking for the {@code abstract} modifier on
     * classes that match the name.
     *
     * @param value new value
     * @since 5.3
     */
    public void setIgnoreModifier(boolean value) {
        ignoreModifier = value;
    }

    /**
     * Setter to control whether to ignore checking the name. Realistically only useful if
     * using the check to identify that match name and do not have the {@code abstract} modifier.
     *
     * @param value new value.
     * @since 5.3
     */
    public void setIgnoreName(boolean value) {
        ignoreName = value;
    }

    /**
     * Setter to specify valid identifiers.
     *
     * @param pattern the new pattern
     * @since 3.2
     */
    public void setFormat(Pattern pattern) {
        format = pattern;
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {TokenTypes.CLASS_DEF};
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public void visitToken(DetailAST ast) {
        visitClassDef(ast);
    }

    /**
     * Checks class definition.
     *
     * @param ast class definition for check.
     */
    private void visitClassDef(DetailAST ast) {
        final String className =
            ast.findFirstToken(TokenTypes.IDENT).getText();
        if (isAbstract(ast)) {
            // if class has abstract modifier
            if (!ignoreName && !isMatchingClassName(className)) {
                log(ast, MSG_ILLEGAL_ABSTRACT_CLASS_NAME, className, format.pattern());
            }
        }
        else if (!ignoreModifier && isMatchingClassName(className)) {
            log(ast, MSG_NO_ABSTRACT_CLASS_MODIFIER, className);
        }
    }

    /**
     * Checks if declared class is abstract or not.
     *
     * @param ast class definition for check.
     * @return true if a given class declared as abstract.
     */
    private static boolean isAbstract(DetailAST ast) {
        final DetailAST abstractAST = ast.findFirstToken(TokenTypes.MODIFIERS)
            .findFirstToken(TokenTypes.ABSTRACT);

        return abstractAST != null;
    }

    /**
     * Returns true if class name matches format of abstract class names.
     *
     * @param className class name for check.
     * @return true if class name matches format of abstract class names.
     */
    private boolean isMatchingClassName(String className) {
        return format.matcher(className).find();
    }

}
