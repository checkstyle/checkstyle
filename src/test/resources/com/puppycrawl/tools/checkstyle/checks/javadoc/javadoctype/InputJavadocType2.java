/*
JavadocType
scope = (default)private
excludeScope = public
authorFormat = \S
allowMissingParamTags = (default)false
allowUnknownTags = (default)false
allowedAnnotations = (default)Generated
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;

public class InputJavadocType2 {
}

/** Test class for variable naming in for each clause. */
class InputSimple2 { // violation 'Type Javadoc comment is missing @author tag'

    /** Some more Javadoc. */
    public void doSomething() {
    }
}
