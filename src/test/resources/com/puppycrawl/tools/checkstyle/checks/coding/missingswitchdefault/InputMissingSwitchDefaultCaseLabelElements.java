/*
MissingSwitchDefault


*/

// java21
package com.puppycrawl.tools.checkstyle.checks.coding.missingswitchdefault;

public class InputMissingSwitchDefaultCaseLabelElements {
    static void m(Object o) {
        switch (o) {
            case null -> System.out.println("null");
            case String s -> System.out.println("String");
            default -> System.out.println("something else");
        }

        switch (o) {
            case null: System.out.println("null"); break;
            case String s: System.out.println("String"); break;
            default: System.out.println("something else");
        }

        switch(o) {
            case null: System.out.println("null"); break;
            default: System.out.println("The rest");
        }

        switch(o) { // ok, pattern totality enforced by compiler
            case Object o1 -> System.out.println("object");
        }

        switch(o) { // ok, pattern totality enforced by compiler
            case Object o1 when o1.toString().length() > 2 ->
                System.out.println("object");
            case Object o1 when o1.toString().length() <= 2 ->
                System.out.println("object");
            case null -> {}
            case Object o1 -> {}
        }

        switch(o) {
            case String str -> System.out.println("string");
            case null -> System.out.println("null");
            default -> System.out.println("default");
        }

        switch(o) {
            case null -> System.out.println("null");
            default -> System.out.println("The rest");
        }

        switch(o) {
            case String s when s.length() > 2 ->
                System.out.println("The string longer than 2 chars");
            default -> System.out.println("default!");
        }

        switch(o) {
            case null -> System.out.println("null");
            default -> System.out.println("The rest");
        }

        switch(o) {
            case null -> System.out.println("null");
            default -> System.out.println("The rest");
        }

        switch(o) {
            case null -> System.out.println("null");
            default -> System.out.println("The rest");
        }

        switch(o) {
            case null -> throw new UnsupportedOperationException("not supported!");
            default -> throw new UnsupportedOperationException("not supported!");
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
