////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.imports;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <p>
 * Checks that there are no static import statements.
 * </p>
 * <p>
 * Rationale: Importing static members can lead to naming conflicts
 * between class' members. It may lead to poor code readability since it
 * may no longer be clear what class a member resides in (without looking
 * at the import statement).
 * </p>
 * <p>
 * If you exclude a starred import on a class this automatically excludes
 * each member individually.
 * </p>
 * <p>
 * For example: Excluding {@code java.lang.Math.*}. will allow the import
 * of each static member in the Math class individually like
 * {@code java.lang.Math.PI, java.lang.Math.cos, ...}.
 * </p>
 * <ul>
 * <li>
 * Property {@code excludes} - Control whether to allow for certain classes via
 * a star notation to be excluded such as {@code java.lang.Math.*} or specific
 * static members to be excluded like {@code java.lang.System.out} for a variable
 * or {@code java.lang.Math.random} for a method. See notes section for details.
 * Type is {@code java.lang.String[]}.
 * Default value is {@code ""}.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name="AvoidStaticImport"/&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * import static java.lang.Math.pow;          // violation
 * import static java.lang.System.*;          // violation
 * import java.io.File;                       // OK
 * import java.util.*;                        // OK
 * </pre>
 * <p>
 * To configure the check so that the {@code java.lang.System.out} member and all
 * members from {@code java.lang.Math} are allowed:
 * </p>
 * <pre>
 * &lt;module name="AvoidStaticImport"&gt;
 *   &lt;property name="excludes" value="java.lang.System.out,java.lang.Math.*"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * import static java.lang.Math.*;            // OK
 * import static java.lang.System.out;        // OK
 * import static java.lang.Integer.parseInt;  // violation
 * import java.io.*;                          // OK
 * import java.util.*;                        // OK
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code import.avoidStatic}
 * </li>
 * </ul>
 *
 * @since 5.0
 */
@StatelessCheck
public class AvoidStaticImportCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "import.avoidStatic";

    /**
     * Control whether to allow for certain classes via a star notation to be
     * excluded such as {@code java.lang.Math.*} or specific static members
     * to be excluded like {@code java.lang.System.out} for a variable or
     * {@code java.lang.Math.random} for a method. See notes section for details.
     */
    private String[] excludes = CommonUtil.EMPTY_STRING_ARRAY;

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
        return new int[] {TokenTypes.STATIC_IMPORT};
    }

    /**
     * Setter to control whether to allow for certain classes via a star notation
     * to be excluded such as {@code java.lang.Math.*} or specific static members
     * to be excluded like {@code java.lang.System.out} for a variable or
     * {@code java.lang.Math.random} for a method. See notes section for details.
     *
     * @param excludes a list of fully-qualified class names/specific
     *     static members where static imports are ok
     */
    public void setExcludes(String... excludes) {
        this.excludes = excludes.clone();
    }

    @Override
    public void visitToken(final DetailAST ast) {
        final DetailAST startingDot =
            ast.getFirstChild().getNextSibling();
        final FullIdent name = FullIdent.createFullIdent(startingDot);

        if (!isExempt(name.getText())) {
            log(startingDot, MSG_KEY, name.getText());
        }
    }

    /**
     * Checks if a class or static member is exempt from known excludes.
     *
     * @param classOrStaticMember
     *                the class or static member
     * @return true if except false if not
     */
    private boolean isExempt(String classOrStaticMember) {
        boolean exempt = false;

        for (String exclude : excludes) {
            if (classOrStaticMember.equals(exclude)
                    || isStarImportOfPackage(classOrStaticMember, exclude)) {
                exempt = true;
                break;
            }
        }
        return exempt;
    }

    /**
     * Returns true if classOrStaticMember is a starred name of package,
     *  not just member name.
     *
     * @param classOrStaticMember - full name of member
     * @param exclude - current exclusion
     * @return true if member in exclusion list
     */
    private static boolean isStarImportOfPackage(String classOrStaticMember, String exclude) {
        boolean result = false;
        if (exclude.endsWith(".*")) {
            // this section allows explicit imports
            // to be exempt when configured using
            // a starred import
            final String excludeMinusDotStar =
                exclude.substring(0, exclude.length() - 2);
            if (classOrStaticMember.startsWith(excludeMinusDotStar)
                    && !classOrStaticMember.equals(excludeMinusDotStar)) {
                final String member = classOrStaticMember.substring(
                        excludeMinusDotStar.length() + 1);
                // if it contains a dot then it is not a member but a package
                if (member.indexOf('.') == -1) {
                    result = true;
                }
            }
        }
        return result;
    }

}
