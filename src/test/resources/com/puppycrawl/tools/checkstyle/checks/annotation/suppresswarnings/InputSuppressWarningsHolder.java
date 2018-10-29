package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings;

public class InputSuppressWarningsHolder {
    static final String UN_U = "UN_U";

    @SuppressWarnings(UN_U)
    int a;
    @SuppressWarnings(InputSuppressWarningsHolder.UN_U)
    int b;
    @SuppressWarnings(
com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings.InputSuppressWarningsHolder.UN_U)
    int c;
    @SuppressWarnings(value = UN_U)
    int d;
    @SuppressWarnings(value = InputSuppressWarningsHolder.UN_U)
    int e;
    @SuppressWarnings(value =
com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings.InputSuppressWarningsHolder.UN_U)
    int f;
    @SuppressWarnings((1 != 1) ? "" : "UN_U")
    int g;
    @SuppressWarnings("un" + "used")
    int h;
    @SuppressWarnings((String) "UN_U")
    int i;
    @SuppressWarnings({})
    int j;
    @SuppressWarnings({UN_U})
    int k;
}

class CustomSuppressWarnings {
    @SuppressWarnings
    private @interface SuppressWarnings {
    }
}
