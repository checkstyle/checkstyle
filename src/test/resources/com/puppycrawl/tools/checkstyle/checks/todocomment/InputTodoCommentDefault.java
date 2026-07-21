/*
TodoComment
format = (default)TODO\:


*/

package com.puppycrawl.tools.checkstyle.checks.todocomment;

public class InputTodoCommentDefault {
    int i;
    public void method() { // violation below 'matches to-do format'
        i++; // TODO: do differently in future
        i++;
    }

}
