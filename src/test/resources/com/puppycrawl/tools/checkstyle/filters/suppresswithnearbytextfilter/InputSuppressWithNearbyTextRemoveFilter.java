/*xml
<module name="Checker">
<module name="com.puppycrawl.tools.checkstyle.filters.SuppressWithNearbyTextFilter">
</module>
<module name="com.puppycrawl.tools.checkstyle.internal.utils.FileRemovalAfterFirstUsageFilter">
</module>
<module name="TreeWalker">
<module name="com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck"/>
</module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppresswithnearbytextfilter;

public class InputSuppressWithNearbyTextRemoveFilter {
    public void foo() {
        int i = 5;
        if(i > 3);
        int y = 50;
        if(y > 50);

    }
}

