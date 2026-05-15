/*
RedundantThis
checkMethodCall=(default)false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

public class InputRedundantThisInstantVariables {
    String name = "abc";
    String name2 = this.name;
    // violation above, 'Redundant "this", variable 'name' can be accessed directly.'
}
