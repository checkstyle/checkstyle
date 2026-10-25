/*
ConstructorsDeclarationGrouping
orderByIncreasingParameterCount = (default)false


*/

package org.checkstyle.suppressionxpathfilter.coding.constructorsdeclarationgrouping;

public class InputXpathConstructorsDeclarationGroupingClass {
    InputXpathConstructorsDeclarationGroupingClass() {}

    InputXpathConstructorsDeclarationGroupingClass(int a) {}

    int x;

    // violation below 'Constructors should be grouped together. The last grouped constructor is declared at line '13'.'
    InputXpathConstructorsDeclarationGroupingClass(String str) {} // warn
}
