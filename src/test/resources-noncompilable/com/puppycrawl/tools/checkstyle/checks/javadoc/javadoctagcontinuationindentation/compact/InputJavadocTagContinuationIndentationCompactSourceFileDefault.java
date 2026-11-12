/*
JavadocTagContinuationIndentation
offset = (default)4
violateExecutionOnNonTightHtml = (default)false


*/

// non-compiled with javac: Compilable with Java25

// violation 6 lines below 'Line continuation have incorrect indentation level,
// expected level should be 4.'
/**
 * Summary.
 *
 * @param input the input that is long
 * and continues here.
 * @return the input
 */
String greet(String input) {
    return input;
}

void main() {
    System.out.println(greet("hi"));
}
