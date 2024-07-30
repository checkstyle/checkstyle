//non-compiled with javac: Compilable with Java17
package org.checkstyle.suppressionxpathfilter.sealedshouldhavepermitslist;

public sealed class InputXpathSealedShouldHavePermitsListTopLevel { // warn
   final class B extends InputXpathSealedShouldHavePermitsListTopLevel {}
   final class C extends InputXpathSealedShouldHavePermitsListTopLevel {}
   final class D { }
}
