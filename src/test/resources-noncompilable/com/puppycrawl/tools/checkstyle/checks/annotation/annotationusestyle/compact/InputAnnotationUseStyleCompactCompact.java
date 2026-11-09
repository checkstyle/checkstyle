/*
AnnotationUseStyle
elementStyle = compact
closingParens = ignore
trailingArrayComma = ignore


*/

// non-compiled with javac: Compilable with Java25

@SuppressWarnings({"unused"})
int field1 = 0;

@SuppressWarnings("unused")
int field2 = 0;

@SuppressWarnings(value = "unused") // violation 'Annotation style must be 'COMPACT''
int field3 = 0;

@Deprecated()
void method1() {
}

@SuppressWarnings({"unused", "rawtypes",})
void method2() {
}

void main() {
}

@interface Tags {
    String[] value();
}

enum Color {
    @Tags(value = {"one"}) // violation 'Annotation style must be 'COMPACT''
    RED,
    @Tags({"one"})
    GREEN
}
