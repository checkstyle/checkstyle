/*
UnusedImports
processJavadoc = (default)true
violateExecutionOnNonTightHtml = (default)false


*/

// non-compiled with javac: Compilable with Java25

import java.util.List;
import java.util.Map; // violation 'Unused import - java.util.Map'
import java.util.Set;

void main() {
}

/**
 * Returns the size, see {@link Set}.
 *
 * @param values the values
 * @return the size
 */
int size(List<String> values) {
    return values.size();
}
