/*
com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocVariableCheck
accessModifiers = (default)public,protected,package,private
considerEnclosingScope = (default)false
ignoreNamePattern = (default)null
tokens = (default)VARIABLE_DEF, ENUM_CONSTANT_DEF


*/

package com.puppycrawl.tools.checkstyle.utils.scopeutil;

/**
 * Input for compact constructor code block handling.
 *
 * @param value record value
 */
public record InputScopeUtilJavadocVariableCompactCtor(String value) {

    /**
     * Compact constructor.
     */
    public InputScopeUtilJavadocVariableCompactCtor {
        int localVariable = value.length();
    }
}
