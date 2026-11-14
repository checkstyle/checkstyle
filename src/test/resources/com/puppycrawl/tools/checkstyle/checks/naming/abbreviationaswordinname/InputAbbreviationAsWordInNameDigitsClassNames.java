/*
AbbreviationAsWordInName
allowedAbbreviationLength = (default)3
allowedAbbreviations = ABCDEFG,ABCDE12,ABCDE
ignoreFinal = (default)true
ignoreStatic = (default)true
ignoreStaticFinal = (default)true
ignoreOverriddenMethods = (default)true
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, PATTERN_VARIABLE_DEF, RECORD_DEF, \
         RECORD_COMPONENT_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

class InputAbbreviationAsWordInNameDigitsClassNames {
}

class ABCDEFGRunner {
}

class ABCDE12Runner {
}

class ABC12345Runner {
}

class ABCDE12 {
}

// violation below 'Abbreviation in name 'ABCDX12Runner''
class ABCDX12Runner {
}
