/*
InnerTypeLast


*/

package com.puppycrawl.tools.checkstyle.checks.design.innertypelast;

public class InputInnerTypeLastClassCtorsInitBlocks {
    public class Inner {
    }

    public InputInnerTypeLastClassCtorsInitBlocks() { // violation 'Init blocks, constructors, fields and methods should be before inner types.'
    }
}

class BeforeInitBlock {

    public class Inner2 {
    }

    {} // violation 'Init blocks, constructors, fields and methods should be before inner types.'

}

class BeforeStaticInitBlock {

    public interface Inner3 {
    }

    static {} // violation 'Init blocks, constructors, fields and methods should be before inner types.'
}
