/*
SuppressionXpathSingleFilter
files = InputSuppressionXpathSingleFilterNonMatchingColumnNumber
checks = TypeNameCheck
message = (default)(null)
id = (default)(null)
query = /COMPILATION_UNIT/CLASS_DEF[./IDENT \
        [@text='InputSuppressionXpathSingleFilterNonMatchingColumnNumber']]

com.puppycrawl.tools.checkstyle.checks.naming.TypeNameCheck
format = (default)^[A-Z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true
tokens = CLASS_DEF

*/
package com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter;

public class InputSuppressionXpathSingleFilterNonMatchingColumnNumber {

    class testClass { // violation
    }

    class anotherTestClass { // violation
    }
}
