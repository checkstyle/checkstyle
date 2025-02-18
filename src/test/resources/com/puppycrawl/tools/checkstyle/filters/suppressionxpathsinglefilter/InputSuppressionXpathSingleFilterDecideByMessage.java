/*
SuppressionXpathSingleFilter
files = (null)
checks = (null)
message = Missing a Javadoc comment
id = (null)
query = (null)

com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocTypeCheck
scope = (default)public
excludeScope = (default)(null)
skipAnnotations = (default)Generated
tokens = CLASS_DEF

com.puppycrawl.tools.checkstyle.checks.coding.MagicNumberCheck
ignoreNumbers = (default)-1, 0, 1, 2
ignoreHashCodeMethod = (default)false
ignoreAnnotation = (default)false
ignoreFieldDeclaration = (default)false
ignoreAnnotationElementDefaults = (default)true
constantWaiverParentToken = (default)TYPECAST, METHOD_CALL, EXPR, ARRAY_INIT, UNARY_MINUS, \
                            UNARY_PLUS, ELIST, STAR, ASSIGN, PLUS, MINUS, DIV, LITERAL_NEW
tokens = (default)NUM_DOUBLE, NUM_FLOAT, NUM_INT, NUM_LONG

*/
package com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter;

public class InputSuppressionXpathSingleFilterDecideByMessage {
    // filtered violation above 'Missing a Javadoc comment.'
    private int countTokens() {
        double pi = 3.14; // violation
        return 123; // violation
    }

    public String getName() {
        int someVariable = 123; // violation
        return "InputSuppressByXpathThree";
    }
}
