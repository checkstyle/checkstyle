/*
JavadocMethod
allowInlineReturn = (default)false
allowedAnnotations = (default)Override
validateThrows = (default)false
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
violateExecutionOnNonTightHtml = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodDuplicateThrows {
    // snippets from openjdk code

    /**
     * Sets the contents, or fails if the value was already set.
     *
     * @param contents value to set
     * @throws IllegalStateException if the contents was already set
     * @throws IllegalStateException if another operation recursively attempts to set it
     */
    void setOrThrow(Object contents) {
    }

    /**
     * Closes a resource arena.
     *
     * @throws IllegalStateException if the arena has already been closed
     * @throws IllegalStateException if an associated segment is being accessed concurrently
     * @throws WrongThreadException if called from a thread other than the owner thread
     * @throws UnsupportedOperationException if explicit close is not supported
     * @throws RuntimeException if a custom cleanup action fails
     */
    void closeArena() {
    }

    /**
     * @throws java.io.IOException first I/O failure case
     * @throws java.io.IOException second I/O failure case
     * @throws java.lang.NullPointerException null state failure
     * @throws java.io.IOException fourth I/O failure case
     */
    void repeatedIoFailure() throws java.io.IOException {
    }

    /**
     * Validates a format pattern.
     *
     * @param pattern pattern to validate
     * @throws IllegalArgumentException if {@code pattern} is invalid
     * @throws IllegalArgumentException if {@code pattern} is null
     */
    static void validateMessageFormatPattern(String pattern) {
    }
}
