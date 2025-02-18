/*
SuppressionXpathSingleFilter
files = (default)(null)
checks = (default)(null)
message = (default)(null)
id = (default)(null)
query = /COMPILATION_UNIT/CLASS_DEF[./IDENT \
        [@text='InputSuppressionXpathSingleFilterComplexQuery']]/OBJBLOCK/METHOD_DEF[./IDENT \
        [@text='countTokens']]/SLIST/VARIABLE_DEF[./IDENT[@text='pi']] \
        /ASSIGN/EXPR/NUM_FLOAT[@text='3.14']

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

public class InputSuppressionXpathSingleFilterComplexQuery {
    private int countTokens() {
        double pi = 3.14; // filtered violation ''3.14' is a magic number.'
        return 123; // violation
    }

    public String getName() {
        int someVariable = 123; // violation
        return "InputSuppressByXpathThree";
    }

    public int sum(int a, int b) {
        String someVariable = "Hello World";
        return a + b;
    }

}
