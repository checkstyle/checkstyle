/*
InappropriateJavadocBlockTagsOnPackage
violateExecutionOnNonTightHtml = (default)false

*/

// non-compiled with javac: Compilable with Java25

// ok, a compact source file has no package declaration, and the check
// only validates the Javadoc of a package declaration.
/**
 * Javadoc of a method in a compact source file.
 * @return Something.
 * @throws Exception Something else.
 */
void main() throws Exception {
}
