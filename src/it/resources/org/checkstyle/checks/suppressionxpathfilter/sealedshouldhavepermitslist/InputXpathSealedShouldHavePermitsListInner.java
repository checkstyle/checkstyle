// Java17
package org.checkstyle.checks.suppressionxpathfilter.sealedshouldhavepermitslist;

public class InputXpathSealedShouldHavePermitsListInner {
   sealed class A {}   // warn
   final class B extends A {}
   final class C extends A {}
   final class D { }
}
