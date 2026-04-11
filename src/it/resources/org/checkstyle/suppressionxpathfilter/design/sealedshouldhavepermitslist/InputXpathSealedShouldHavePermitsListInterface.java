// Java17
package org.checkstyle.suppressionxpathfilter.design.sealedshouldhavepermitslist;

public sealed interface InputXpathSealedShouldHavePermitsListInterface { // warn
    final class A implements InputXpathSealedShouldHavePermitsListInterface {}
    final class B implements InputXpathSealedShouldHavePermitsListInterface {}
}
