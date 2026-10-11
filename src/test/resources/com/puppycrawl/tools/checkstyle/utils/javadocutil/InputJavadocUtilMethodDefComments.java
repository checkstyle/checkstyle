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

package com.puppycrawl.tools.checkstyle.utils.javadocutil;

class InputJavadocUtilMethodDefComments {

    /**modifier*/
    public int modifierPath() { // violation '@return tag should be present and have description'
        return 1;
    }

    /**annotation*/
    @Deprecated
    void annotationPath(int value) { // violation 'Expected @param tag for 'value''
    }

    // 3 violations 5 lines below:
    //      '@return tag should be present and have description'
    //      'Expected @param tag for '<T>''
    //      'Expected @param tag for 'value''
    /**typeParams*/
    <T> T typeParameterPath(T value) {
        return value;
    }

    // violation 2 lines below '@return tag should be present and have description'
    /**qualifiedType*/
    java.lang.String/**nope*/ qualifiedReturnType/**nope*/()/**nope*/ {
        /**local class javadoc*/
        class Local {
        }
        return null;
    }

    public int bodyCommentOnly() {
        /**local class javadoc*/
        class Local {
        }
        return 0;
    }

    /**
     * Dangling Javadoc.
     *
     * @return ignored
     */
    /**real*/
    int danglingReal() { // violation '@return tag should be present and have description'
        return 1;
    }
}
