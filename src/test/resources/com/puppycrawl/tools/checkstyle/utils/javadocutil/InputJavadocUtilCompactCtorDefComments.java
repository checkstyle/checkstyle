package com.puppycrawl.tools.checkstyle.utils.javadocutil;

record InputJavadocUtilCompactCtorDefComments(String value) {

    /**modifier*/
    InputJavadocUtilCompactCtorDefComments {
    }

}

record CompactBodyCommentOnly(String value) {

    CompactBodyCommentOnly {
        /**nope*/
    }

}

record CompactDanglingReal(String value) {

    /**dangling*/
    /**real*/
    CompactDanglingReal {
    }

}
