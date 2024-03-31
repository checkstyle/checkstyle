package org.checkstyle.suppressionxpathfilter.constructorsdeclarationgrouping;

public class InputXpathConstructorsDeclarationGroupingOne {
    InputXpathConstructorsDeclarationGroupingOne() {}

    InputXpathConstructorsDeclarationGroupingOne(int a) {}

    int x;

    InputXpathConstructorsDeclarationGroupingOne(String str) {} // warn
}
