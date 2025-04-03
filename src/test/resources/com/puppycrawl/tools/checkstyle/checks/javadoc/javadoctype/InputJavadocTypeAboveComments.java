/*
JavadocType
authorFormat = Mohamed Mahfouz
allowUnknownTags = (default)false
scope = (default)private
excludeScope = (default)
versionFormat = (default)
tokens = (default)INTERFACE_DEF,CLASS_DEF,ENUM_DEF,ANNOTATION_DEF,RECORD_DEF
allowMissingParamTags = (default)false
allowedAnnotations = (default)Generated

*/


package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;

/** Test class for variable naming in for each clause. */
/*
   Input class for JavadocType
*/
public class InputJavadocTypeAboveComments {
    // violation above, 'Type Javadoc comment is missing @author tag'
}

/**
 * Test class for variable naming in for each clause.*
 * @author Mohamed Mahfouz
 */
/*
   Input class for JavadocType
*/
class MyClass {

}

/**
 * Test class for variable naming in for each clause.*
 * @author Mohamed Mahfouz
 */
class MyClass2 /* Comment */{

}

/**
 * Test class for variable naming in for each clause.*
 */
/* Comment */ class MyClass3 {
// violation above, 'Type Javadoc comment is missing @author tag'
}
