/*
JavadocMissingWhitespaceAfterAsterisk
violateExecutionOnNonTightHtml = (default)false


*/

// non-compiled with javac: Compilable with Java25

// violation below 'Missing a whitespace after the leading asterisk.'
/**Javadoc on a top-level field.
 */
int field = 0;

/**
 * Javadoc on a top-level method.
 */
void method() {
}

void main() {
}

// violation 2 lines below 'Missing a whitespace after the leading asterisk.'
/**
 *Javadoc on a nested class.
 */
class Nested {
    // violation 3 lines below 'Missing a whitespace after the leading asterisk.'
    /**
     * Javadoc.
     *<p>Javadoc on a nested method.
     */
    void nestedMethod() {
    }
}

/** Single line Javadoc on a record. */
record Point(int x) {
    // violation 2 lines below 'Missing a whitespace after the leading asterisk.'
    /**
     *@throws IllegalArgumentException never
     */
    Point {
    }
}
