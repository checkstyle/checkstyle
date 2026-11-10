/*
JavadocLinkWellKnownApi
violateExecutionOnNonTightHtml = (default)false
wellKnownQualifiedPackages = (default)java.lang
wellKnownSimpleNames = (default)String


*/

// non-compiled with javac: Compilable with Java25

// violation 3 lines below "'java.lang.String' should not be linked because it
// belongs to a well-known package."
/**
 * Uses {@link java.lang.String} here.
 *
 * @return the result
 */
String greet() {
    return "hi";
}

void main() {
    System.out.println(greet());
}
