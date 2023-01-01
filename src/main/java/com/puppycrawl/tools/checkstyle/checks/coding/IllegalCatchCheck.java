///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;

/**
 * <p>
 * Checks that certain exception types do not appear in a {@code catch} statement.
 * </p>
 * <p>
 * Rationale: catching {@code java.lang.Exception}, {@code java.lang.Error} or
 * {@code java.lang.RuntimeException} is almost never acceptable.
 * Novice developers often simply catch Exception in an attempt to handle
 * multiple exception classes. This unfortunately leads to code that inadvertently
 * catches {@code NullPointerException}, {@code OutOfMemoryError}, etc.
 * </p>
 * <ul>
 * <li>
 * Property {@code illegalClassNames} - Specify exception class names to reject.
 * Type is {@code java.lang.String[]}.
 * Default value is {@code Error, Exception, RuntimeException, Throwable, java.lang.Error,
 * java.lang.Exception, java.lang.RuntimeException, java.lang.Throwable}.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;IllegalCatch&quot;/&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * try {
 *     // some code here
 * } catch (Exception e) { // violation
 *
 * }
 *
 * try {
 *     // some code here
 * } catch (ArithmeticException e) { // OK
 *
 * } catch (Exception e) { // violation, catching Exception is illegal
 *                           and order of catch blocks doesn't matter
 * }
 *
 * try {
 *     // some code here
 * } catch (ArithmeticException | Exception e) { // violation, catching Exception is illegal
 *
 * }
 *
 * try {
 *     // some code here
 * } catch (ArithmeticException e) { // OK
 *
 * }
 *
 * </pre>
 * <p>
 * To configure the check to override the default list
 * with ArithmeticException and OutOfMemoryError:
 * </p>
 * <pre>
 * &lt;module name=&quot;IllegalCatch&quot;&gt;
 *   &lt;property name=&quot;illegalClassNames&quot; value=&quot;ArithmeticException,
 *               OutOfMemoryError&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * try {
 *     // some code here
 * } catch (OutOfMemoryError e) { // violation
 *
 * }
 *
 * try {
 *     // some code here
 * } catch (ArithmeticException e) { // violation
 *
 * }
 *
 * try {
 *     // some code here
 * } catch (NullPointerException e) { // OK
 *
 * } catch (OutOfMemoryError e) { // violation
 *
 * }
 *
 * try {
 *     // some code here
 * } catch (ArithmeticException | Exception e) {  // violation
 *
 * }
 *
 * try {
 *     // some code here
 * } catch (Exception e) { // OK
 *
 * }
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code illegal.catch}
 * </li>
 * </ul>
 *
 * @since 3.2
 */
@StatelessCheck
public final class IllegalCatchCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "illegal.catch";

    /** Specify exception class names to reject. */
    private final Set<String> illegalClassNames = Arrays.stream(new String[] {"Exception", "Error",
        "RuntimeException", "Throwable", "java.lang.Error", "java.lang.Exception",
        "java.lang.RuntimeException", "java.lang.Throwable", }).collect(Collectors.toSet());

    /**
     * Setter to specify exception class names to reject.
     *
     * @param classNames
     *            array of illegal exception classes
     */
    public void setIllegalClassNames(final String... classNames) {
        illegalClassNames.clear();
        illegalClassNames.addAll(
                CheckUtil.parseClassNames(classNames));
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {TokenTypes.LITERAL_CATCH};
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public void visitToken(DetailAST detailAST) {
        final DetailAST parameterDef =
            detailAST.findFirstToken(TokenTypes.PARAMETER_DEF);
        final DetailAST excTypeParent =
                parameterDef.findFirstToken(TokenTypes.TYPE);

        DetailAST currentNode = excTypeParent.getFirstChild();
        while (currentNode != null) {
            final FullIdent ident = FullIdent.createFullIdent(currentNode);
            final String identText = ident.getText();
            if (illegalClassNames.contains(identText)) {
                log(detailAST, MSG_KEY, identText);
            }
            currentNode = currentNode.getNextSibling();
        }
    }
}
