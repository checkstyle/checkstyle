/*
AnnotationOnSameLine
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, \
         RECORD_DEF, COMPACT_CTOR_DEF


*/

// non-compiled with javac: Compilable with Java25

// comment over the field, annotation is on the same line as the field
@Deprecated int fieldOnSameLine = 0;

// comment over the field, annotation is not on the same line as the field
// violation below "Annotation 'Deprecated' should be on the same line with its target."
@Deprecated
int fieldOnPreviousLine = 0;

@Deprecated void methodOnSameLine() {
}

// violation below "Annotation 'SuppressWarnings' should be on the same line with its target."
@SuppressWarnings("unused")
void methodOnPreviousLine() {
}

class Nested {
    // violation below "Annotation 'Deprecated' should be on the same line with its target."
    @Deprecated
    void nestedMethod() {
    }
}

void main() {
}
