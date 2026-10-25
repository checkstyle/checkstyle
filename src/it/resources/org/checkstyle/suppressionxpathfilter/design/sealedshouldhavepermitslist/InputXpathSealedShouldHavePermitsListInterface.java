/*
SealedShouldHavePermitsList


*/


package org.checkstyle.suppressionxpathfilter.design.sealedshouldhavepermitslist;

public sealed interface InputXpathSealedShouldHavePermitsListInterface {
// violation above 'Sealed classes or interfaces should explicitly declare permitted subclasses.'
    final class A implements InputXpathSealedShouldHavePermitsListInterface {}
    final class B implements InputXpathSealedShouldHavePermitsListInterface {}
}
