/*
AnnotationUseStyle
elementStyle = (default)compact_no_array
closingParens = (default)never
trailingArrayComma = (default)never


*/

// non-compiled with javac: Compilable with Java25

@SuppressWarnings({"unused"}) // violation 'Annotation style must be 'COMPACT_NO_ARRAY''
int field1 = 0;

@SuppressWarnings("unused")
int field2 = 0;

@SuppressWarnings(value = "unused")
int field3 = 0;

@Deprecated() // violation 'Annotation cannot have closing parenthesis.'
void method1() {
}

// violation below 'Annotation array values cannot contain trailing comma.'
@SuppressWarnings({"unused", "rawtypes",})
void method2() {
}

void main() {
}

@interface Tags {
    String[] value();
}

@Tags({"one", "two"})
class Nested {
    @Tags("one")
    int nestedField = 0;

    // 2 violations 3 lines below:
    //    'Annotation style must be 'COMPACT_NO_ARRAY''
    //    'Annotation array values cannot contain trailing comma.'
    @Tags({"one",})
    void nestedMethod() {
    }
}
