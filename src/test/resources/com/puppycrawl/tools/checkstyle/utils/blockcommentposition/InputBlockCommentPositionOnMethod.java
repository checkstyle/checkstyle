package com.puppycrawl.tools.checkstyle.utils.blockcommentposition;

public class InputBlockCommentPositionOnMethod {
    /**
     * I'm a javadoc
     */
    int method(){
        return 0;
    }

    /**
     * I'm a javadoc
     */
    public int method1(){
        return 0;
    }

    /**
     * I'm a javadoc
     */
    @Deprecated
    int method2(){
        return 0;
    }
}
