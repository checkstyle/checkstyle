/*
JavadocType
scope = (default)private
excludeScope = (default)null
authorFormat = (default)null
versionFormat = (default)null
allowMissingParamTags = (default)false
allowUnknownTags = (default)false
allowedAnnotations = (default)Generated
violateExecutionOnNonTightHtml = (default)false
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

// non-compiled with javac: Compilable with Java25

import javax.annotation.processing.Generated;

/**
 * Method of the implicit class, it is not a type definition.
 *
 * @unknownTag value
 */
void method() {
}

// violation 4 lines below 'Unknown tag 'unknownTag'.'
/**
 * Unknown tag.
 *
 * @unknownTag value
 */
class UnknownTag {
}

// violation 4 lines below 'Unused @param tag for '<X>'.'
/**
 * Unused type parameter tag.
 *
 * @param <X> unused
 */
class MissingParamTag<T> { // violation 'missing @param '<T>' tag.'
}

// violation 4 lines below 'Unused Javadoc tag'
/**
 * Tag without a name.
 *
 * @param
 */
interface UnusedTag {
}

/**
 * Missing component tag.
 */
record MissingComponentTag(int value) { // violation 'missing @param 'value' tag.'
}

/** Outer type. */
enum Outer {
    FIRST;

    /** Private nested type. */
    private class Nested<T> { // violation 'missing @param '<T>' tag.'
    }
}

/** Skipped by the default of allowedAnnotations. */
@Generated("tool")
class Skipped<T> {
}

/** Annotation without violations. */
@interface Valid {
}

class WithoutJavadoc<T> {
}

void main() {
    /** Local type. */
    class Local<T> { // violation 'missing @param '<T>' tag.'
    }
}
