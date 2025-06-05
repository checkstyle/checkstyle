package org.checkstyle.suppressionxpathfilter;

public class InputXpathSealedShouldHavePermitsListInner {
   sealed class A {}   // warn
   final class B extends A {}
   final class C extends A {}
   final class D { }
}
