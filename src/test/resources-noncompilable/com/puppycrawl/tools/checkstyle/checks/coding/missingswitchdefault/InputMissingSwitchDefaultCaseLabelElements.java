/*
MissingSwitchDefault


*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.coding.missingswitchdefault;

public class InputMissingSwitchDefaultCaseLabelElements {
    static void m(Object o) {
        switch (o) { // ok
            case null, String s -> System.out.println("String, including null");
            default -> System.out.println("something else");
        }

        switch (o) { // ok
            case null, String s: System.out.println("String, including null"); break;
            default: System.out.println("something else");
        }

        // The following switch statements are equivalent:

        switch(o) {
            case null: default: // ok
                System.out.println("The rest (including null)");
        }

        switch(o) { // ok
            case null, default ->
                System.out.println("The rest (including null)");
        }

        switch(o) { // ok
            case null, default:
                throw new UnsupportedOperationException("not supported!");
        }

        switch(o) { // violation
            case null:
                System.out.println("null");
        }

        switch(o) { // violation
            case null ->
                System.out.println("null");
        }
    }
}
