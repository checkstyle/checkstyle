/*
TodoComment
format = (default)TODO\:


*/

package com.puppycrawl.tools.checkstyle.checks.todocomment;

public class InputTodoCommentDefault {
    int i;
    public void method() { // violation below "Comment matches to-do format 'T.DO:'\."
        i++; // TODO: do differently in future
        i++;
    }

}
