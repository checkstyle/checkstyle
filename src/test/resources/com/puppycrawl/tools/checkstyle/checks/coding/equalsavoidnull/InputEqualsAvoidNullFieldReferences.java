/*
EqualsAvoidNull
ignoreEqualsIgnoreCase = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.equalsavoidnull;

public class InputEqualsAvoidNullFieldReferences {
    static String classVar;
    String instanceVar;
    InputEqualsAvoidNullFieldReferences testObj = new InputEqualsAvoidNullFieldReferences("");

    InputEqualsAvoidNullFieldReferences(String param) {
        param.equals(""); // violation 'left .* of .* equals'
    }

    public void method(String param) {
        final String localVar = "";

        localVar.equals(""); // violation 'left .* of .* equals'
        param.equals(""); // violation 'left .* of .* equals'

        classVar.equals(""); // violation 'left .* of .* equals'
        instanceVar.equals(""); // violation 'left .* of .* equals'
        InputEqualsAvoidNullFieldReferences.classVar.equals(""); // violation 'left .* of .* equals'
        this.classVar.equals(""); // violation 'left .* of .* equals'
        this.instanceVar.equals(""); // violation 'left .* of .* equals'

        InputEqualsAvoidNullFieldReferences testObj = new InputEqualsAvoidNullFieldReferences("");
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
                // violation below 'left .* of .* equals'
                InputEqualsAvoidNullFieldReferences.classVar.equals("");

                instanceVarInner.equals(""); // violation 'left .* of .* equals'
                this.instanceVarInner.equals(""); // violation 'left .* of .* equals'
                localVar.equals(""); // violation 'left .* of .* equals'

                InputEqualsAvoidNullFieldReferences testObj =
                        new InputEqualsAvoidNullFieldReferences("");
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
        public void method() {
            classVar.equals(""); // violation 'left .* of .* equals'
            // violation below 'left .* of .* equals'
            InputEqualsAvoidNullFieldReferences.classVar.equals("");
            this.nestedInstanceVar.equals(""); // violation 'left .* of .* equals'
            nestedClassVar.equals(""); // violation 'left .* of .* equals'
            nestedInstanceVar.equals(""); // violation 'left .* of .* equals'

            class Inner {
                public void method() {
                    classVar.equals(""); // violation 'left .* of .* equals'
                    // violation below 'left .* of .* equals'
                    InputEqualsAvoidNullFieldReferences.classVar.equals("");
                    nestedClassVar.equals(""); // violation 'left .* of .* equals'
                    nestedInstanceVar.equals(""); // violation 'left .* of .* equals'
                }
            }
        }
    }
}
