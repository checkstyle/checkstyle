/*
SealedShouldHavePermitsList

*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.design.sealedshouldhavepermitslist;

// violation below
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

// violation below
sealed class Rectangle extends InputSealedShouldHavePermitsListJepExample {
    float length, width;
}
final class FilledRectangle extends Rectangle {
    int red, green, blue;
}
