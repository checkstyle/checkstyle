/*
EmptyCatchBlock
exceptionVariableName = (default)^$
commentFormat = (default).*


*/

package org.checkstyle.suppressionxpathfilter.blocks.emptycatchblock;

public class InputXpathEmptyCatchBlockTwo {
    public static void main(String[] args) {

        try {
            throw new RuntimeException();
            // violation below 'Empty catch block.'
        } catch (RuntimeException e) {
        }
    }
}
