/*
IllegalBlockTag
tag = @since
tagTextPattern = ^[1-9\\.]+$
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, METHOD_DEF, \
CTOR_DEF, ENUM_CONSTANT_DEF, ANNOTATION_FIELD_DEF, RECORD_DEF, COMPACT_CTOR_DEF
violateExecutionOnNonTightHtml = (default)false


*/

// non-compiled with javac: Compilable with Java25

// 'since' does not match the pattern, ok until
// https://github.com/checkstyle/checkstyle/issues/22573
/**
 * Some javadoc.
 *
 * @since 1.1-beta
 */
void method() {
}

/**
 * Some javadoc.
 *
 * @since 1.1
 */
int other() {
    return 0;
}

// violation 4 lines below 'Block tag 'since' is matched illegal pattern'
/**
 * Some javadoc.
 *
 * @since 1.1-beta
 */
void third() {
}

void main() {
}

/**
 * Some javadoc.
 *
 * @since 2.1
 */
class Nested {
    // violation 4 lines below 'Block tag 'since' is matched illegal pattern'
    /**
     * Some javadoc.
     *
     * @since next
     */
    void nestedMethod() {
    }
}

// violation 4 lines below 'Block tag 'since' is matched illegal pattern'
/**
 * Some javadoc.
 *
 * @since 1.x
 */
record Point(int x) {
    // violation 4 lines below 'Block tag 'since' is matched illegal pattern'
    /**
     * Some javadoc.
     *
     * @since soon
     */
    Point {
    }
}

enum Color {
    // violation 4 lines below 'Block tag 'since' is matched illegal pattern'
    /**
     * Some javadoc.
     *
     * @since later
     */
    RED
}
