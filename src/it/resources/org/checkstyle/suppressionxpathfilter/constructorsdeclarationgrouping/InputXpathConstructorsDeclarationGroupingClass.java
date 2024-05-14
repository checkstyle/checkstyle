package org.checkstyle.suppressionxpathfilter.constructorsdeclarationgrouping;

public class InputXpathConstructorsDeclarationGroupingClass {
    InputXpathConstructorsDeclarationGroupingClass() {}

    InputXpathConstructorsDeclarationGroupingClass(int a) {}

    int x;

    InputXpathConstructorsDeclarationGroupingClass(String str) {} // warn
}
