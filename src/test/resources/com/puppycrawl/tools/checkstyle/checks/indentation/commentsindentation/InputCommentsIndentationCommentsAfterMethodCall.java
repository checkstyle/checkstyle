/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN


*/

package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;


public class InputCommentsIndentationCommentsAfterMethodCall {
    public static void main() {
        InputCommentsIndentationCommentsAfterMethodCall.flatMap(4,5)
           .flatMap()
           .flatMap();
    }
    public static void main1() {
        InputCommentsIndentationCommentsAfterMethodCall.flatMap(4,5)
                .flatMap()
                .flatMap();
               // violation '.* incorrect .* level 15, expected is 8, .* same .* as line 20.'
    }
    public static InputCommentsIndentationCommentsAfterMethodCall flatMap(int i, int h) {
        return new InputCommentsIndentationCommentsAfterMethodCall();
    }
    public static InputCommentsIndentationCommentsAfterMethodCall flatMap() {
        return new InputCommentsIndentationCommentsAfterMethodCall();
    }

}
