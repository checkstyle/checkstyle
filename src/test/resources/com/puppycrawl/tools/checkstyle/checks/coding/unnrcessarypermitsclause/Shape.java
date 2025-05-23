/*
UnnecessaryPermitsClause

 */
package com.puppycrawl.tools.checkstyle.checks.coding.unnrcessarypermitsclause;

interface Shape {
    double area();
}

// record implementing Shape
class Circle implements  Shape{
    @Override
    public double area() {
        return Math.PI;
    }
}
