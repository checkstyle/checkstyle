/*
MissingSwitchDefault


*/

package com.puppycrawl.tools.checkstyle.checks.coding.missingswitchdefault;

public class InputMissingSwitchDefaultCaseLabelElements {
    static void m(Object o) {
        switch (o) {
            case null -> System.out.println("String, including null");
            case String s -> System.out.println("String, including null");
            default -> System.out.println("something else");
        }

        switch (o) {
            case null: System.out.println("String, including null"); break;
            case String s: System.out.println("String, including null"); break;
            default: System.out.println("something else");
        }

        switch(o) {
            case null: default:
                System.out.println("The rest (including null)");
        }

        switch(o) { // ok, pattern totality enforced by compiler
            case Object o1:
                System.out.println("object");
        }

        switch(o) { // ok, pattern totality enforced by compiler
            case Object o1 when o1.toString().length() > 2:
                System.out.println("object");
                break;
            case Object o1 when o1.toString().length() <= 2:
                System.out.println("object");
                break;
            case null:
                break;
            case Object o1:
                break;
        }

        switch(o) {
            case String str -> System.out.println("null");
            case null -> System.out.println("null");
            default -> System.out.println("default");
        }

        switch(o) {
            case null, default ->
                System.out.println("The rest (including null)");
        }

        switch(o) {
            case String s when s.length() > 2 ->
                System.out.println("The string longer than 2 chars");
            default -> System.out.println("default!");
        }

        switch(o) {
            case null -> System.out.println("The rest (including null)");
            default -> System.out.println("The rest (including null)");
        }

        switch(o) {
            case null:
            default:
                System.out.println("The rest (including null)");
        }

        switch(o) {
            case null, default:
                System.out.println("The rest (including null)");
        }

        switch(o) {
            case null, default:
                throw new UnsupportedOperationException("not supported!");
        }
    }

    void m2(String s) {
        switch (s) {
            case "a": throw new AssertionError("Wrong branch.");
            default: break;
        }
        switch (s) {
            case "a" -> throw new AssertionError("Wrong branch.");
            default -> {}
        }
    }
}
