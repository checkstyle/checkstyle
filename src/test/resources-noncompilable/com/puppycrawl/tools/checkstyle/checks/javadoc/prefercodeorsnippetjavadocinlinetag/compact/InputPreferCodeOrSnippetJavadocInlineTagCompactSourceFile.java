/*
PreferCodeOrSnippetJavadocInlineTag
violateExecutionOnNonTightHtml = (default)false


*/

// non-compiled with javac: Compilable with Java25

// violation 2 lines below 'Use code or snippet inline tags instead of html tag.'
/**
 * <pre> This is a single line pre.</pre>
 */
void method1() {
}

// violation 2 lines below 'Use code or snippet inline tags instead of html tag.'
/**
 * <code> List&lt;String&gt; list = new ArrayList&lt;String&gt;(); </code>
 */
void method2() {
}

/**
 * {@code List<String> list = new ArrayList<>();}
 * {@snippet :
 *      List<String> list = new ArrayList<>();
 * }
 */
void method3() {
}


// violation 2 lines below 'Use snippet inline tag instead of html tag.'
/**
 * <pre>
 *      class Solution {
 *          System.out.println("Hello");
 *      }
 * </pre>
 */
void main() {
}
