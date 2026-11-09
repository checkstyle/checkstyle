/*
ImportControl
file = (file)(resource)InputImportControlCompact.xml
path = .*Compact.*


*/

// non-compiled with javac: Compilable with Java25

// A compact source file has no package declaration, so it is never matched
// against the import control file and its imports are not checked, the same
// as an ordinary file in the unnamed package.
import module java.sql;

import java.io.File;
import java.util.List;

void main() {
}

boolean exists(List<File> files) {
    return files.isEmpty();
}
