/*
com.puppycrawl.tools.checkstyle.checks.javadoc.WriteTagCheck
tag = @author
tagFormat = \\S
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.utils.javadocutil;

/**classComment*/
class InputJavadocUtilTypeDefComments { // violation 'Javadoc comment is missing @author tag.'
}

// violation 2 lines below 'Javadoc comment is missing @author tag.'
/**annotationClass*/
@Deprecated
class ClassAnnotationPath {
}

class ClassBodyCommentOnly {

    /**nope*/
    void method() {
    }

}

/**dangling*/
/**real*/
class ClassDanglingReal { // violation 'Javadoc comment is missing @author tag.'
}
