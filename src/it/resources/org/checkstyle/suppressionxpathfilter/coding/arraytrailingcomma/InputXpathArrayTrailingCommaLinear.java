/*
ArrayTrailingComma
alwaysDemandTrailingComma = (default)false


*/

package org.checkstyle.suppressionxpathfilter.coding.arraytrailingcomma;

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
        3 // violation 'Array should contain trailing comma.'
    };
}
