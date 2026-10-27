/*
EmptyBlock
option = text
tokens = (default)LITERAL_WHILE, LITERAL_TRY, LITERAL_FINALLY, LITERAL_DO, \
         LITERAL_IF, LITERAL_ELSE, LITERAL_FOR, INSTANCE_INIT, STATIC_INIT, \
         LITERAL_SWITCH, LITERAL_SYNCHRONIZED


*/

package org.checkstyle.suppressionxpathfilter.blocks.emptyblock;

public class InputXpathEmptyBlockText {
    private void emptyLoop() {
        for (int i = 0; i < 10; i++) {} // violation 'Empty for block.'
    }
}
