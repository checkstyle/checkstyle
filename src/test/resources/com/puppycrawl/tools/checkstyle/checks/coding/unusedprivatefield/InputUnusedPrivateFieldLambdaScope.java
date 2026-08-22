/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial
ignoredFieldPattern = (default)serialVersionUID

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

import java.util.List;

public class InputUnusedPrivateFieldLambdaScope {

    private int value; // ok, private field is used

    void test(java.util.List<Integer> values) {
        values.forEach((Integer value) -> { });
        System.out.println(value);
    }

    private int unused; // violation 'Unused private field'

    void unrelatedLambda(List<Integer> values) {
        values.forEach((Integer element) -> System.out.println(element));
    }

    private int total; // ok, private field is used

    void sumLambda(List<Integer> values) {
        values.forEach(v -> {
            System.out.println(total);
        });
    }

    private int assignedOnly; // ok, private field is used
    void set(int value) { this.assignedOnly = value; }

    private int size; // violation 'Unused private field'
    int size() { return 1; }

}
