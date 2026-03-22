/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

import java.util.*;

public class InputRequireThisValidateOnlyOverlappingFalse {

    private static String fieldStatic = "fieldStatic";

    private final long fieldFinal1;
    private final long fieldFinal2;
    private final BitSet fieldFinal3;

    private String field1;
    private String field2;
    private String field3 = "some string";
    private boolean booleanField;
    private int intField;

    public InputRequireThisValidateOnlyOverlappingFalse(String field1) {
        field1 = field1; // violation 'Reference to instance variable 'field1' needs "this.".'
        fieldFinal1 = 0; // violation '.* variable 'fieldFinal1' needs "this.".'
        fieldFinal2 = 0; // violation '.* variable 'fieldFinal2' needs "this.".'
        fieldFinal3 = new BitSet(); // violation '.* variable 'fieldFinal3' needs "this.".'
    }

    public InputRequireThisValidateOnlyOverlappingFalse(long value) {
        fieldFinal1 = value; // violation '.* variable 'fieldFinal1' needs "this.".'
        fieldFinal2 = 0; // violation '.* variable 'fieldFinal2' needs "this.".'
        fieldFinal3 = new BitSet(); // violation '.* variable 'fieldFinal3' needs "this.".'
    }

    public InputRequireThisValidateOnlyOverlappingFalse() {
        fieldFinal1 = 0; // violation '.* variable 'fieldFinal1' needs "this.".'
        long fieldFinal2 = 0L;
        fieldFinal2 = 1L;
        this.fieldFinal2 = fieldFinal2;
        fieldFinal3 = new BitSet(); // violation '.* variable 'fieldFinal3' needs "this.".'
    }

    public InputRequireThisValidateOnlyOverlappingFalse(String name, long id) {
        fieldFinal1 = 0; // violation '.* variable 'fieldFinal1' needs "this.".'
        long field1 = 0L;
        field1 = field1; // violation 'Reference to instance variable 'field1' needs "this.".'
        this.fieldFinal2 = 1L;
        fieldFinal3 = new BitSet(); // violation '.* variable 'fieldFinal3' needs "this.".'
    }

    public InputRequireThisValidateOnlyOverlappingFalse(int param) {
        fieldFinal2 = 0L; // violation '.* variable 'fieldFinal2' needs "this.".'
        fieldFinal3 = new BitSet(); // violation '.* variable 'fieldFinal3' needs "this.".'
        long fieldFinal1 = 1L;
        try {
            fieldFinal1 = 2L;
        }
        catch (Exception ex) {}
        this.fieldFinal1 = fieldFinal1;
    }

    public InputRequireThisValidateOnlyOverlappingFalse(BitSet fieldFinal3) {
        fieldFinal1 = 1L; // violation 'Reference to instance variable 'fieldFinal1' needs "this.".'
        fieldFinal2 = 0L; // violation 'Reference to instance variable 'fieldFinal2' needs "this.".'
        fieldFinal3 = new BitSet();
        if (true) {
            fieldFinal3 = (BitSet) fieldFinal3.clone();
        }
        this.fieldFinal3 = fieldFinal3;
    }

    void foo1(String methodParam) {
        methodParam = methodParam;
    }

    void foo2() {
        String localVar = "values";
        localVar = localVar;
    }

    void foo3() {
        String field1 = "values";
        field1 = field1; // violation 'Reference to instance variable 'field1' needs "this.".'
    }

    void foo4(String methodParam) {
        fieldStatic = methodParam;
    }

    void foo5(String methodParam) {
        methodParam = methodParam + "string";
    }

    void foo6(String field1) {
        field1 = this.field1 + field1;
        this.field1 = field1 + this.field1;
        field1 = field1 + this.field1;
        field1 = this.field1 + this.field1;
        this.field1 = this.field1 + this.field1;
        this.field1 = this.field1 + field1;
        field1 += field1;
    }

    String addSuffixToParameter(String methodParam) {
        return methodParam += "suffix";
    }

    String addSuf2F(String field1) {
        return field1 += "suffix";
    }

    String addSuffixToThisField(String field1) {
        return this.field1 += "suffix";
    }

    static void foo7(String fieldStatic) {
//        this.fieldStatic = fieldStatic; <- fieldStatic cannot be referenced from a static context
        fieldStatic = fieldStatic;
    }

    void foo8(Long field1) {
        field1 += field1; // violation 'Reference to instance variable 'field1' needs "this.".'
    }

    void foo9(Long fieldFinal1) {
//        this.fieldFinal1 += fieldFinal1; <- cannot assign value to a final variable
        fieldFinal1 += fieldFinal1;
    }

    void method1() {
        field1 = "2"; // violation 'Reference to instance variable 'field1' needs "this.".'
    }

    void method2() {
        method1() ; // violation 'Method call to 'method1' needs "this.".'
    }

    void method3() {
        staticFoo();
        staticTwoArgs("message", "arg");
        staticTwoArgs("message", 1);
        this.method1() ;
    }

    static void staticFoo() { }

    static void foo10() {
        staticFoo();
        staticTwoArgs("message", "arg");
    }

//    void staticFoo() {} -> compile-time error. Already defined in the scope.

    static void staticTwoArgs(String message1, String argument) {}

    void staticTwoArgs(String message1, int argument) {}

    static void foo16() {
        long fieldFinal1 = 5L;
// this.fieldFinal1 = fieldFinal1; // compile-time error: cannot be referenced from a static context
        fieldFinal1 = 11L;
    }

    static void foo17() {
        String fieldStatic = "";
// this.fieldStatic = fieldStatic; // compile-time error: cannot be referenced from a static context
        fieldStatic = "Hello, World!";
    }

    InputRequireThisValidateOnlyOverlappingFalse(boolean flag) {
        fieldFinal1 = 0L; // violation 'Reference to instance variable 'fieldFinal1' needs "this.".'
        fieldFinal2 = 0L; // violation 'Reference to instance variable 'fieldFinal2' needs "this.".'
        fieldFinal3 = new BitSet(); // violation '.* variable 'fieldFinal3' needs "this.".'
        long field1 = 1L;
        field1 = field1; // violation 'Reference to instance variable 'field1' needs "this.".'
    }

    InputRequireThisValidateOnlyOverlappingFalse(boolean flag, String name) {
        fieldFinal1 = 0L; // violation 'Reference to instance variable 'fieldFinal1' needs "this.".'
        fieldFinal2 = 0L; // violation 'Reference to instance variable 'fieldFinal2' needs "this.".'
        fieldFinal3 = new BitSet(); // violation '.* variable 'fieldFinal3' needs "this.".'
        long field1 = 1L;
        field1 = field1; // violation 'Reference to instance variable 'field1' needs "this.".'
        return;
    }

    void foo18() {
        field1 = "Hello"; // violation 'Reference to instance variable 'field1' needs "this.".'
    }

    void foo19(String field1) {
        field1 = "Hello"; // violation 'Reference to instance variable 'field1' needs "this.".'
    }

    void foo20() {
        boolean foo21 = this.foo21("");
        if (foo21) {

        }
    }

    boolean foo21(String s) {
        return true;
    }

    void foo22() {
        long fieldFinal1 = 1L;
//        this.fieldFinal1 = fieldFinal1; <- cannot assign value to a final variable
        fieldFinal1 = fieldFinal1;
    }

    void foo23() {
        field1 = "Hello!"; // violation 'Reference to instance variable 'field1' needs "this.".'
    }

    void foo24() {
        String field1 = "Hello";
        field1 = "Java"; // No violation. Local var allowed
        this.booleanField = true;
        this.booleanField = booleanField; // violation '.* variable 'booleanField' needs "this.".'
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

    void foo26(String field1) {
        field1 = field1.replace('/', '*'); // violation '.* variable 'field1' needs "this.".'
    }

    void foo27() {
        int intField = -1;
        if (intField == -1) {
            intField = 20;
        }
        else {
            intField = this.intField / 100;
        }
    }

    void foo28() {
        boolean booleanField = true;
        booleanField = !booleanField; // violation '.* variable 'booleanField' needs "this.".'
    }

    static void foo29(String field1) {
//this.field1 = true ? "field1" : field1; /compile error: cannot be referenced from a static context
        field1 = true ? "field1" : field1;
    }

    void foo30(String field1) {
        field1 = true ? "field1" : field1; // violation '.* variable 'field1' needs "this.".'
    }

    void foo31(String field1) {
        field1 = this.field1;
    }

    String foo32(String field1) {
        field1 = addSuf2F(field1); // violation 'Method call to 'addSuf2F' needs "this.".'
        return field1;
    }

    String foo33(String field1 ) {
        field1 = addSuf2F(field1); // 2 violations
        return "New String";
    }

    String foo34(String field1) {
        field1 = field1.replace('A', 'B');
        if (field1.contains("C")) {
            return field1;
        }
        else {
            return field1 + 'C';
        }
    }

    String foo35() {
        String field1 = "values";
        field1 = field1;
        return field1;
    }

    void foo36(String field1) {
        field1 = field1.replace('/', '*');
        field1 = this.field1;
    }

    String foo37(String field1) {
        field1 += "suffix"; // violation 'Reference to instance variable 'field1' needs "this.".'
        return "New string";
    }

    static void foo38() {
//        this.fieldStatic = ""; <-- compile-time error: cannot be referenced from a static context
        fieldStatic = "";
    }

    static void foo39() {
        boolean booleanField = true;
// this.booleanField = !booleanField; compile-time error: cannot be referenced from a static context
        booleanField = !booleanField;
    }

    static void foo40() {
        try {
            boolean booleanField = true;
//this.booleanField = !booleanField; <-- compile error: cannot be referenced from a static context
            booleanField = !booleanField;
        }
        catch (Exception e) {}
    }

    static {
//        this.fieldStatic = ""; <-- compile-time error: cannot be referenced from a static context
        fieldStatic = "";
    }

    {
//        if we assign variable to a final variable in initialization block,
//        it will lead to compile-time error in constructor block: variable migh have been
//        already assigned

//        fieldFinal1 = 1;
    }

    {
        String field1 = "";
        field1 = field1; // violation 'Reference to instance variable 'field1' needs "this.".'
    }

    static {
        fieldStatic = "";
        String field1 = "";
//        this.field1 = field1; <-- compile-time error: cannot be referenced from a static context
        field1 = field1;
    }

    void foo41(long fieldFinal1) {
//        this.fieldFinal1 = 1L; <- cannot assign value to a final variable
        fieldFinal1 = fieldFinal1;
    }

    void foo42(String fieldStatic) {
        this.fieldStatic = fieldStatic;
    }

    void foo43(String fieldStatic) {
        fieldStatic = fieldStatic;
    }

    void foo44(String fieldStatic) {
        fieldStatic = this.fieldStatic;
    }

    private String action;

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

    public InputRequireThisValidateOnlyOverlappingFalse(long fieldFinal1, long fieldFinal2,
                                                        BitSet fieldFinal3, boolean booleanField) {
        this.fieldFinal1 = fieldFinal1;
        this.fieldFinal2 = fieldFinal2;
        this.fieldFinal3 = fieldFinal3;

        booleanField = this.booleanField;
        if (booleanField) {
            booleanField = "Hello, World!".equals("Hello, Checkstyle!");
        }

        this.booleanField = booleanField;
    }

    void foo46(boolean booleanField) {
        booleanField = this.booleanField;
        if (booleanField) {
            booleanField = "Hello, World!".equals("Hello, Checkstyle!");
        }

        this.booleanField = booleanField;
    }

    static void foo47(String fieldStatic) {
        fieldStatic = "Andrei";
    }

    void foo48(long fieldFinal1) {
        fieldFinal1 = 1L;
    }

    private boolean foo49(boolean booleanField) {
        boolean suppressionSourceExists = true;
        try {

        }
        catch (Exception ex) {
            suppressionSourceExists = false;
        }
        finally {
            if (booleanField) {
                try {
                }
                catch (Exception ignored) {
                    this.booleanField = false;
                }
            }
        }
        return suppressionSourceExists;
    }

    void foo50(String fieldStatic) {
        fieldStatic = fieldStatic;
    }

    void foo51(String methodParam) {
        fieldStatic = methodParam;
    }

    void foo52(String fieldStatic) {
        fieldStatic += fieldStatic;
    }

    void foo53(String fieldStatic) {
        fieldStatic += fieldStatic;
    }

    void foo54(String methodParam) {
        fieldStatic += methodParam;
    }

    void foo55(String methodParam) {
        fieldStatic += fieldStatic;
    }

    void foo56(boolean booleanField) { booleanField = this.booleanField; }

    boolean foo57(boolean booleanField) { booleanField = !booleanField;  return booleanField; }
}
class Issue6264 {
    private final String hashCode;

    Issue6264() {
        this.hashCode = new String();
    }

    private void hashCodeMethod(String param) {
        hashCode();
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
class AnotherOverlappingFalse {
    private String name;

    public void bind() {
        new Object() {
            @Override
            public String toString() {
                String name = null;

                if ( true ) {
                    name = String.format(Locale.US, name);
                }

                return name;
            }
        };
    }
}
