/*
SealedShouldHavePermitsList

*/

package com.puppycrawl.tools.checkstyle.checks.design.sealedshouldhavepermitslist;

// violation below 'Sealed classes or interfaces should explicitly declare permitted subclasses'
public sealed class InputSealedShouldHavePermitsListJepExample
        // The permits clause has been omitted
        // as its permitted classes have been
        // defined in the same file.
{ }

final class Circle extends InputSealedShouldHavePermitsListJepExample {
    float radius;
}
non-sealed class Square extends InputSealedShouldHavePermitsListJepExample {
    float side;
}

// violation below 'Sealed classes or interfaces should explicitly declare permitted subclasses'
sealed class Rectangle extends InputSealedShouldHavePermitsListJepExample {
    float length, width;
}
final class FilledRectangle extends Rectangle {
    int red, green, blue;
}
