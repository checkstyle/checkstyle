/*
TodoComment
format = (default)TODO:

*/

package com.puppycrawl.tools.checkstyle.checks.todocomment;

public class InputTodoCommentDefault {
    int i; // violation 9 lines above 'Comment matches .*'
    public void method() { // violation below 'Comment matches .*'
        i++; // TODO: do differently in future
        i++;
    }

}
