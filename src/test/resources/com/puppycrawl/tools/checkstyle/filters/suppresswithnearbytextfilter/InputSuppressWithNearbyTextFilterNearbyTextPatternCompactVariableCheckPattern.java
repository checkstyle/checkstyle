/*
SuppressWithNearbyTextFilter
nearbyTextPattern = -@cs\\[(\\w+)\\] (\\w+)
checkPattern = $1
messagePattern = (default)(null)
idPattern = (default)(null)
lineRange = (default)0

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

package com.puppycrawl.tools.checkstyle.filters.suppresswithnearbytextfilter;

public class InputSuppressWithNearbyTextFilterNearbyTextPatternCompactVariableCheckPattern {
    // filtered violation below ''42' is a magic number.'
    int a = 42; // -@cs[MagicNumber] We do not consider this number as magic for some reason.
    int b = 43; // violation ''43' is a magic number.'
}
