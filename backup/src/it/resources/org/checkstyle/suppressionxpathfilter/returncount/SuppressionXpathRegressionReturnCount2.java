package org.checkstyle.suppressionxpathfilter.returncount;

public class SuppressionXpathRegressionReturnCount2 {
    public boolean equals(Object obj) { // OK
        int i = 1;
        switch (i) {
        case 1: return true;
        case 2: return true;
        case 3: return true;
        case 4: return true;
        case 5: return true;
        case 6: return true;
        }
        return false;
    }
    boolean testNonVoid() { // warn
        int i = 1;
        switch(i) {
        case 1: return true;
        case 2: return true;
        case 3: return true;
        }
        return false;
    }

}
