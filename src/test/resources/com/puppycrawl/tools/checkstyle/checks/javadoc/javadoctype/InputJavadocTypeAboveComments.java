/*
JavadocType
authorFormat = Mohamed Mahfouz


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
