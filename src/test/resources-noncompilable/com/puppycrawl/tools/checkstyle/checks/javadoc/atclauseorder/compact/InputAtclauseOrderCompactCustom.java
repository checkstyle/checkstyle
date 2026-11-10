/*
AtclauseOrder
violateExecutionOnNonTightHtml = (default)false
target = METHOD_DEF, COMPACT_CTOR_DEF
tagOrder = @since, @version, @param, @return, @throws, @exception, @deprecated, @see, \
           @serial, @serialField, @serialData, @author


*/

// non-compiled with javac: Compilable with Java25

/**
 * Some javadoc.
 *
 * @see Object
 * @since 1.0
 */
int field = 0;

// violation 5 lines below 'Block tags have to appear in the order .[@since.*'
/**
 * Some javadoc.
 *
 * @param value the value
 * @since 1.0
 * @return the value
 */
int method(int value) {
    return value;
}

/**
 * Some javadoc.
 *
 * @since 1.0
 * @param value the value
 * @return the value
 */
int other(int value) {
    return value;
}

void main() {
}

/**
 * Some javadoc.
 *
 * @author max
 * @version 1.0
 */
class Nested {
    // violation 5 lines below 'Block tags have to appear in the order .[@since.*'
    /**
     * Some javadoc.
     *
     * @see Object
     * @deprecated use something else
     */
    @Deprecated
    void nestedMethod() {
    }
}

/**
 * Some javadoc.
 *
 * @see Object
 * @param x the x
 */
record Point(int x) {
    // violation 5 lines below 'Block tags have to appear in the order .[@since.*'
    /**
     * Some javadoc.
     *
     * @throws IllegalArgumentException if invalid
     * @param x the x
     */
    Point {
    }
}
