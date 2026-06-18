/*
PreferJavadocInlineTags
violateExecutionOnNonTightHtml = (default)false
jdkVersion = (default)25


*/

// non-compiled with javac: Compilable with Java25

// violation 2 lines below 'Prefer Javadoc inline tag '{@link ...}' over '<a href="#...">'.'
/**
 * Refer to <a href="#validate()">validate</a>.
 */
public void method() {
}

// violation 2 lines below 'Prefer Javadoc inline tag '{@link ...}' over '<a href="#...">'.'
/**
 * Link with multiple attributes: <a id="foo" href="#bar">bar</a>.
 */
public void multipleAttributes() {
}

void main() {
    int local = 5;
    System.out.println(local);
}

// violation 4 lines below 'Prefer Javadoc inline tag '{@snippet ...}' over '<pre>'.'
/**
 * Content inside pre tags should not be flagged.
 *
 * <pre>
 * &lt;code&gt;example&lt;/code&gt;
 * &lt;T&gt; generic type
 * <a href="#method">link</a>
 * </pre>
 */
class Nested {
}
