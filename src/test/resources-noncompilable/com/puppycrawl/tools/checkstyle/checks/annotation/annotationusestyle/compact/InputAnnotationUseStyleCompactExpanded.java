/*
AnnotationUseStyle
elementStyle = expanded
closingParens = always
trailingArrayComma = always


*/

// non-compiled with javac: Compilable with Java25

@SuppressWarnings("unused") // violation 'Annotation style must be 'EXPANDED''
int field1 = 0;

@SuppressWarnings(value = "unused")
int field2 = 0;

@Deprecated // violation 'Annotation must have closing parenthesis.'
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
    @Tags({"one",}) // violation 'Annotation style must be 'EXPANDED''
    Point {
    }
}
