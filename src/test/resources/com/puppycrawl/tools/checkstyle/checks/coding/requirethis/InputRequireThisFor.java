package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

import java.nio.file.Path;
import java.nio.file.Paths;

public class InputRequireThisFor {
    private String name;
    int bottom;

    public void method1() {
        for (int i = 0; i < 10; i++) {
            int bottom = i - 4;
            bottom = bottom > 0 ? bottom - 1 : bottom;
        }
    }

    public void method2() {
        for (String name : new String[]{}) {
        }

        Path jarfile = Paths.get(name + ".jar");
    }
}
