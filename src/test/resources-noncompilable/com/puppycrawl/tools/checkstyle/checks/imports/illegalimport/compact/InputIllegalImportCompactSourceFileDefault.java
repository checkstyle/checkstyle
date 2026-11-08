/*
IllegalImport
illegalPkgs = (default)sun
illegalClasses = (default)(null)
illegalModules = (default)(null)
regexp = (default)false


*/

// non-compiled with javac: Compilable with Java25

import module java.sql;

import java.util.List;
import sun.misc.Unsafe; // violation 'Illegal import - sun.misc.Unsafe'

void main() {
}

int size(List<String> values) {
    return values.size();
}

Class<?> unsafeType() {
    return Unsafe.class;
}
