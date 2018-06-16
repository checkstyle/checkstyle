package com.puppycrawl.tools.checkstyle.checks.whitespace.separatorwrap;

class InputSeparatorWrapForEllipsis {

    public void testMethodWithGoodWrapping(String...
            parameters) {

    }

    public void testMethodWithBadWrapping(String
            ...parameters) {

    }

}

