/*
IllegalBlockTag
tag = (default)null
tagTextPattern = (default)^$
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, METHOD_DEF, \
CTOR_DEF, ENUM_CONSTANT_DEF, ANNOTATION_FIELD_DEF, RECORD_DEF, COMPACT_CTOR_DEF
violateExecutionOnNonTightHtml = (default)false


*/

// non-compiled with javac: Compilable with Java25

/**
 * Some javadoc.
 *
 * @todo something
 */
void method() {
}

void main() {
}

/**
 * Some javadoc.
 *
 * @since 1.1-beta
 */
class Nested {
    /**
     * Some javadoc.
     *
     * @deprecated use something else
     */
    @Deprecated
    void nestedMethod() {
    }
}
