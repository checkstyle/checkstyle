/*
DeclarationOrder
ignoreConstructors = (default)false
ignoreModifiers = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.declarationorder;

public class InputDeclarationOrder2 {
    private static char[] sHexChars = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F' };

    int[] array = new int[] {1, 2, 3};

    int[] array2 = new int[] {
            1, 2, 3
    };

}
