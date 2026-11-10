/*
RequireEmptyLineBeforeBlockTagGroup
violateExecutionOnNonTightHtml = (default)false


*/

// non-compiled with javac: Compilable with Java25

// violation 4 lines below """Javadoc tag '@param' should be preceded with
// an empty line."""
/**
 * Summary text directly above the tag.
 * @param input the input
 * @return the input
 */
String greet(String input) {
    return input;
}

void main() {
    System.out.println(greet("hi"));
}
