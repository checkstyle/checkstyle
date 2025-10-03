/*
TodoComment
format = (default)"TODO:"
tokens = (default){SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN}
*/
package com.puppycrawl.tools.checkstyle.checks.todocomment;

public class InputTodoCommentDefault {
    int i;
    public void method() { // violation below 'Comment matches .*'
        i++; // TODO: do differently in future
        i++;
    }
}
