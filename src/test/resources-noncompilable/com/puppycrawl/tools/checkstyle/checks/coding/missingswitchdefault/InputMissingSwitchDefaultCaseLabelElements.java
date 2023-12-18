/*
MissingSwitchDefault


*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.coding.missingswitchdefault;

public class InputMissingSwitchDefaultCaseLabelElements {
    static void m(Object o) {
        switch (o) {
            case null, String s -> System.out.println("String, including null");
            default -> System.out.println("something else");
        }

        switch (o) {
            case null, String s: System.out.println("String, including null"); break;
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
            case Object o1 && o1.toString().length() > 2:
                System.out.println("object");
                break;
            case Object o1 && o1.toString().length() <= 2:
                System.out.println("object");
                break;
            case null, Object o1:
                break;
        }

        switch(o) {
            case String str, null ->
                System.out.println("null");
            default -> System.out.println("default");
        }

        switch(o) {
            case null, default ->
                System.out.println("The rest (including null)");
        }

        switch(o) {
            case String s && s.length() > 2 ->
                System.out.println("The string longer than 2 chars");
            default -> System.out.println("default!");
        }

        switch(o) {
            case default, null ->
                System.out.println("The rest (including null)");
        }

        switch(o) {
            case default, null:
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
            case default: break;
        }
        switch (s) {
            case "a" -> throw new AssertionError("Wrong branch.");
            case default -> {}
        }
    }
}
