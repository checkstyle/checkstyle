/*
UnusedLocalVariable
allowUnnamedVariables = false
jdkVersion = (default)22

*/
// non-compiled with javac: Compilable with Java25
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

public class InputUnusedLocalVariablePatternVariables {

    sealed abstract static class Shape permits Circle, Rectangle {}
    static final class Circle    extends Shape {}
    static final class Rectangle extends Shape {}
    record Box<T extends Shape>(T content) {}

    void unusedInArrowCase(Shape s) {
        switch (s) {
            case Circle c    -> process(s); // violation, Unused local variable 'c'
            case Rectangle r -> process(s); // violation, Unused local variable 'r'
            default -> { }
        }
    }

    void usedInArrowCase(Shape s) {
        switch (s) {
            case Circle    c -> process(c);
            case Rectangle r -> process(r);
            default -> { }
        }
    }

    void unnamedInArrowCase(Shape s) {
        switch (s) {
            case Circle    _ -> process(s);
            case Rectangle _ -> process(s);
            default -> { }
        }
    }

    void unusedRecordPatternArrow(Box<? extends Shape> box) {
        switch (box) {
            case Box(Circle c)    -> process(box); // violation, Unused local variable 'c'
            case Box(Rectangle r) -> process(box); // violation, Unused local variable 'r'
            default -> { }
        }
    }

    void usedRecordPatternArrow(Box<? extends Shape> box) {
        switch (box) {
            case Box(Circle    c) -> process(c);
            case Box(Rectangle r) -> process(r);
            default -> { }
        }
    }

    void unnamedRecordPatternArrow(Box<? extends Shape> box) {
        switch (box) {
            case Box(Circle    _) -> process(box);
            case Box(Rectangle _) -> process(box);
            default -> { }
        }
    }

    void process(Object o) {}
}
