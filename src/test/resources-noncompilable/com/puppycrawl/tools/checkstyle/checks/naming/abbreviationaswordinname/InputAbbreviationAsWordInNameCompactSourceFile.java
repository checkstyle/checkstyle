/*
AbbreviationAsWordInName
allowedAbbreviationLength = (default)3
allowedAbbreviations = (default)
ignoreFinal = (default)true
ignoreStatic = (default)true
ignoreStaticFinal = (default)true
ignoreOverriddenMethods = (default)true
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, PATTERN_VARIABLE_DEF, RECORD_DEF, \
         RECORD_COMPONENT_DEF


*/

// non-compiled with javac: Compilable with Java25

// violation below 'Abbreviation in name 'useHTTPSConnection''
int useHTTPSConnection = 1;

static final int anotherHTTPSConnection = 2;

void main() {
    // violation below 'Abbreviation in name 'localHTTPSConnection''
    int localHTTPSConnection = 3;
}
