package org.checkstyle.suppressionxpathfilter.returncount;

public class InputXpathReturnCount3 {
    InputXpathReturnCount3() { // warn
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
