/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = (default)false
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
violateExecutionOnNonTightHtml = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

// non-compiled with javac: Compilable with Java25

/** Missing param tag. */
void params(int value) { // ok until https://github.com/checkstyle/checkstyle/issues/20221
}

/** Missing return tag. */
int result() { // ok until https://github.com/checkstyle/checkstyle/issues/20221
    return 0;
}

// ok below until https://github.com/checkstyle/checkstyle/issues/20221
/** @param other not a parameter */
void unused() {
}

void main() {
}

/** Holder. */
class Holder {
    /** Missing param tag. */
    void params(int value) { // violation 'Expected @param tag for 'value'.'
    }

    /** Missing return tag. */
    int result() { // violation '@return tag should be present and have description.'
        return 0;
    }

    // violation below 'Unused @param tag for 'other'.'
    /** @param other not a parameter */
    void unused() {
    }

    /** Constructor. */
    Holder(int size) { // violation 'Expected @param tag for 'size'.'
    }
}

/**
 * Point.
 *
 * @param x coordinate
 */
record Point(int x) {
    /** Compact constructor. */
    Point { // ok until https://github.com/checkstyle/checkstyle/issues/21697
    }
}
