/*
DuplicateSwitchBranch


*/

package org.checkstyle.suppressionxpathfilter.coding.duplicateswitchbranch;

public class InputXpathDuplicateSwitchBranchOne {
    public int test(int id) {
        switch (id) {
            case 0:
                return 1;
            case 1: // violation 'identical to the branch at line 12'
                return 1;
            default:
                return 0;
        }
    }
}
