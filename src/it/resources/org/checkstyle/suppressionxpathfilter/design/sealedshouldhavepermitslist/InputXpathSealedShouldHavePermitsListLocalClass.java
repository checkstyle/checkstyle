package org.checkstyle.suppressionxpathfilter.design.sealedshouldhavepermitslist;

public class InputXpathSealedShouldHavePermitsListLocalClass {
    void test() {
    sealed class A {} //warn
    final class B extends A {}
    final class C extends A {}
    final class D { }
    }
}
