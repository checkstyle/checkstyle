/*
PreferLiteralJavadocInlineTag
violateExecutionOnNonTightHtml = (default)false


*/

// non-compiled with javac: Compilable with Java25

// 2 violations 4 lines below:
//  'Prefer Javadoc inline tag '{@literal <}' over '&lt;'.'
//  'Prefer Javadoc inline tag '{@literal >}' over '&gt;'.'
/**
 * &lt; represents the less than sign and &gt; represents the greater than sign.
 */
public void method() {
}

/**
 * <pre>
 *      &lt;&gt;
 * </pre>
 */
void method1() {
}

/**
 * {@code List&lt;String&gt;}
 * {@literal List&lt;String&gt;}
 */
void method2() {
}

void main() {
}
