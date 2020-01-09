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

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;

/**
 * <p>
 * Checks that specified types are not declared to be thrown.
 * Declaring that a method throws {@code java.lang.Error} or
 * {@code java.lang.RuntimeException} is almost never acceptable.
 * </p>
 * <ul>
 * <li>
 * Property {@code illegalClassNames} - Specify throw class names to reject.
 * Default value is {@code Error, RuntimeException, Throwable, java.lang.Error,
 * java.lang.RuntimeException, java.lang.Throwable}.
 * </li>
 * <li>
 * Property {@code ignoredMethodNames} - Specify names of methods to ignore.
 * Default value is {@code finalize}.
 * </li>
 * <li>
 * Property {@code ignoreOverriddenMethods} - allow to ignore checking overridden methods
 * (marked with {@code Override} or {@code java.lang.Override} annotation).
 * Default value is {@code true}.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name="IllegalThrows"/&gt;
 * </pre>
 * <p>
 * To configure the check rejecting throws NullPointerException from methods:
 * </p>
 * <pre>
 * &lt;module name="IllegalThrows"&gt;
 *   &lt;property name="illegalClassNames" value="NullPointerException"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * To configure the check ignoring method named "foo()":
 * </p>
 * <pre>
 * &lt;module name="IllegalThrows"&gt;
 *   &lt;property name="ignoredMethodNames" value="foo"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * To configure the check to warn on overridden methods:
 * </p>
 * <pre>
 * &lt;module name=&quot;IllegalThrows&quot;&gt;
 *   &lt;property name=&quot;ignoreOverriddenMethods&quot; value=&quot;false&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @since 4.0
 */
@StatelessCheck
public final class IllegalThrowsCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "illegal.throw";

    /** Specify names of methods to ignore. */
    private final Set<String> ignoredMethodNames =
        Arrays.stream(new String[] {"finalize", }).collect(Collectors.toSet());

    /** Specify throw class names to reject. */
    private final Set<String> illegalClassNames = Arrays.stream(
        new String[] {"Error", "RuntimeException", "Throwable", "java.lang.Error",
                      "java.lang.RuntimeException", "java.lang.Throwable", })
        .collect(Collectors.toSet());

    /**
     * Allow to ignore checking overridden methods (marked with {@code Override}
     * or {@code java.lang.Override} annotation).
     */
    private boolean ignoreOverriddenMethods = true;

    /**
     * Setter to specify throw class names to reject.
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
        return new int[] {TokenTypes.LITERAL_THROWS};
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public void visitToken(DetailAST detailAST) {
        final DetailAST methodDef = detailAST.getParent();
        // Check if the method with the given name should be ignored.
        if (!isIgnorableMethod(methodDef)) {
            DetailAST token = detailAST.getFirstChild();
            while (token != null) {
                final FullIdent ident = FullIdent.createFullIdent(token);
                if (illegalClassNames.contains(ident.getText())) {
                    log(token, MSG_KEY, ident.getText());
                }
                token = token.getNextSibling();
            }
        }
    }

    /**
     * Checks if current method is ignorable due to Check's properties.
     * @param methodDef {@link TokenTypes#METHOD_DEF METHOD_DEF}
     * @return true if method is ignorable.
     */
    private boolean isIgnorableMethod(DetailAST methodDef) {
        return shouldIgnoreMethod(methodDef.findFirstToken(TokenTypes.IDENT).getText())
            || ignoreOverriddenMethods
               && (AnnotationUtil.containsAnnotation(methodDef, "Override")
                  || AnnotationUtil.containsAnnotation(methodDef, "java.lang.Override"));
    }

    /**
     * Check if the method is specified in the ignore method list.
     * @param name the name to check
     * @return whether the method with the passed name should be ignored
     */
    private boolean shouldIgnoreMethod(String name) {
        return ignoredMethodNames.contains(name);
    }

    /**
     * Setter to specify names of methods to ignore.
     * @param methodNames array of ignored method names
     */
    public void setIgnoredMethodNames(String... methodNames) {
        ignoredMethodNames.clear();
        Collections.addAll(ignoredMethodNames, methodNames);
    }

    /**
     * Setter to allow to ignore checking overridden methods
     * (marked with {@code Override} or {@code java.lang.Override} annotation).
     * @param ignoreOverriddenMethods Check's property.
     */
    public void setIgnoreOverriddenMethods(boolean ignoreOverriddenMethods) {
        this.ignoreOverriddenMethods = ignoreOverriddenMethods;
    }

}
