/*
TypeBodyPadding
atStartOfBody = (default)true
atEndOfBody = (default)true
allowEmpty = (default)true
skipInner = (default)true
skipLocal = (default)true
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, RECORD_DEF, ANNOTATION_DEF


*/

// non-compiled with javac: Compilable with Java25

void main() {
}

// Skipped with default skipInner = true as a member of the implicit class.
class DefaultConfig {
    int x;
}
