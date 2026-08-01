/*
UnusedPrivateField
ignoreAnnotationCanonicalNames=InputUnusedPrivateFieldAnnotationShortName.MockBean
ignoredFieldPattern = (default)serialVersionUID

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldDotAccessForms {

    private int thisField; // ok, private field is used
    int readThis() {
        return this.thisField;
    }

    private int outerField; // ok, private field is used
    class Inner {
        int readOuter() {
            return InputUnusedPrivateFieldDotAccessForms.this.outerField;
        }
    }

    private int objField; // violation 'Unused private field'

    private int shared; // violation 'Unused private field'
    static class Other {
        int shared;
    }
    void useOther(Other o) {
        System.out.println(o.shared);
    }

    static class Other2 {
        private int external; // ok, used via o.external below
    }
    void useOther2(Other2 o) {
        System.out.println(o.external);
    }

    private int onlyOuter;
    class Inner2 {
        int readOnlyOuter() {
            return onlyOuter;
        }
    }

    private int outerOnly;
    class Middle {
        class Innermost {
            int readOuterOnly() {
                return InputUnusedPrivateFieldDotAccessForms.this.outerOnly;
            }
        }
    }


   private int middleField;
    class MiddleLevel {
        private int middleField; // violation 'Unused private field'
        class DeepInner {
            int readViaOuterOnly() {
                return InputUnusedPrivateFieldDotAccessForms.this.middleField;
            }
        }
    }

    static class Container {
        static class Nested {
            int value;
        }
        Nested nested = new Nested();
    }
    private int value; // ok, matched via global fallback
    void useContainer(Container c) {
        System.out.println(c.nested.value);
    }

    private int shadowTarget; // violation 'Unused private field'

    class OtherWithSameFieldName {
        private int shadowTarget; // ok, private field is used
        int read() {
            return this.shadowTarget;
        }
}
}
