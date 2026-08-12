/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = true
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
violateExecutionOnNonTightHtml = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF

*/

// non-compiled with javac: Compilable with Java25

/**
 * Fails.
 */
void fail() throws Exception {
    // violation above 'Expected @throws tag for 'Exception'.'
    throw new Exception();
}

void main() { }
