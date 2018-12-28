package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

public class InputRedundantModifierPrivateMethodInPrivateClass {
    private class Inner {
        private final void example() {}
    }
}
