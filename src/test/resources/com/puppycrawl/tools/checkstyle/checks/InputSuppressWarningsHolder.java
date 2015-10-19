package com.puppycrawl.tools.checkstyle.checks;

public class InputSuppressWarningsHolder {
    static final String UNUSED = "unused";

    @SuppressWarnings(UNUSED)
    int a;
    @SuppressWarnings(InputSuppressWarningsHolder.UNUSED)
    int b;
    @SuppressWarnings(com.puppycrawl.tools.checkstyle.checks.InputSuppressWarningsHolder.UNUSED)
    int c;
    @SuppressWarnings(value = UNUSED)
    int d;
    @SuppressWarnings(value = InputSuppressWarningsHolder.UNUSED)
    int e;
    @SuppressWarnings(value = com.puppycrawl.tools.checkstyle.checks.InputSuppressWarningsHolder.UNUSED)
    int f;
    @SuppressWarnings((1 != 1) ? "" : "unused")
    int g;
    @SuppressWarnings("un" + "used")
    int h;
    @SuppressWarnings((String) "unused")
    int i;
    @SuppressWarnings({})
    int j;
    @SuppressWarnings({UNUSED})
    int k;
    @SuppressWarnings({"unused", true ? "unused" : ""})
    int l;
}

class CustomSuppressWarnings {
    @SuppressWarnings
    private @interface SuppressWarnings {
    }
}
