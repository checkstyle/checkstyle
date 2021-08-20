/*
MatchXpath
query = //INSTANCE_INIT[not(../*[not(\n                    self::LCURLY or\n                    \
        self::INSTANCE_INIT or\n                    self::RCURLY or\n                    \
        self::SINGLE_LINE_COMMENT or\n                    self::BLOCK_COMMENT_BEGIN\
        \n                )])]
message.matchxpath.match = Do not use double-brace initialization


*/

package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

import java.util.ArrayList;

public class InputMatchXpathDoubleBrace {
    public void test() {
        new ArrayList<Integer>() {{ // violation
            add(2);
            add(4);
            add(6);
        }};
    }
}
