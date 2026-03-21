/*
SuppressionXpathSingleFilter
files = InputSuppressionXpathSingleFilterNonMatchingLineNumber
checks = MissingJavadocTypeCheck
message = (default)(null)
id = (default)(null)
query = /COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='TestClass']]

com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocTypeCheck
scope = package
excludeScope = (default)(null)
skipAnnotations = (default)Generated
tokens = CLASS_DEF

*/
package com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter;

public class InputSuppressionXpathSingleFilterNonMatchingLineNumber { // violation
}

class TestClass { // filtered violation 'Missing a Javadoc comment.'
}
