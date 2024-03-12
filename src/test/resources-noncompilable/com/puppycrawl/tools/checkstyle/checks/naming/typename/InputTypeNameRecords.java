/*
TypeName
format = (default)^[A-Z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.naming.typename;

public class InputTypeNameRecords {
    interface FirstName {
    }

    class SecondName {
    }

    enum Third_Name {} // violation

    class FourthName_ { // violation
    }

    record My_Record() { // violation
        record Inner__Record(Record MyRecord) { // violation
            public Inner__Record{}
        }
    }

    record MyRecord__() { // violation
    }

    record GoodName() {
    }
}
