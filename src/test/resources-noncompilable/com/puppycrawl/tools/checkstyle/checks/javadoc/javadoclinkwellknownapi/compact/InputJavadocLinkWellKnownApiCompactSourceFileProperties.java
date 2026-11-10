/*
JavadocLinkWellKnownApi
violateExecutionOnNonTightHtml = (default)false
wellKnownQualifiedPackages = java.util
wellKnownSimpleNames = Integer


*/

// non-compiled with javac: Compilable with Java25

// violation 3 lines below "'java.util.List' should not be linked because it
// belongs to a well-known package."
/**
 * Uses {@link java.util.List} here.
 *
 * @return the result
 */
String greet() {
    return "hi";
}

void main() {
    System.out.println(greet());
}
