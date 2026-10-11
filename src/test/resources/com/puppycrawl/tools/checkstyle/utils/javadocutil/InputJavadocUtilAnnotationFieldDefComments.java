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

@interface InputJavadocUtilAnnotationFieldDefComments {

    /**annotationField*/
    String value(); // violation '@return tag should be present and have description'

    // violation 2 lines below '@return tag should be present and have description'
    /**qualifiedType*/
    java.lang.String qualifiedValue();

    String noJavadoc();

    /**dangling*/
    /**real*/
    int number(); // violation '@return tag should be present and have description'

}
