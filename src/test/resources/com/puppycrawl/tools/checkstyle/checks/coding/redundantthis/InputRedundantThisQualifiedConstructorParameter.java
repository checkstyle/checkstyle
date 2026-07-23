/*
RedundantThis
checkMethodCall=(default)false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;
public class InputRedundantThisQualifiedConstructorParameter {
    class Inner {
        class Inner2 {
            Inner2(Inner Inner.this) { }
        }
    }
}
