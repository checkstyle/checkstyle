/*
SealedShouldHavePermitsList


*/


package org.checkstyle.suppressionxpathfilter.design.sealedshouldhavepermitslist;

public class InputXpathSealedShouldHavePermitsListInner {
   sealed class A {}
   // violation above 'Sealed classes or interfaces should explicitly declare permitted subclasses.'
   final class B extends A {}
   final class C extends A {}
   final class D { }
}
