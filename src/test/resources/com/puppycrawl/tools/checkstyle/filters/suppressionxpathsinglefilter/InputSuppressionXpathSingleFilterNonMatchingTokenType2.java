/*
SuppressionXpathSingleFilter
files = InputSuppressionXpathSingleFilterNonMatchingTokenType2
checks = RedundantModifierCheck
message = (default)(null)
id = (default)(null)
query = //METHOD_DEF[./IDENT[@text='bar']]

com.puppycrawl.tools.checkstyle.checks.modifier.RedundantModifierCheck
jdkVersion = (default)22
tokens = METHOD_DEF

*/
package com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter;

public interface InputSuppressionXpathSingleFilterNonMatchingTokenType2 {

    public void bar(); // violation 'Redundant 'public' modifier.'
}
