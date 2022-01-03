/*
RedundantThis
checkFields = (default)true
checkMethods = false
validateOnlyOverlapping = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

public class InputRedundantThisExpressions {
    String id;
    int length;
    boolean b;

    public void method(int[] arr) {
        Object plus = "" + id; // ok
        Object minus = 1 - length; // ok
        Object multi = 1 * length; // ok
        Object div = 1 / length; // ok
        Object mod = 1 % length; // ok
        Object lt = 1 < length; // ok
        Object gt = 1 > length; // ok
        Object le = 1 <= length; // ok
        Object ge = 1 >= length; // ok
        Object equal = false == b; // ok
        Object notEqual = false != b; // ok
        Object sl = 1 << length; // ok
        Object sr = 1 >> length; // ok
        Object bsr = 1 >>> length; // ok
        Object and = 1 & length; // ok
        Object xor = 1 ^ length; // ok
        Object bor = 1 | length; // ok
        Object lor = false || b; // ok
        Object land = false && b; // ok
    }

    public void methodThis(int[] arr) {
        Object plus = "" + this.id; // violation
        Object minus = 1 - this.length; // violation
        Object multi = 1 * this.length; // violation
        Object div = 1 / this.length; // violation
        Object mod = 1 % this.length; // violation
        Object lt = 1 < this.length; // violation
        Object gt = 1 > this.length; // violation
        Object le = 1 <= this.length; // violation
        Object ge = 1 >= this.length; // violation
        Object equal = false == this.b; // violation
        Object notEqual = false != this.b; // violation
        Object sl = 1 << this.length; // violation
        Object sr = 1 >> this.length; // violation
        Object bsr = 1 >>> this.length; // violation
        Object and = 1 & this.length; // violation
        Object xor = 1 ^ this.length; // violation
        Object bor = 1 | this.length; // violation
        Object lor = false || this.b; // violation
        Object land = false && this.b; // violation

        Object fields = arr.length + this.length; // violation
    }
}
