/*
UnnecessaryNullCheckWithInstanceOf

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarynullcheckwithinstanceof;

public class InputUnnecessaryNullCheckWithInstanceOfArithmeticDereference {
    public void testArithmeticWrap(Object obj, Object other) {
        // obj.hashCode() is a real dereference - null check IS necessary
        if (obj != null && (obj.hashCode() + 1 > 0) && obj instanceof String) {
            System.out.println("ok");
        }
        
        // GT wrapping a method call dereference
        if (obj != null && obj.hashCode() > 0 && obj instanceof String) {
            System.out.println("ok");
        }

        // BAND wrapping a DOT dereference
        if (obj != null && (obj.hashCode() & 0xFF) == 0 && obj instanceof String) {
            System.out.println("ok");
        }

        // EQUAL wrapping a method call
        if (obj != null && obj.toString().equals("x") == false && obj instanceof String) {
            System.out.println("ok");
        }
        
        // Other variable is dereferenced, but not 'obj'. So 'obj != null' is redundant.
        // violation below 'Unnecessary nullity check'
        if (obj != null && other.hashCode() > 0 && obj instanceof String) {
            System.out.println("ok");
        }
        
        // Method call on 'obj', so 'obj != null' is necessary
        if (obj != null && method(obj.hashCode()) && obj instanceof String) {
            System.out.println("ok");
        }
        
        // Passing 'obj' as parameter - does NOT count as dereference for this check.
        // So 'obj != null' is redundant.
        // violation below 'Unnecessary nullity check'
        if (obj != null && method(obj) && obj instanceof String) {
            System.out.println("ok");
        }
        
        // Field access on 'other' named 'obj'. 'obj' is not dereferenced.
        // violation below 'Unnecessary nullity check'
        if (obj != null
            && ((InputUnnecessaryNullCheckWithInstanceOfArithmeticDereference) other).obj > 0
            && obj instanceof String) {
            System.out.println("ok");
        }
    }
    
    private boolean method(Object o) {
        return true;
    }
    
    public int obj;
}
