/*
JavadocMethod
allowedAnnotations = Deprecated
validateThrows = true
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = true
violateExecutionOnNonTightHtml = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

// non-compiled with javac: Compilable with Java25

/** Throws without a tag. */
void fail() {
    throw new Error(); // ok until https://github.com/checkstyle/checkstyle/issues/20221
}

/** {@return the value} */
int inline() {
    return 0;
}

void main() {
}

/** Holder. */
class Holder {
    /** Throws without a tag. */
    void fail() {
        throw new Error(); // violation 'Expected @throws tag for 'Error'.'
    }

    /** {@return the value} */
    int inline() {
        return 0;
    }

    /** Missing param tag is allowed because of the annotation. */
    @Deprecated
    void annotated(int value) {
    }
}
