package org.checkstyle.suppressionxpathfilter.anoninnerlength;

import java.util.Comparator;

public class SuppressionXpathRegressionAnonInnerLength {
    public int compare(String v1, String v2) {
        Comparator<String> comp = new Comparator<String>() { // warn: inner class is 6 lines (max=5)
            @Override
            public int compare(String s1, String s2) {
                return s1.compareTo(s2);
            }
        };
        return comp.compare(v1, v2);
    }
}
