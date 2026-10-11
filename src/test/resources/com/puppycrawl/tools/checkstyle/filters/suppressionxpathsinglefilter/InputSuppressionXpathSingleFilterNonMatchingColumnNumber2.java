/*
SuppressionXpathSingleFilter
files = InputSuppressionXpathSingleFilterNonMatchingColumnNumber2
checks = MagicNumberCheck
message = (default)(null)
id = (default)(null)
query = //NUM_INT[@text='12']

com.puppycrawl.tools.checkstyle.checks.coding.MagicNumberCheck
ignoreNumbers = (default)-1, 0, 1, 2
ignoreHashCodeMethod = (default)false
ignoreAnnotation = (default)false
ignoreFieldDeclaration = (default)false
ignoreAnnotationElementDefaults = (default)true
constantWaiverParentToken = PLUS, EXPR, ASSIGN
tokens = NUM_INT

*/
package com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter;

public class InputSuppressionXpathSingleFilterNonMatchingColumnNumber2 {
    int x = 11 + 12; // violation ''11' is a magic number.'
    // filtered violation above ''12' is a magic number.'
}
