/*
JavadocContentLocation
location = FIRST_LINE


*/

// non-compiled with javac: Compilable with Java25

/** Summary on the first line.
 */
int field = 0;

// violation below 'Javadoc content should start from the same line as /\*\*.'
/**
 * Summary on the second line.
 */
void method() {
}

void main() {
}

/** Single line Javadoc. */
record Point(int x) {
    // violation below 'Javadoc content should start from the same line as /\*\*.'
    /**
     * Summary on the second line.
     */
    Point {
    }
}
