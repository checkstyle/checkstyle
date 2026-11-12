/*
DuplicateSwitchBranch


*/

package org.checkstyle.suppressionxpathfilter.coding.duplicateswitchbranch;

public class InputXpathDuplicateSwitchBranchThree {
    private int field;

    InputXpathDuplicateSwitchBranchThree(int id) {
        switch (id) {
            case 0 -> field = 1;
            case 1 -> field = 1; // violation 'identical to the branch at line 14'
            default -> field = 0;
        }
    }
}
