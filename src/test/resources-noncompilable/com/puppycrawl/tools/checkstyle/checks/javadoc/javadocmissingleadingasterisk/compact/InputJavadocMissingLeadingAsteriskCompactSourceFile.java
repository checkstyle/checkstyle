/*
JavadocMissingLeadingAsterisk
violateExecutionOnNonTightHtml = (default)false


*/

// non-compiled with javac: Compilable with Java25

// violation 2 lines below 'Javadoc line should start with leading asterisk'
/**
 Missing asterisk on a top-level field.
 */
int field = 0;

/**
 * Leading asterisk on a top-level method.
 */
void method() {
}

void main() {
}

// violation 3 lines below 'Javadoc line should start with leading asterisk'
/**
 * Nested class.
   Missing asterisk.
 */
class Nested {
    // violation 3 lines below 'Javadoc line should start with leading asterisk'
    /**
     *
       @return missing asterisk before a block tag
     */
    int nestedMethod() {
        return 0;
    }
}

/** Single line Javadoc on a record. */
record Point(int x) {
    // violation 3 lines below 'Javadoc line should start with leading asterisk'
    /**
     *
       Last line */
    Point {
    }
}
