package com.puppycrawl.tools.checkstyle.checks.design.innertypelast;

/** Config: default */
public class InputInnerTypeLastClassCtorsInitBlocks {
    public class Inner { // violation
    }

    public InputInnerTypeLastClassCtorsInitBlocks() {
    }
}

class BeforeInitBlock {

    public class Inner2 { // violation
    }

    {}

}

class BeforeStaticInitBlock {

    public interface Inner3 { // violation
    }

    static {}
}
