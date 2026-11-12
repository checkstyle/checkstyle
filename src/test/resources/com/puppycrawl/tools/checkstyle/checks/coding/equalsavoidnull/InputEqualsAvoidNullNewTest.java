/*
EqualsAvoidNull
ignoreEqualsIgnoreCase = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.equalsavoidnull;

public class InputEqualsAvoidNullNewTest {
    static String classVar;
    String instanceVar;
    InputEqualsAvoidNullNewTest testObj = new InputEqualsAvoidNullNewTest("");

    InputEqualsAvoidNullNewTest(String param) {
        param.equals(""); // violation 'left .* of .* equals'
    }

    public void method(String param) {
        final String localVar = "";

        localVar.equals(""); // violation 'left .* of .* equals'
        param.equals(""); // violation 'left .* of .* equals'

        classVar.equals(""); // violation 'left .* of .* equals'
        instanceVar.equals(""); // violation 'left .* of .* equals'
        InputEqualsAvoidNullNewTest.classVar.equals(""); // violation 'left .* of .* equals'
        this.classVar.equals(""); // violation 'left .* of .* equals'
        this.instanceVar.equals(""); // violation 'left .* of .* equals'

        InputEqualsAvoidNullNewTest testObj = new InputEqualsAvoidNullNewTest("");
        this.testObj.instanceVar.equals(""); // not violated, too confusing
        testObj.classVar.equals(""); // not violated

        for (Nested instanceVar = new Nested(); instanceVar != null; ) {
            instanceVar.equals(1);
            if (instanceVar.equals("")) {
                instanceVar.equals("");
            }
        }

        class Inner {
            String instanceVarInner;

            public void main() {
                classVar.equals(""); // violation 'left .* of .* equals'
                instanceVar.equals(""); // violation 'left .* of .* equals'
                InputEqualsAvoidNullNewTest.classVar.equals(""); // violation 'left .* of .* equals'

                instanceVarInner.equals(""); // violation 'left .* of .* equals'
                this.instanceVarInner.equals(""); // violation 'left .* of .* equals'
                localVar.equals(""); // violation 'left .* of .* equals'

                InputEqualsAvoidNullNewTest testObj = new InputEqualsAvoidNullNewTest("");
                testObj.instanceVar.equals(""); // not violated
                testObj.classVar.equals(""); // not violated

                Inner testInnerObj = new Inner();
                testInnerObj.instanceVarInner.equals(""); // not violated
            }
        }

        Inner testInnerObj = new Inner();
        testInnerObj.instanceVarInner.equals(""); // not violated

        Nested.nestedClassVar.equals(""); // not violated, because the equals call is not
        Nested Nested = new Nested(); // embedded in class Nested, what can lead to really
        Nested.nestedInstanceVar.equals(""); // confusing constructions. But could be improved.
        Nested.nestedClassVar.equals("");
    }

    static class Nested {
        static String nestedClassVar;
        String nestedInstanceVar;
    }
}
