package org.checkstyle.suppressionxpathfilter.constructorsdeclarationgrouping;

public enum InputXpathConstructorsDeclarationGroupingTwo {
    ONE;

    InputXpathConstructorsDeclarationGroupingTwo() {}

    InputXpathConstructorsDeclarationGroupingTwo(String str) {}

    int f;

    InputXpathConstructorsDeclarationGroupingTwo(int x) {} // warn
}
