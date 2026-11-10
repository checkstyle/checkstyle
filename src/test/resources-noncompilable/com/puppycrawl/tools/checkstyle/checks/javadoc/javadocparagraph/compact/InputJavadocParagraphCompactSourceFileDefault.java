/*
JavadocParagraph
allowNewlineParagraph = (default)true
violateExecutionOnNonTightHtml = (default)false


*/

// non-compiled with javac: Compilable with Java25

// violation 3 lines below '<p> tag should be preceded with an empty line.'
/**
 * First paragraph.
 * <p>
 * Second paragraph.
 *
 * @return greeting
 */
String greet() {
    return "hi";
}

void main() {
    System.out.println(greet());
}
