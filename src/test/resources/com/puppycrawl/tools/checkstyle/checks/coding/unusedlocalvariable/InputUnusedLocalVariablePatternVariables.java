/*
UnusedLocalVariable
allowUnnamedVariables = false

*/

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
        }
    }

    void usedInArrowCase(Shape s) {
        switch (s) {
            case Circle    c -> process(c);
            case Rectangle r -> process(r);
        }
    }

    void unnamedInArrowCase(Shape s) {
        switch (s) {
            case Circle    _ -> process(s);
            case Rectangle _ -> process(s);
        }
    }

    void unusedRecordPatternArrow(Box<? extends Shape> box) {
        switch (box) {
            case Box(Circle c)    -> process(box); // violation, Unused local variable 'c'
            case Box(Rectangle r) -> process(box); // violation, Unused local variable 'r'
        }
    }

    void usedRecordPatternArrow(Box<? extends Shape> box) {
        switch (box) {
            case Box(Circle    c) -> process(c);
            case Box(Rectangle r) -> process(r);
        }
    }

    void unnamedRecordPatternArrow(Box<? extends Shape> box) {
        switch (box) {
            case Box(Circle    _) -> process(box);
            case Box(Rectangle _) -> process(box);
        }
    }

    void unusedGuardedPattern(Object obj) {
        switch (obj) {

            case Integer i when i > 0 -> System.out.println("positive");
            default                   -> System.out.println("other");
        }
    }

    void usedInGuardedPattern(Object obj) {
        switch (obj) {
            case Integer i when i > 0 -> System.out.println(i); // ok
            default                   -> System.out.println("other");
        }
    }

    void unusedInColonCase(Object obj) {
        switch (obj) {
            case String s: // violation, Unused local variable 's'
                System.out.println("string");
                break;
            default:
                break;
        }
    }

    void usedInColonCase(Object obj) {
        switch (obj) {
            case String s:
                System.out.println(s);
                break;
            default:
                break;
        }
    }

    void unusedInstanceof(Object obj) {
        if (obj instanceof String s) { // violation, Unused local variable 's'
            System.out.println("it is a string");
        }
    }

    void usedInstanceof(Object obj) {
        if (obj instanceof String s) {
            System.out.println(s);
        }
    }

    void process(Object o) {}

    class Test {
    Object field = (Object) (true ? "" : 1) instanceof String s
        ? s
        : null;
    }
}
