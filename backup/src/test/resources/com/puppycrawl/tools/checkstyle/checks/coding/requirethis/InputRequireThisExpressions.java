/*
RequireThis
checkFields = (default)true
checkMethods = false
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisExpressions {
    String id;
    int length;
    boolean b;

    public void method(int[] arr) {
        Object plus = "" + id; // violation 'Reference to instance variable 'id' needs "this.".'
        Object minus = 1 - length; // violation '.* variable 'length' needs "this.".'
        Object multi = 1 * length; // violation '.* variable 'length' needs "this.".'
        Object div = 1 / length; // violation '.* variable 'length' needs "this.".'
        Object mod = 1 % length; // violation '.* variable 'length' needs "this.".'
        Object lt = 1 < length; // violation '.* variable 'length' needs "this.".'
        Object gt = 1 > length; // violation '.* variable 'length' needs "this.".'
        Object le = 1 <= length; // violation '.* variable 'length' needs "this.".'
        Object ge = 1 >= length; // violation '.* variable 'length' needs "this.".'
        Object equal = false == b; // violation '.* variable 'b' needs "this.".'
        Object notEqual = false != b; // violation '.* variable 'b' needs "this.".'
        Object sl = 1 << length; // violation '.* variable 'length' needs "this.".'
        Object sr = 1 >> length; // violation '.* variable 'length' needs "this.".'
        Object bsr = 1 >>> length; // violation '.* variable 'length' needs "this.".'
        Object and = 1 & length; // violation '.* variable 'length' needs "this.".'
        Object xor = 1 ^ length; // violation '.* variable 'length' needs "this.".'
        Object bor = 1 | length; // violation '.* variable 'length' needs "this.".'
        Object lor = false || b; // violation '.* variable 'b' needs "this.".'
        Object land = false && b; // violation '.* variable 'b' needs "this.".'
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
