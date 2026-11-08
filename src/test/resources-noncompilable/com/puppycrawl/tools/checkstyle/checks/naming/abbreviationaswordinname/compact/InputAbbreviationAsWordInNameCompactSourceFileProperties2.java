/*
AbbreviationAsWordInName
allowedAbbreviationLength = (default)3
allowedAbbreviations = (default)
ignoreFinal = false
ignoreStatic = false
ignoreStaticFinal = false
ignoreOverriddenMethods = false
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, PATTERN_VARIABLE_DEF, RECORD_DEF, \
         RECORD_COMPONENT_DEF


*/

// non-compiled with javac: Compilable with Java25

// violation below 'Abbreviation in name 'myHTTPSField''
final int myHTTPSField = 1;

// violation below 'Abbreviation in name 'myHTTPSOther''
static int myHTTPSOther = 2;

// violation below 'Abbreviation in name 'myHTTPSThird''
static final int myHTTPSThird = 3;

interface MyInterface {
    // violation below 'Abbreviation in name 'myHTTPSMethod''
    void myHTTPSMethod();
}

class MyClass implements MyInterface {
    // violation 2 lines below 'Abbreviation in name 'myHTTPSMethod''
    @Override
    public void myHTTPSMethod() {
    }
}

void main() {
}
