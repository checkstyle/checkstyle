/*
ReturnCount
max = (default)2
maxForVoid = (default)1
format = (default)^equals$
tokens = (default)CTOR_DEF, METHOD_DEF, LAMBDA, LITERAL_RETURN


*/

package org.checkstyle.suppressionxpathfilter.coding.returncount;

public class InputXpathReturnCountCtor {
    InputXpathReturnCountCtor() { // violation 'Return count is 5'
        int i = 1;
        switch(i) {
        case 1: return;
        case 2: return;
        case 3: return;
        case 4: return;
        }
        return;
    }
}
