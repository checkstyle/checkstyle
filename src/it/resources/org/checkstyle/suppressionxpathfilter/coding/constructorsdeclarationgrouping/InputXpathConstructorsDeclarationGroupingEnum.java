package org.checkstyle.suppressionxpathfilter.coding.constructorsdeclarationgrouping;

public enum InputXpathConstructorsDeclarationGroupingEnum {
    ONE;

    InputXpathConstructorsDeclarationGroupingEnum() {}

    InputXpathConstructorsDeclarationGroupingEnum(String str) {}

    int f;

    InputXpathConstructorsDeclarationGroupingEnum(int x) {} // warn
}
