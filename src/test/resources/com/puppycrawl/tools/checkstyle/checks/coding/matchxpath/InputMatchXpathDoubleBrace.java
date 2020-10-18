package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

import java.util.ArrayList;

/* Config:
 *
 * default
 */
public class InputMatchXpathDoubleBrace {
    public void test() {
        new ArrayList<Integer>() {{ // violation
            add(2);
            add(4);
            add(6);
        }};
    }
}
