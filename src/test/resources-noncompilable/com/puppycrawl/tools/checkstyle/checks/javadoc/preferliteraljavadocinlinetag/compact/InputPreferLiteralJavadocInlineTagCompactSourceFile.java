/*
PreferLiteralJavadocInlineTag
violateExecutionOnNonTightHtml = (default)false


*/

// non-compiled with javac: Compilable with Java25

// 5 violations 7 lines below:
//  'Prefer literal or code javadoc inline tag over '&lt;'.'
//  'Prefer literal or code javadoc inline tag over '&gt;'.'
//  'Prefer literal or code javadoc inline tag over '&amp;'.'
//  'Prefer literal or code javadoc inline tag over '&quot;'.'
//  'Prefer literal or code javadoc inline tag over '&apos;'.'
/**
 * Entities are : &lt; &gt; &amp; &quot; &apos;
 */
public void method() {
}

/**
 * <pre>
 *      &lt;&gt;&apos;&quot;&amp;
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
