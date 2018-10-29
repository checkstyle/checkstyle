////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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
 * Throwing java.lang.Error or java.lang.RuntimeException
 * is almost never acceptable.
 * </p>
 * Check has following properties:
 * <p>
 * <b>illegalClassNames</b> - throw class names to reject.
 * </p>
 * <p>
 * <b>ignoredMethodNames</b> - names of methods to ignore.
 * </p>
 * <p>
 * <b>ignoreOverriddenMethods</b> - ignore checking overridden methods (marked with Override
 *  or java.lang.Override annotation) default value is <b>true</b>.
 * </p>
 *
 */
@StatelessCheck
public final class IllegalThrowsCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "illegal.throw";

    /** Methods which should be ignored. */
    private final Set<String> ignoredMethodNames =
        Arrays.stream(new String[] {"finalize", }).collect(Collectors.toSet());

    /** Illegal class names. */
    private final Set<String> illegalClassNames = Arrays.stream(
        new String[] {"Error", "RuntimeException", "Throwable", "java.lang.Error",
                      "java.lang.RuntimeException", "java.lang.Throwable", })
        .collect(Collectors.toSet());

    /** Property for ignoring overridden methods. */
    private boolean ignoreOverriddenMethods = true;

    /**
     * Set the list of illegal classes.
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
                if (token.getType() != TokenTypes.COMMA) {
                    final FullIdent ident = FullIdent.createFullIdent(token);
                    if (illegalClassNames.contains(ident.getText())) {
                        log(token, MSG_KEY, ident.getText());
                    }
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
     * Set the list of ignore method names.
     * @param methodNames array of ignored method names
     */
    public void setIgnoredMethodNames(String... methodNames) {
        ignoredMethodNames.clear();
        Collections.addAll(ignoredMethodNames, methodNames);
    }

    /**
     * Sets <b>ignoreOverriddenMethods</b> property value.
     * @param ignoreOverriddenMethods Check's property.
     */
    public void setIgnoreOverriddenMethods(boolean ignoreOverriddenMethods) {
        this.ignoreOverriddenMethods = ignoreOverriddenMethods;
    }

}
