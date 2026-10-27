/*
ConstructorsDeclarationGrouping
orderByIncreasingParameterCount = (default)false


*/

package org.checkstyle.suppressionxpathfilter.coding.constructorsdeclarationgrouping;

public class InputXpathConstructorsDeclarationGroupingClass {
    InputXpathConstructorsDeclarationGroupingClass() {}

    InputXpathConstructorsDeclarationGroupingClass(int a) {}

    int x;

    // violation below 'Constructors should be grouped together.'
    InputXpathConstructorsDeclarationGroupingClass(String str) {}
}
