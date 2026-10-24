/*
AnonInnerLength
max = 5


*/

package org.checkstyle.suppressionxpathfilter.sizes.anoninnerlength;

import java.util.Comparator;

public class InputXpathAnonInnerLength {
    public int compare(String v1, String v2) {
        // violation below 'Anonymous inner class length is 6 lines (max allowed is 5).'
        Comparator<String> comp = new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareTo(s2);
            }
        };
        return comp.compare(v1, v2);
    }
}
