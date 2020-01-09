/*
 * Config:
 * option = alone
 * tokens = ENUM_CONSTANT_DEF
 */

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyAloneEnumConstDefTokenWithSemi {

    public enum Temp {
        FOO {
            public void doSomething(){}
        }; //violation
    }

    public enum Temp1 {
        FOO {
            public void doSomething(){} }; //violation
    }

    public enum Temp2 {
        FOO { public void doSomething(){} }; //violation
    }

    public enum Temp3 {
        FOO {}; //violation
    }

    public enum Temp4 {
        FOO { public void doSomething(){}
        }; //violation
    }
}
