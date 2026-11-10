/*
ImportOrder
option = (default)under
groups = (default)
ordered = (default)true
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
import java.io.File; // violation 'Wrong lexicographical order for 'java.io.File' import. Should be before 'java.util.List'.'
import static java.lang.Math.max;
import java.util.Map; // violation 'Import 'java.util.Map' violates the configured relative order between static and non-static imports.'

int counter = 0;

void main() {
    List<String> list = List.of();
    Map<String, File> map = Map.of();
    counter = max(list.size(), map.size());
}
