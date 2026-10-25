/*
SealedShouldHavePermitsList


*/


package org.checkstyle.suppressionxpathfilter.design.sealedshouldhavepermitslist;

public sealed class InputXpathSealedShouldHavePermitsListTopLevel {
// violation above 'Sealed classes or interfaces should explicitly declare permitted subclasses.'
   final class B extends InputXpathSealedShouldHavePermitsListTopLevel {}
   final class C extends InputXpathSealedShouldHavePermitsListTopLevel {}
   final class D { }
}
