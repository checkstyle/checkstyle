/*
com.puppycrawl.tools.checkstyle.checks.coding.DuplicateMapOrSetKeyCheck
*/

package org.checkstyle.suppressionxpathfilter.coding.duplicatemaporsetkey;

import java.util.Map;

public class InputXpathDuplicateMapOrSetKeyTwo {
    void bar() {
        Map.of("k1", 1, "k2", 2, "k1", 3);
    }
}
