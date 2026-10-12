/*
TodoComment
format = FIXME\:


*/

package org.checkstyle.suppressionxpathfilter.todocomment;

public class InputXpathTodoCommentInsideMethod {
    void method() { // violation below 'matches to-do format'
        // FIXME: //warn
    }
}
