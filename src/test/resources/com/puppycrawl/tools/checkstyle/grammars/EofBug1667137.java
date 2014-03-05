package com.puppycrawl.tools.checkstyle.grammars;

// Demonstrates the bug #1667137
class EofBug1667137 {

    void checkstyleIsBroken() {
        EofBug1667137 borkage = new EofBug1667137() {

                <T extends EofBug1667137> T borked(T brokenness) {
                    return brokenness;
                }
            };
    }
}
