/*
JavadocMethod
validateThrows = (default)false
tokens = (default)METHOD_DEF,CTOR_DEF,ANNOTATION_FIELD_DEF,COMPACT_CTOR_DEF
allowInlineReturn = (default)false
accessModifiers = (default)public, protected, package, private
allowMissingReturnTag = (default)false
allowMissingParamTags = (default)false
allowedAnnotations = (default)Override

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

/**
 * @deprecated stuff
 */
public class InputJavadocMethod1 {
}

/**
 * @deprecated stuff
 */
@interface Bleh {

    /**
     * @deprecated stuff
     */
    int method(); // violation '@return tag should be present and have description'
}
