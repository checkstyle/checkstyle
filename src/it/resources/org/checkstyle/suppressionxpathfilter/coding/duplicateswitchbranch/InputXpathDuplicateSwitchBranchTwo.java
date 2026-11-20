/*
DuplicateSwitchBranch


*/

package org.checkstyle.suppressionxpathfilter.coding.duplicateswitchbranch;

public class InputXpathDuplicateSwitchBranchTwo {
    public int test(String value) {
        return switch (value) {
            case "A" -> 1;
            case "B" -> 1; // violation 'identical to the branch at line 12'
            default -> 0;
        };
    }
}
