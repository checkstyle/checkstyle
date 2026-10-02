/*
com.puppycrawl.tools.checkstyle.checks.coding.DuplicateMapOrSetKeyCheck
*/

package org.checkstyle.suppressionxpathfilter.coding.duplicatemaporsetkey;

import java.util.Set;

public class InputXpathDuplicateMapOrSetKey {
    void foo() {
        Set.of("a", "b", "a");
    }
}
