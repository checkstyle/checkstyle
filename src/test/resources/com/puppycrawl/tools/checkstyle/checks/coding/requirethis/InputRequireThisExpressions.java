package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

/**
 * Config
 * validateOnlyOverlapping = false
 * checkMethods = false
 * checkFields = true
 */
public class InputRequireThisExpressions {
    String id;
    int length;
    boolean b;

    public void method(int[] arr) {
        Object plus = "" + id; // violation
        Object minus = 1 - length; // violation
        Object multi = 1 * length; // violation
        Object div = 1 / length; // violation
        Object mod = 1 % length; // violation
        Object lt = 1 < length; // violation
        Object gt = 1 > length; // violation
        Object le = 1 <= length; // violation
        Object ge = 1 >= length; // violation
        Object equal = false == b; // violation
        Object notEqual = false != b; // violation
        Object sl = 1 << length; // violation
        Object sr = 1 >> length; // violation
        Object bsr = 1 >>> length; // violation
        Object and = 1 & length; // violation
        Object xor = 1 ^ length; // violation
        Object bor = 1 | length; // violation
        Object lor = false || b; // violation
        Object land = false && b; // violation
    }

    public void methodThis(int[] arr) {
        Object plus = "" + this.id;
        Object minus = 1 - this.length;
        Object multi = 1 * this.length;
        Object div = 1 / this.length;
        Object mod = 1 % this.length;
        Object lt = 1 < this.length;
        Object gt = 1 > this.length;
        Object le = 1 <= this.length;
        Object ge = 1 >= this.length;
        Object equal = false == this.b;
        Object notEqual = false != this.b;
        Object sl = 1 << this.length;
        Object sr = 1 >> this.length;
        Object bsr = 1 >>> this.length;
        Object and = 1 & this.length;
        Object xor = 1 ^ this.length;
        Object bor = 1 | this.length;
        Object lor = false || this.b;
        Object land = false && this.b;

        Object fields = arr.length + this.length;
    }
}
