package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

/* Config: default */

public class InputCommentsIndentationCommentsAfterMethodCall {
    public static void main() {
        InputCommentsIndentationCommentsAfterMethodCall.flaMap(4,5)
           // ok
           .flaMap()
           .flaMap();
        // ok
    }
    public static void main1() {
        InputCommentsIndentationCommentsAfterMethodCall.flaMap(4,5)
                // ok
                .flaMap()
                .flaMap();
               // violation
    }
    public static InputCommentsIndentationCommentsAfterMethodCall flaMap(int i, int h) {
        return new InputCommentsIndentationCommentsAfterMethodCall();
    }
    public static InputCommentsIndentationCommentsAfterMethodCall flaMap() {
        return new InputCommentsIndentationCommentsAfterMethodCall();
    }

}
