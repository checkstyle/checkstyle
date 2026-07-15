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

class DefaultConfig { // violation 'Blank line required after the opening brace of type definition.'
    int x;
} // violation 'Blank line required before the closing brace of type definition.'
