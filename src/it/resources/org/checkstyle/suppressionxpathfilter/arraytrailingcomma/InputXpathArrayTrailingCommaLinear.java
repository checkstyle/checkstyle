package org.checkstyle.suppressionxpathfilter.arraytrailingcomma;

public class InputXpathArrayTrailingCommaLinear
{
    int[] a1 = new int[]
    {
        1,
        2,
        3,
    };

    int[] a2 = new int[]
    {
        1,
        2,
        3 // warn
    };
}
