package org.checkstyle.suppressionxpathfilter.returncount;

class SuppressionXpathRegressionReturnCount1 {
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
    void testVoid() { // warn
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
