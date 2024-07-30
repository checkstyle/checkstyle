//non-compiled with javac: Compilable with Java17
package org.checkstyle.suppressionxpathfilter.sealedshouldhavepermitslist;

public class InputXpathSealedShouldHavePermitsListInner {
   sealed class A {}   // warn
   final class B extends A {}
   final class C extends A {}
   final class D { }
}
