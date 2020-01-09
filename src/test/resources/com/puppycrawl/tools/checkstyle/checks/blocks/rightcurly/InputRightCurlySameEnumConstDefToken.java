/*
 * Config:
 * option = same
 * tokens = ENUM_CONSTANT_DEF
 */

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlySameEnumConstDefToken {

    public enum Temp {
        FOO {
            public void doSomething(){}
        }
    }

    public enum Temp1 {
        FOO {
            public void doSomething(){} } //violation
    }

    public enum Temp2 {
        FOO { public void doSomething(){} }
    }

    public enum Temp3 {
        FOO {}
    }

    public enum Temp4 {
        FOO { public void doSomething(){}
        }
    }
}
