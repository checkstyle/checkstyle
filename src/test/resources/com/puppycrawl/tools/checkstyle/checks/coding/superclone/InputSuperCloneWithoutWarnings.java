package com.puppycrawl.tools.checkstyle.checks.coding.superclone;

public class InputSuperCloneWithoutWarnings {
    @Override
    protected final Object clone() throws CloneNotSupportedException {
        return new InputSuperCloneWithoutWarnings();
    }
}
