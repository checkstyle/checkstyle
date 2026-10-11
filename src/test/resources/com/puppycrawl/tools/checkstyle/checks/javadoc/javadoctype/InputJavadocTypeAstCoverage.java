/*
JavadocType
scope = (default)private
excludeScope = (default)null
authorFormat = (default)null
versionFormat = .+
allowMissingParamTags = (default)false
allowUnknownTags = (default)false
allowedAnnotations = (default)Generated
violateExecutionOnNonTightHtml = (default)false
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;

/**
 * Missing version.
 */
class InputJavadocTypeAstCoverage { // violation 'missing @version tag.'
}

/**
 * Versioned generic type.
 *
 * @param <T> the type
 * @version 1.0
 */
class InputJavadocTypeAstCoverageGeneric<T> {
}

/**
 * Versioned outer type.
 *
 * @version 1.0
 */
class InputJavadocTypeAstCoverageOuter {

    /**
     * Inner type has no version tag.
     */
    class Inner {
    }
}
