/*
NoLineWrap
tokens = IMPORT, METHOD_DEF
skipAnnotations = false

*/

// non-compiled with javac: Compilable with Java25

import java.util.List;

void main() {
}

@Deprecated // violation 'METHOD_DEF statement should not be line-wrapped.'
int oldSize(List<String> values) {
    return values.size();
}

int // violation 'METHOD_DEF statement should not be line-wrapped.'
    wrappedSize(List<String> values) {
    return values.size();
}

int size(List<String> values) { return values.size(); }
