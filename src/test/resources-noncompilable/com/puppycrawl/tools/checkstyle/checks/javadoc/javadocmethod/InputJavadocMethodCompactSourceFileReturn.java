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

/**
 * Calculates value.
 */
int value() {
    // violation above '@return tag should be present and have description.'
    return 1;
}

void main() { }
