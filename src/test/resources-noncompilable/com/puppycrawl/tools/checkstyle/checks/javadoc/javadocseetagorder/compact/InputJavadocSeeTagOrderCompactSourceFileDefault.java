/*
JavadocSeeTagOrder
violateExecutionOnNonTightHtml = (default)false


*/

// non-compiled with javac: Compilable with Java25

// violation 6 lines below """@see tag '#greet()' should be placed before
// 'java.util.List'."""
/**
 * Summary.
 *
 * @see java.util.List
 * @see #greet()
 */
String greet() {
    return "hi";
}

void main() {
    System.out.println(greet());
}
