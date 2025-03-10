/*
TypeName
format = (default)^[A-Z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.naming.typename;

public class InputTypeNameRecords {
    interface FirstName {
    }

    class SecondName {
    }
    // violation 'Name 'Third_Name' must match pattern'
    enum Third_Name {}
    // violation 'Name 'FourthName_' must match pattern'
    class FourthName_ {
    }
    // violation 'Name 'My_Record' must match pattern'
    record My_Record() {
        // violation 'Name 'Inner__Record' must match pattern'
        record Inner__Record(Record MyRecord) {
            public Inner__Record{}
        }
    }
    // violation 'Name 'MyRecord__' must match pattern'
    record MyRecord__() {
    }

    record GoodName() {
    }
}
