/*
JavadocLinkFirstOccurrence
violateExecutionOnNonTightHtml = (default)false


*/

// non-compiled with javac: Compilable with Java25

// violation 4 lines below "'java.util.List' should be linked only on its first
// occurrence in the Javadoc comment."
/**
 * First {@link java.util.List} mention.
 * Second {@link java.util.List} mention.
 *
 * @return the result
 */
String greet() {
    return "hi";
}

void main() {
    System.out.println(greet());
}
