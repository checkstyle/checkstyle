/*
com.puppycrawl.tools.checkstyle.checks.coding.DuplicateMapOrSetKeyCheck
*/

package org.checkstyle.suppressionxpathfilter.coding.duplicatemaporsetkey;

import java.util.Map;

public class InputXpathDuplicateMapOrSetKeyThree {
    void baz() {
        Map.ofEntries(
            Map.entry("key", 1),
            Map.entry("key", 2)
        );
    }
}
