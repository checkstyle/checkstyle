/*
InvalidJavadocPosition


*/

// non-compiled with javac: Compilable with Java25

/** invalid - before an import */ // violation 'Javadoc .* placed in the wrong location.'
import java.util.List;

/** valid - after the imports, on the first top-level field */
List<String> names = List.of();

void main() {
}

/** invalid - before a stray semicolon */ // violation 'Javadoc .* placed in the wrong location.'
;

/** valid - on a top-level record */
record Point(int x) {
}

/** invalid - at the end of the file */ // violation 'Javadoc .* placed in the wrong location.'
