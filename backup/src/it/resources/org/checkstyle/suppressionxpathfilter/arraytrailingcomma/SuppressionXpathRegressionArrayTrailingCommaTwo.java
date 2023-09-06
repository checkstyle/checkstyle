package org.checkstyle.suppressionxpathfilter.arraytrailingcomma;

public class SuppressionXpathRegressionArrayTrailingCommaTwo
{
     int[][] d1 = new int[][]
    {
        {1, 2,},
        {3, 3,},
        {5, 6,},
    };

    int[][] d2 = new int[][]
    {
        {1,
         2},
        {3, 3,},
        {5, 6,} // warn
    };

}
