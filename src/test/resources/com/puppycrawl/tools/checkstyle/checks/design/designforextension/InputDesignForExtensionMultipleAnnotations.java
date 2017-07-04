package com.puppycrawl.tools.checkstyle.checks.design.designforextension;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

public class InputDesignForExtensionMultipleAnnotations {

    @Ignore
    @Deprecated
    public void foo1() {
        foo3();
    }

    @Deprecated
    @Ignore
    public void foo2() {
        foo3();
    }

    @Ignore
    // comment
    @Deprecated
    public void foo4() {
        foo3();
    }

    @Deprecated
    // comment
    @Ignore
    public void foo5() {
        foo3();
    }


    @Ignore
    /**
     * comment
     */
    @Deprecated
    public void foo6() {
        foo3();
    }

    @Deprecated
    /**
     * comment
     */
    @Ignore
    public void foo7() {
        foo3();
    }

    @Ignore
    /* comment */
    @Deprecated
    public void foo8() {
        foo3();
    }

    @Deprecated
    /* comment */
    @Ignore
    public void foo9() {
        foo3();
    }

    /* comment */
    @Ignore
    @Deprecated
    public void foo10() {
        foo3();
    }

    /* comment */
    @Deprecated
    @Ignore
    public void foo11() {
        foo3();
    }

    private void foo3() {}
}
