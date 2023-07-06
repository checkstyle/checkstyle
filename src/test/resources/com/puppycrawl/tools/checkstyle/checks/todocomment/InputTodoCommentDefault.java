/*
TodoComment

*/

package com.puppycrawl.tools.checkstyle.checks.todocomment;

public class InputTodoCommentDefault {
    int i;
    public void method() {
        i++; // TODO: do differently in future // violation 'Comment matches to-do format 'TODO:''
        i++;
    }

}
