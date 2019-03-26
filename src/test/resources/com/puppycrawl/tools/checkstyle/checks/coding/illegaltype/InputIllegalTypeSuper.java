package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

import java.util.HashMap;

public interface InputIllegalTypeSuper {
    void foo(HashMap<?, ?> buffer);
    HashMap<?, ?> foo();
    Object bar();
}
