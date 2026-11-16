/*
AnnotationUseStyle
elementStyle = expanded
closingParens = always
trailingArrayComma = always


*/

// non-compiled with javac: Compilable with Java25

// violation below 'Annotation style must be 'EXPANDED''
@SuppressWarnings("unused")
int field1 = 0;

@SuppressWarnings(value = "unused")
int field2 = 0;

// violation below 'Annotation must have closing parenthesis.'
@Deprecated
int field3 = 0;

@Deprecated()
void method1() {
}

// violation below 'Annotation array values must contain trailing comma.'
@SuppressWarnings(value = {"unused", "rawtypes"})
void method2() {
}

@SuppressWarnings(value = {"unused", "rawtypes",})
void method3() {
}

void main() {
}

@interface Tags {
    String[] value();
}

@Tags(value = {"one", "two",})
record Point(int x, int y) {
    // violation below 'Annotation style must be 'EXPANDED''
    @Tags({"one",})
    Point {
    }
}
