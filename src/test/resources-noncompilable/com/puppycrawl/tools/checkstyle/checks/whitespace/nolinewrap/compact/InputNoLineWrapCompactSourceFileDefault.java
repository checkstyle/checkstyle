/*
NoLineWrap
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, MODULE_IMPORT, MODULE_DEF
skipAnnotations = (default)true

*/

// non-compiled with javac: Compilable with Java25

import module // violation 'import statement should not be line-wrapped.'
    java.sql;

import java.util.
    List; // violation above 'import statement should not be line-wrapped.'
import java.util.Map;

void main() {
}

int size(List<String> values, Map<String, String> pairs) {
    return values.size() + pairs.size();
}
