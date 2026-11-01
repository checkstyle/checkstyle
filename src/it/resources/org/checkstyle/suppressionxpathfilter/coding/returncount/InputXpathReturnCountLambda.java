/*
ReturnCount
max = (default)2
maxForVoid = (default)1
format = (default)^equals$
tokens = (default)CTOR_DEF, METHOD_DEF, LAMBDA, LITERAL_RETURN


*/

package org.checkstyle.suppressionxpathfilter.coding.returncount;

import java.util.function.Function;

public class InputXpathReturnCountLambda {
    void testLambda() {
        Function<Integer, Boolean> a = i -> { // violation 'Return count is 4'
            switch(i) {
            case 1: return true;
            case 2: return true;
            case 3: return true;
            }
            return false;
        };
    }
}
