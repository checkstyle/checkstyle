/*
JavadocMethod
accessModifiers = (default)public, protected, package, private
allowInlineReturn = (default)false
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowedAnnotations = (default)Override
validateThrows = (default)false
violateExecutionOnNonTightHtml = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

// non-compiled with javac: Compilable with Java25

class Inner {

    /**
     * Proper Javadoc.
     *
     * @param x the number
     * @return the result
     */
    public int method1(int x) {
        return x;
    }

    // violation 6 lines below '@return tag should be present and have description.'
    /**
     * Missing return tag.
     *
     * @param x the number
     */
    public int method2(int x) {
        return x;
    }

    // violation 6 lines below 'Expected @param tag for 'x'.'
    /**
     * Missing param tag.
     *
     * @return the result
     */
    public int method3(int x) {
        return x;
    }

    // violation 4 lines below 'Unused @param tag for 'y'.'
    /**
     * Unused param tag.
     *
     * @param y the unused param
     */
    public void method4() {
    }
}

void main() {
}
