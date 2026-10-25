/*
ConstructorsDeclarationGrouping
orderByIncreasingParameterCount = (default)false


*/

package org.checkstyle.suppressionxpathfilter.coding.constructorsdeclarationgrouping;

public enum InputXpathConstructorsDeclarationGroupingEnum {
    ONE;

    InputXpathConstructorsDeclarationGroupingEnum() {}

    InputXpathConstructorsDeclarationGroupingEnum(String str) {}

    int f;

    // violation below 'Constructors should be grouped together. The last grouped constructor is declared at line '15'.'
    InputXpathConstructorsDeclarationGroupingEnum(int x) {} // warn
}
