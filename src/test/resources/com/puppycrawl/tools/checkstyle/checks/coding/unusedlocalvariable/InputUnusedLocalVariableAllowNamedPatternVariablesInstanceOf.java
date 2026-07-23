/*
UnusedLocalVariable
allowUnnamedVariables = false
jdkVersion = 21

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

public class InputUnusedLocalVariableAllowNamedPatternVariablesInstanceOf {

    sealed interface Customer {}
    record Person(String name, int age) implements Customer {}
    record Company(String name) implements Customer {}
    record Organization(String orgName, Person contactPerson) {}

    sealed interface Maybe<T> {}
    record None<T>() implements Maybe<T> {}
    record Some<T>(T value) implements Maybe<T> {}

    void method(Object object) {
        if (object instanceof String s) { // violation 'Unused local variable'
            System.out.println("string");
        }
    }
    String name(Customer customer) {
        if (customer instanceof Person(String name, int ignoredAge)) {
            return name;
        } else if (customer instanceof Company(String companyName)) {
            return companyName;
        }
        throw new IllegalStateException();
    }

    boolean isNested(Maybe<?> maybe) {
        if (maybe instanceof Some(Some<?> inner)) {
            return true;
        }
        return false;
    }

    void traditionalSwitchRecord(Object object) {
    switch (object) {
        case Person(String name, int ignoredAge):
            System.out.println(name);
            break;
        default:
            break;
    }
    }

    void nestedRecordPattern(Object object) {
        if (object instanceof Organization(String orgName,
                                           Person(String name, int nestedAge))) {
            System.out.println("org name");
        }
    }
}
