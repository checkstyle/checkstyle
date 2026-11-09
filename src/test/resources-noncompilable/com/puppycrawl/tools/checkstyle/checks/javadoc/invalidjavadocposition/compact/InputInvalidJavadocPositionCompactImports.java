/*
InvalidJavadocPosition


*/

// non-compiled with javac: Compilable with Java25

// violation below 'Javadoc .* placed in the wrong location.'
/** invalid - before an import */
import java.util.List;

/** valid - after the imports, on the first top-level field */
List<String> names = List.of();

void main() {
}

// violation below 'Javadoc .* placed in the wrong location.'
/** invalid - before a stray semicolon */
;

/** valid - on a top-level record */
record Point(int x) {
}

// violation below 'Javadoc .* placed in the wrong location.'
/** invalid - at the end of the file */
