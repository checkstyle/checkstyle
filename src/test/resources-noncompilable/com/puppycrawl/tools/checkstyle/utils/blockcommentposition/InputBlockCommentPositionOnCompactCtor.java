//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.utils.blockcommentposition;

public record InputBlockCommentPositionOnCompactCtor() {
    /**
     * I'm a javadoc
     */
    public InputBlockCommentPositionOnCompactCtor{}
}


record InputBlockCommentPositionOnRecord1() {
    /**
     * I'm a javadoc
     */
    public InputBlockCommentPositionOnRecord1{}
}


record InputBlockCommentPositionOnRecord2() {
    /**
     * I'm a javadoc
     */
    @Deprecated
    public InputBlockCommentPositionOnRecord2{}
}
