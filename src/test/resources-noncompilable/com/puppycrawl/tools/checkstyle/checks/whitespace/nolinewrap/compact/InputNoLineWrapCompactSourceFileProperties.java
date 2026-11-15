/*
NoLineWrap
tokens = IMPORT, METHOD_DEF
skipAnnotations = false

*/

// non-compiled with javac: Compilable with Java25

import java.util.List;

void main() {
}

// violation below 'METHOD_DEF statement should not be line-wrapped.'
@Deprecated
int oldSize(List<String> values) {
    return values.size();
}

int // violation 'METHOD_DEF statement should not be line-wrapped.'
    wrappedSize(List<String> values) {
    return values.size();
}

int size(List<String> values) { return values.size(); }
