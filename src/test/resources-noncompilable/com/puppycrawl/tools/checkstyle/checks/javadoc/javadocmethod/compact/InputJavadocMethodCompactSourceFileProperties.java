/*
JavadocMethod
allowInlineReturn = true
allowMissingParamTags = true
allowMissingReturnTag = true
validateThrows = true
accessModifiers = package
allowedAnnotations = Deprecated
violateExecutionOnNonTightHtml = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

// non-compiled with javac: Compilable with Java25

class Example {

    /**
     * Method with inline return.
     * {@return the answer}
     */
    int getAnswer() {
        return 42;
    }

    /**
     * Method with throws.
     *
     * @param x the number
     */
    // violation below 'Expected @throws tag for 'Exception'.'
    void check(int x) throws Exception {
        if (x < 0) {
            throw new Exception();
        }
    }

    /**
     * Method with allowed annotation Deprecated.
     */
    @Deprecated
    int oldMethod(int z) {
        return z;
    }

}

void main() throws Exception {
    final Example example = new Example();
    example.check(example.getAnswer());
    System.out.println(example.oldMethod(1));
}
