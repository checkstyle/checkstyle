//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.naming.typename;

/* Config:
 *
 * format = "^[A-Z][a-zA-Z0-9]*$"
 * applyToPublic = true
 * applyToProtected = true
 * applyToPackage = true
 * applyToPrivate = true
 * tokens = {CLASS_DEF , INTERFACE_DEF , ENUM_DEF , ANNOTATION_DEF, RECORD_DEF}
 */
public class InputTypeNameRecords {
    interface FirstName {
    } // OK

    class SecondName {
    } // OK

    enum Third_Name {} // violation

    class FourthName_ { // violation
    }

    record My_Record() { // violation
        record Inner__Record(Record MyRecord) {
            public Inner__Record{}
        }
    }

    record MyRecord__() { // violation
    }

    record GoodName() {
    } // ok
}
