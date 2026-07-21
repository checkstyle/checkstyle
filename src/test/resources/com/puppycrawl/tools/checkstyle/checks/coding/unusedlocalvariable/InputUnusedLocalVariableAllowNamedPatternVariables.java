/*
UnusedLocalVariable
allowUnnamedVariables = false
jdkVersion = 23

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

public class InputUnusedLocalVariableAllowNamedPatternVariables {
    record Ignored(int x, int y) {}
    sealed interface Customer {}
    record Person(String name, int age) implements Customer {}
    record Company(String name) implements Customer {}
    sealed interface Maybe<T> {}
    record None<T>() implements Maybe<T> {}
    record Some<T>(T value) implements Maybe<T> {}


    String whatClass(Object object) {
        return switch (object) {
            case String ignored -> "A String"; // violation, 'Unused local variable'
            case Integer ignored2 -> "An Integer"; // violation, 'Unused local variable'
            default -> "Something Else";
        };
    }

    void method(Object object) {
        int x = 10; // violation, 'Unused local variable'
        if (object instanceof String ignored) { // violation, 'Unused local variable'
            System.out.println("string");
        }
    }

    String withrecord(Object object) {
        return switch (object) { // violation below, unused local variable 'y'
            case Ignored(int y, int z) -> "record switch"; // violation, unused local variable 'z'
            default -> "other";
        };
    }

    String nameWithoutIgnored(Customer customer) { // violation below, 'Unused local variable'
        if (customer instanceof Person(String name, int ignoredAge)) {
            return name;
        } else if (customer instanceof Company(String companyName)) {
            return companyName;
        }

        if (customer instanceof Person nameWithoutIgnored) {
            return nameWithoutIgnored.name();
        }
        else if (customer instanceof Company company) {
            return company.name();
        }
        throw new IllegalStateException();
    }

    boolean isNested(Maybe<?> maybe) {
        if (maybe instanceof Some(Some<?> inner)) { // violation, 'Unused local variable'
            return true;
        }
        return false;
    }

}
