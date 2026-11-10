/*
AtclauseOrder
violateExecutionOnNonTightHtml = (default)false
target = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, \
         CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF
tagOrder = (default)@author, @version, @param, @return, @throws, @exception, \
         @see, @since, @serial, @serialField, @serialData, @deprecated


*/

// non-compiled with javac: Compilable with Java25

// violation 5 lines below 'Block tags have to appear in the order .[@author.*'
/**
 * Some javadoc.
 *
 * @since 1.0
 * @see Object
 */
int field = 0;

// violation 5 lines below 'Block tags have to appear in the order .[@author.*'
/**
 * Some javadoc.
 *
 * @return the value
 * @param value the value
 * @throws IllegalStateException never
 */
int method(int value) {
    return value;
}

void main() {
}

// violation 5 lines below 'Block tags have to appear in the order .[@author.*'
/**
 * Some javadoc.
 *
 * @version 1.0
 * @author max
 */
class Nested {
    /**
     * Some javadoc.
     *
     * @param value the value
     * @return the value
     * @throws IllegalStateException never
     */
    int nestedMethod(int value) {
        return value;
    }
}

// violation 6 lines below 'Block tags have to appear in the order .[@author.*'
/**
 * Some javadoc.
 *
 * @param x the x
 * @deprecated use something else
 * @see Object
 */
@Deprecated
record Point(int x) {
    // violation 5 lines below 'Block tags have to appear in the order .[@author.*'
    /**
     * Some javadoc.
     *
     * @throws IllegalArgumentException if invalid
     * @param x the x
     */
    Point {
    }
}
