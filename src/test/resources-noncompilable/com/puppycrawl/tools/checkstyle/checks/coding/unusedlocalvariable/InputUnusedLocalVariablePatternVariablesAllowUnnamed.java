/*
UnusedLocalVariable
allowUnnamedVariables = (default)true
jdkVersion = (default)22

*/
// non-compiled with javac: Compilable with Java25
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

public class InputUnusedLocalVariablePatternVariablesAllowUnnamed {

    sealed abstract static class Shape permits Circle, Rectangle {}
    static final class Circle    extends Shape {}
    static final class Rectangle extends Shape {}
    record Box<T extends Shape>(T content) {}

    void unusedNamedPatternVar(Shape s) {
        switch (s) {
            case Circle    c -> process(s); // violation, unused named local variable 'c'
            case Rectangle r -> process(s); // violation, unused named local variable 'r'
        }
        switch (s) {
            case Circle    _ -> process(s);
            case Rectangle _ -> process(s);
        }
    }

    void unusedNamedRecordPattern(Box<? extends Shape> box) {
        switch (box) {
            case Box(Circle    c) -> process(box); // violation, unused named local variable 'c'
            case Box(Rectangle r) -> process(box); // violation, unused named local variable 'r'
            default -> {

            }
        }
        switch (box) {
            case Box(Circle    _) -> process(box);
            case Box(Rectangle _) -> process(box);
            default -> {

            }
        }
    }

    void unusedNamedInstanceof(Object obj) {
        if (obj instanceof String s) { // violation, unused named local variable 's'
            System.out.println("it is a string");
        }
        if (obj instanceof String t) {
            System.out.println(t);
        }
    }

   void namedloops() {
        int[] orderIDs = {34, 45, 23, 27, 15};
        int total = 0;
        for (int id : orderIDs) { // violation, unused named local variable 'id'
            total++;
        }
        System.out.println("Total: " + total);
   }
   void unnamedloops() {
        int[] orderIDs = {34, 45, 23, 27, 15};
        int total = 0;
        for (int _ : orderIDs) {
            total++;
        }
        System.out.println("Total: " + total);
   }

    void process(Object o) {}
}
