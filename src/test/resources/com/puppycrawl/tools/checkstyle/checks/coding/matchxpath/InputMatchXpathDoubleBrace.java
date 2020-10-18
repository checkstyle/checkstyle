package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

import java.util.ArrayList;

/* Config:
 *
 * query: //INSTANCE_INIT[not(../*[not(self::LCURLY or self::INSTANCE_INIT or self::RCURLY or
 * self::SINGLE_LINE_COMMENT or self::BLOCK_COMMENT_BEGIN)])]
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
