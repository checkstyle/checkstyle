package com.puppycrawl.tools.checkstyle.checks.whitespace.separatorwrap;

class InputSeparatorWrapTestEllipsis {

    public void testMethodWithGoodWrapping(String...
            parameters) {

    }

    public void testMethodWithBadWrapping(String
            ...parameters) {

    }

}

