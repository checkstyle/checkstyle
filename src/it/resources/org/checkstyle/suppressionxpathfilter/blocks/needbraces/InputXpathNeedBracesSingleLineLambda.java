/*
NeedBraces
allowEmptyLoopBody = (default)false
allowSingleLineStatement = true
tokens = LAMBDA


*/

package org.checkstyle.suppressionxpathfilter.blocks.needbraces;

public class InputXpathNeedBracesSingleLineLambda {
    static Runnable r3 = () -> // violation ''->'' construct must use '{}'s.'
            String.CASE_INSENSITIVE_ORDER.equals("Hello world two!");
}
