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
