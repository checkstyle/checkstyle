/*
JavadocType
scope = anoninner
excludeScope = package
authorFormat = \\S
versionFormat = \\$Revision.*\\$
allowMissingParamTags = true
allowUnknownTags = true
allowedAnnotations = Allowed
violateExecutionOnNonTightHtml = (default)false
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

// non-compiled with javac: Compilable with Java25

/**
 * The implicit class has package scope, so its public member is excluded too.
 *
 * @param <X> unused
 */
public class PublicType<T> {
}

/**
 * Excluded by excludeScope.
 *
 * @param <X> unused
 */
class PackageType<T> {
}

/**
 * Tag formats apply to outermost types only, and this type is in the implicit class.
 *
 * @author
 * @version 1.0
 * @unknownTag value
 */
private class Formats<T> {
}

// violation 4 lines below 'Unused @param tag for '<X>'.'
/**
 * Unused tags are reported even if missing tags are allowed.
 *
 * @param <X> unused
 */
private class PrivateType<T> {
}

// violation 4 lines below 'Unused @param tag for 'unused'.'
/**
 * Record component tag.
 *
 * @param unused unused
 */
private record PrivateRecord(int value) {
}

/**
 * Skipped by allowedAnnotations.
 *
 * @param <X> unused
 */
@Allowed
private class Skipped<T> {
}

@interface Allowed {
}

Object method() {
    return new Object() {
        // violation 4 lines below 'Unused @param tag for '<X>'.'
        /**
         * Anonymous inner scope is in the scope.
         *
         * @param <X> unused
         */
        class AnonymousMember<T> {
        }
    };
}

void main() {
}
