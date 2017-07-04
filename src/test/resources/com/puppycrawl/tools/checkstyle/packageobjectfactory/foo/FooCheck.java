package com.puppycrawl.tools.checkstyle.packageobjectfactory.foo;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;

public class FooCheck extends AbstractCheck {
    @Override
    public int[] getDefaultTokens() {
        return new int[] {0};
    }
}
