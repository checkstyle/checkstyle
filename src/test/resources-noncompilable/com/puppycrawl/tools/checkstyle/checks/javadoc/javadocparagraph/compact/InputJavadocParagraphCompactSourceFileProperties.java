/*
JavadocParagraph
allowNewlineParagraph = false
violateExecutionOnNonTightHtml = (default)false


*/

// non-compiled with javac: Compilable with Java25

// 2 violations 5 lines below:
//  '<p> tag should be placed immediately before the first word'
//  'tag should be preceded with an empty line.'
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
