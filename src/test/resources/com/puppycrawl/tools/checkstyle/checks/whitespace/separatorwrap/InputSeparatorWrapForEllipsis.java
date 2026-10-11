/*
SeparatorWrap
option = (default)EOL
tokens = ELLIPSIS


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.separatorwrap;

class InputSeparatorWrapForEllipsis {

    public void testMethodWithGoodWrapping(String...
            parameters) {

    }

    public void testMethodWithBadWrapping(String
            ...parameters) { // violation ''...' should be on the previous line'

    }

}

