//non-compiled with eclipse: The value for annotation attribute must be a constant expression
package com.puppycrawl.tools.checkstyle.checks.suppresswarningsholder;
public class InputSuppressWarningsHolderNonConstant {
    static final String UNUSED = "unused";

    @SuppressWarnings(UNUSED)
    int a;
    @SuppressWarnings(InputSuppressWarningsHolderNonConstant.UNUSED)
    int b;
    @SuppressWarnings(InputSuppressWarningsHolderNonConstant.UNUSED)
    int c;
    @SuppressWarnings(value = UNUSED)
    int d;
    @SuppressWarnings(value = InputSuppressWarningsHolderNonConstant.UNUSED)
    int e;
    @SuppressWarnings(value = InputSuppressWarningsHolderNonConstant.UNUSED)
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
