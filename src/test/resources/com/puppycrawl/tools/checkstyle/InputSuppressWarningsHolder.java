package com.puppycrawl.tools.checkstyle;

public class InputSuppressWarningsHolder {
    static final String UNUSED = "unused";

    @SuppressWarnings(UNUSED)
    int a;
    @SuppressWarnings(InputSuppressWarningsHolder.UNUSED)
    int b;
    @SuppressWarnings(com.puppycrawl.tools.checkstyle.InputSuppressWarningsHolder.UNUSED)
    int c;
    @SuppressWarnings(value = UNUSED)
    int d;
    @SuppressWarnings(value = InputSuppressWarningsHolder.UNUSED)
    int e;
    @SuppressWarnings(value = com.puppycrawl.tools.checkstyle.InputSuppressWarningsHolder.UNUSED)
    int f;
}
