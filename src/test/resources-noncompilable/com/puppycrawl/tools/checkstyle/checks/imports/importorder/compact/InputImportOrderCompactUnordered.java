/*
ImportOrder
option = above
groups = (default)
ordered = false
separated = (default)false
separatedStaticGroups = (default)false
caseSensitive = (default)true
staticGroups = (default)
sortStaticImportsAlphabetically = (default)false
useContainerOrderingForStatic = (default)false
tokens = (default)IMPORT,STATIC_IMPORT


*/

// non-compiled with javac: Compilable with Java25

import java.util.List;
import java.io.File;
import static java.lang.Math.max;

import java.awt.Color; // violation 'Extra separation in import group before 'java.awt.Color''

void main() {
    List<File> files = List.of();
    Color color = Color.RED;
    int size = max(files.size(), color.getRed());
}
