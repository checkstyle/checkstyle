/*
MissingDeprecated
violateExecutionOnNonTightHtml = (default)false


*/

// non-compiled with javac: Compilable with Java25

/**
 * Javadoc tag without the annotation on the first top-level method.
 *
 * @deprecated use something else
 */
void first() { // violation 'Must include both @java.lang.Deprecated annotation .*'
}

/**
 * Both the tag and the annotation.
 *
 * @deprecated use something else
 */
@Deprecated
int field = 0;

/**
 * Annotation without the Javadoc tag.
 */
@Deprecated // violation 'Must include both @java.lang.Deprecated annotation .*'
void method() {
}

void main() {
}

// violation 5 lines below 'Duplicate @deprecated tag.'
/**
 * Duplicate tag on a nested class.
 *
 * @deprecated use something else
 * @deprecated twice
 */
@Deprecated
class Nested {
    /**
     * Javadoc tag without the annotation.
     *
     * @deprecated use something else
     */
    void nestedMethod() { // violation 'Must include both @java.lang.Deprecated annotation .*'
    }
}

/**
 * Javadoc tag without the annotation on a record.
 *
 * @deprecated use something else
 */
record Point(int x) { // violation 'Must include both @java.lang.Deprecated annotation .*'
}
