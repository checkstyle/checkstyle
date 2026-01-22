/*
SealedShouldHavePermitsList

*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.design.sealedshouldhavepermitslist;

public sealed class InputSealedShouldHavePermitsListJepExample permits Circle, Square, Rectangle { }

final class Circle extends InputSealedShouldHavePermitsListJepExample {
    float radius;
}
non-sealed class Square extends InputSealedShouldHavePermitsListJepExample {
    float side;
}

sealed class Rectangle extends InputSealedShouldHavePermitsListJepExample permits FilledRectangle {
    float length, width;
}
final class FilledRectangle extends Rectangle {
    int red, green, blue;
}
