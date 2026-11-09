/*
JavadocLeadingAsteriskAlign
indentation = 2
violateExecutionOnNonTightHtml = (default)false


*/

// non-compiled with javac: Compilable with Java25

/**
  * Javadoc indented by two columns on a top-level field.
  */
int field = 0;

// violation 2 lines below 'Leading asterisk has .* indentation .* 1, expected is 2.'
/**
 * Javadoc aligned under the first asterisk on a top-level method.
  */
void method() {
}

void main() {
}

/**
  * Javadoc indented by two columns on a record.
  */
record Point(int x) {
    // violation 2 lines below 'Leading asterisk has .* indentation .* 1, expected is 2.'
    /**
     * Javadoc aligned under the first asterisk on a compact constructor.
      */
    Point {
    }
}
