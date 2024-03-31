package org.checkstyle.suppressionxpathfilter.constructorsdeclarationgrouping;

public enum InputXpathConstructorsDeclarationGroupingEnum {
    ONE;

    InputXpathConstructorsDeclarationGroupingEnum() {}

    InputXpathConstructorsDeclarationGroupingEnum(String str) {}

    int f;

    InputXpathConstructorsDeclarationGroupingEnum(int x) {} // warn
}
