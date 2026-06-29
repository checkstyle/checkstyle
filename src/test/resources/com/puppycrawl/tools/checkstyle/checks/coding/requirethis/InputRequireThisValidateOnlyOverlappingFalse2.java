/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

import java.util.*;

public class InputRequireThisValidateOnlyOverlappingFalse2 {

    private String field1;
    private String action;

    void foo19(String field1) {
        field1 = "Hello"; // violation 'Reference to instance variable 'field1' needs "this.".'
    }

    void foo25() {
        try {
            if (true) {
                String field1 = "Hello, World!";
                if (true) {
                    field1 = new String(); // No violation. Local var allowed
                }
                else {
                    field1 += field1; // violation '.* variable 'field1' needs "this.".'
                }
            }
        }
        catch (Exception ex) {

        }
    }


    void foo30(String field1) {
        field1 = true ? "field1" : field1; // violation '.* variable 'field1' needs "this.".'
    }

    String addSuf2F(String field1) {
        return field1 += "suffix";
    }

    String foo32(String field1) {
        field1 = addSuf2F(field1); // violation 'Method call to 'addSuf2F' needs "this.".'
        return field1;
    }

    String foo33(String field1 ) {
        field1 = addSuf2F(field1); // 2 violations
        return "New String";
    }

    String foo37(String field1) {
        field1 += "suffix"; // violation 'Reference to instance variable 'field1' needs "this.".'
        return "New string";
    }

    {
        String field1 = "";
        field1 = field1; // violation 'Reference to instance variable 'field1' needs "this.".'
    }

    public String getAction() {
        return this.action;
    }

    public String foo45() {
        String action = getAction(); // violation 'Method call to 'getAction' needs "this.".'
        if (true) {
            return processAction("action"); // violation 'Method .* 'processAction' needs "this.".'
        }
        else if (action.endsWith("/")) {
            if (action.startsWith("/")) {
                action = "" + action;
            }
        }
        action = "action"; // No violation. Local var allowed
        return processAction(action); // violation 'Method call to 'processAction' needs "this.".'
    }

    private String processAction(String action) {
        return "";
    }
}
class Issue7306 {
    List<String> add = new ArrayList<>();
    List<String> singletonList = new ArrayList<>();

    void someMethod() {
        List<String> test = new ArrayList<>();
        test.forEach(this.add::add);
        test.forEach(test::add);
        test.forEach(Collections::singletonList);
        test.forEach(add::add); // violation 'Reference to instance variable 'add' needs "this.".'
    }
}

