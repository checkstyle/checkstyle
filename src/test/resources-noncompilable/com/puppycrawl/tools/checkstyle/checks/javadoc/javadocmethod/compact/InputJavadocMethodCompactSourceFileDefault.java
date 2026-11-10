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

class Example {

    /**
     * Computes sum.
     */
    // violation 3 lines below '@return tag should be present and have description.'
    // violation 2 lines below 'Expected @param tag for 'a'.'
    // violation below 'Expected @param tag for 'b'.'
    int add(int a, int b) {
        return a + b;
    }

}

void main() {
    System.out.println(new Example().add(1, 2));
}
