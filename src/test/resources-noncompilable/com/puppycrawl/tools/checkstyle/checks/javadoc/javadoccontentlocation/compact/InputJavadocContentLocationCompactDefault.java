/*
JavadocContentLocation
location = (default)SECOND_LINE


*/

// non-compiled with javac: Compilable with Java25

// violation below 'Javadoc content should start from the next line after /\*\*.'
/** Summary on the first line.
 */
int field = 0;

/**
 * Summary on the second line.
 */
void method() {
}

void main() {
}

/** Single line Javadoc. */
class Nested {
    // violation below 'Javadoc content should start from the next line after /\*\*.'
    /** Summary on the first line.
     */
    void nestedMethod() {
    }
}
