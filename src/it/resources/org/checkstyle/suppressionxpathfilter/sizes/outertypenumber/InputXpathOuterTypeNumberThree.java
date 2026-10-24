/*
OuterTypeNumber
max = 2


*/

package org.checkstyle.suppressionxpathfilter.sizes.outertypenumber; // violation 'Outer types defined is 3 (max allowed is 2).'

public class InputXpathOuterTypeNumberThree {
    void method() {}
}

enum Direction {
    NORTH, SOUTH, EAST, WEST;
}

interface SecondInterface {
    int VALUE = 2;
}
