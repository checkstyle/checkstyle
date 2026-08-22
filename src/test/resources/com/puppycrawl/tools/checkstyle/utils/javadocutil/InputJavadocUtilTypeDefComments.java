/*
com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocPackageCheck


*/

package com.puppycrawl.tools.checkstyle.utils.javadocutil;

/**classComment*/
class InputJavadocUtilTypeDefComments {
}

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
class ClassDanglingReal {
}
