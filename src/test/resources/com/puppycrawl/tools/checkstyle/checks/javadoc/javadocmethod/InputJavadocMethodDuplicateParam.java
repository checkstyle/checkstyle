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

public class InputJavadocMethodDuplicateParam {

    // violation 3 lines below 'Duplicate @param tag.'
    /**
     * @param value first description
     * @param value second description
     */
    void duplicateMethodParam(String value) {
    }

    // violation 4 lines below 'Duplicate @param tag.'
    /**
     * @param value first description
     * @param other other description
     * @param value second description
     */
    void duplicateMethodParamSeparated(String value, String other) {
    }

    // violation 3 lines below 'Duplicate @param tag.'
    /**
     * @param <T> first description
     * @param <T> second description
     * @param value value description
     */
    <T> void duplicateTypeParam(T value) {
    }

    // violation 3 lines below 'Unused @param tag for 'extra'.'
    // violation 3 lines below 'Duplicate @param tag.'
    /**
     * @param extra first extra description
     * @param extra second extra description
     */
    void duplicateUnusedParam() {
    }

    record Compact(String value) {
        // violation 3 lines below 'Duplicate @param tag.'
        /**
         * @param value first description
         * @param value second description
         */
        Compact {
        }
    }
}
