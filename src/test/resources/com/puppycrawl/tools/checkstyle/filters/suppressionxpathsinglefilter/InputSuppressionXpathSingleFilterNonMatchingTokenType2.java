/*
SuppressionXpathSingleFilter
files = InputSuppressionXpathSingleFilterNonMatchingTokenType2
checks = RedundantModifierCheck
message = (default)(null)
id = (default)(null)
query = //METHOD_DEF[./IDENT[@text='bar']]

com.puppycrawl.tools.checkstyle.checks.modifier.RedundantModifierCheck
jdkVersion = (default)22
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
        CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE, ANNOTATION_DEF, RECORD_DEF, \
        PATTERN_VARIABLE_DEF, LITERAL_CATCH, LAMBDA

*/
package com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter;

public interface InputSuppressionXpathSingleFilterNonMatchingTokenType2 {

    public void bar(); // violation 'Redundant 'public' modifier.'
}
