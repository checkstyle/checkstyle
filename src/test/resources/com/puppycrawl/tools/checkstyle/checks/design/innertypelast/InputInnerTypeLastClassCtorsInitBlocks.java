/*
InnerTypeLast


*/

package com.puppycrawl.tools.checkstyle.checks.design.innertypelast;

public class InputInnerTypeLastClassCtorsInitBlocks {
    public class Inner {
    }

    // violation below 'Init blocks, constructors, fields and methods should be before inner types.'
    public InputInnerTypeLastClassCtorsInitBlocks() {
    }
}

class BeforeInitBlock {

    public class Inner2 {
    }

    // violation below 'Init blocks, constructors, fields and methods should be before inner types.'
    {}

}

class BeforeStaticInitBlock {

    public interface Inner3 {
    }

    // violation below 'Init blocks, constructors, fields and methods should be before inner types.'
    static {}
}
