package com.puppycrawl.tools.checkstyle.utils.javadocutil;

class InputJavadocUtilMethodDefComments {

    /**modifier*/
    public void modifierPath() {
    }

    /**annotation*/
    @Deprecated
    void annotationPath() {
    }

    /**typeParams*/
    <T> T typeParameterPath(T value) {
        return value;
    }

    /**qualifiedType*/
    java.lang.String/**nope*/ qualifiedReturnType/**nope*/()/**nope*/ {
        /**nope*/
        return null;
    }

    public int bodyCommentOnly() {
        /**local class javadoc*/
        class Local {
        }
        return 0;

    }

    /**dangling*/
    /**real*/
    int danglingReal() {
        return 1;
    }
}
