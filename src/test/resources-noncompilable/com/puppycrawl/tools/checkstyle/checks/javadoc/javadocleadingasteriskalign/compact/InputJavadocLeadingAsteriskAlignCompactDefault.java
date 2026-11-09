/*
JavadocLeadingAsteriskAlign
indentation = (default)1
violateExecutionOnNonTightHtml = (default)false


*/

// non-compiled with javac: Compilable with Java25

/**
 * Javadoc aligned on a top-level field.
 */
int field = 0;

// violation 2 lines below 'Leading asterisk has .* indentation .* 0, expected is 1.'
/**
* Javadoc not aligned on a top-level method.
 */
void method() {
}

void main() {
}

/**
 * Javadoc aligned on a nested class.
 */
class Nested {
    // violation 3 lines below 'Leading asterisk has .* indentation .* 2, expected is 1.'
    /**
     * Javadoc aligned.
      */
    void nestedMethod() {
    }
}
