package com.puppycrawl.tools.checkstyle.checks.coding.arraytrailingcomma;

public class InputArrayTrailingCommaIgnoreInlineArrays {
    int[] a1 = new int[]
        {
            1,
            2,
            3,
        };

    Object[] a2 = new Object[] {"a"};     //violation

    boolean[] a3 = new boolean[] {true,};
    int [] a4 = new int[]{1
    };    //violation
}
