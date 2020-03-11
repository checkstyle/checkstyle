package org.checkstyle.suppressionxpathfilter.arraytrailingcomma;

public class SuppressionXpathRegressionArrayTrailingCommaOne
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
