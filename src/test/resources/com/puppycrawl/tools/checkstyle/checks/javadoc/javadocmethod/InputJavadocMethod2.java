/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = (default)false
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
violateExecutionOnNonTightHtml = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF, BLOCK_COMMENT_BEGIN

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethod2 {

    // violation 4 lines below 'Unused @param tag for '<''
    /**
     * Some explanation.
     *
     * @param < X >  A type param
     * @param <Y1> Another type param
     * @return a string
     */
    public <X, Y1> String doSomething() { // violation 'Expected @param tag for '<X>''
        return null;
    }
}
