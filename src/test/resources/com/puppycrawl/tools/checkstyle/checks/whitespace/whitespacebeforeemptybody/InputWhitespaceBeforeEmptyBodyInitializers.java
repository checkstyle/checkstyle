/*
WhitespaceBeforeEmptyBody
tokens = STATIC_INIT


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacebeforeemptybody;

public class InputWhitespaceBeforeEmptyBodyInitializers {

    static{  // violation ''{' is not preceded with whitespace'
    }

    static{} // violation ''{' is not preceded with whitespace'

    static{  // violation ''{' is not preceded with whitespace'
      // comment
    }

    static{
      int a = 1;
    }

    static {}

}
