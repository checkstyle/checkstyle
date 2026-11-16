/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial
ignoredFieldPattern = (default)serialVersionUID


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

private int unused; // violation 'Unused private field'

private int used = 1;

void main() {
    int local = 1;
    System.out.println(used + local);
}
